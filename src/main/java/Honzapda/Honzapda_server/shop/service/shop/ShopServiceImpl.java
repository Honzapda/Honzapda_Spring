package Honzapda.Honzapda_server.shop.service.shop;

import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewRepository;
import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.user.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopServiceImpl implements ShopService {

    private final UserRepository userRepository;

    private final ShopRepository shopRepository;

    private final ReviewRepository reviewRepository;

    @Transactional
    public ShopResponseDto.SearchDto registerShop(ShopRequestDto.RegisterDto request){

        Shop shop = ShopConverter.toShop(request);
        shopRepository.save(shop);
        return ShopConverter.toShopResponse(shop);
    }

    public ShopResponseDto.SearchDto findShop(Long shopId){
        Optional<Shop> shop = shopRepository.findById(shopId);

        if (shop.isPresent()) {
            ShopResponseDto.SearchDto resultDto = ShopConverter.toShopResponse(shop.get());
            resultDto.setRating(getRating(shopId));

            return resultDto;
        } else {
            throw new NoSuchElementException("해당 가게가 존재하지 않습니다.");
        }
    }

    @Override
    public Map<Long, ShopResponseDto.SearchDto> findShopsByShopIds(List<Long> mysqlIds) {
        List<Shop> shops = shopRepository.findByIdIn(mysqlIds);
        List<ShopResponseDto.SearchDto> searchDtos = shops.stream()
                .map(ShopConverter::toShopResponse)
                .toList();
        searchDtos.forEach(dto -> dto.setRating(getRating(dto.getShopId())));
        return ShopConverter.toShopResponseMap(searchDtos);
    }

    @Override
    public Slice<ShopResponseDto.SearchDto> searchShopByShopNameContainingSortByReview(ShopRequestDto.SearchDto request, Pageable pageable) {
        return shopRepository.findByShopNameContainingOrderByReviewCountDesc(request.getKeyword(), pageable)
                .map(ShopConverter::toShopResponse);
    }

    @Override
    public Slice<ShopResponseDto.SearchDto> searchShopByShopNameContainingSortByBookmark(ShopRequestDto.SearchDto request, Pageable pageable) {
        return shopRepository.findByShopNameContainingOrderByBookmarkCountDesc(request.getKeyword(), pageable)
                .map(ShopConverter::toShopResponse);
    }

    private double getRating(Long shopId){
        List<Review> reviewList = reviewRepository.findByShopId(shopId);

        return reviewList.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);
    }
}
