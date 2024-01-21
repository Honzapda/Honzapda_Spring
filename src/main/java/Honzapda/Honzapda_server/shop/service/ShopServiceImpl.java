package Honzapda.Honzapda_server.shop.service;

import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.repository.ReviewRepository;
import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopPhoto;
import Honzapda.Honzapda_server.shop.repository.ShopPhotoRepository;
import Honzapda.Honzapda_server.shop.repository.ShopRepository;
import Honzapda.Honzapda_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {

    private final UserRepository userRepository;

    private final ShopRepository shopRepository;

    private final ReviewRepository reviewRepository;

    private final ShopPhotoRepository shopPhotoRepository;

    public boolean registerShop(ShopRequestDto.registerDto request){

        Shop shop = ShopConverter.toShop(request);
        shopRepository.save(shop);

        List<String> photoUrls = request.getPhotoUrls();
        saveShopPhoto(shop.getId(), photoUrls);

        return true;
    }

    public ShopResponseDto.searchDto findShop(Long shopId){
        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if (optionalShop.isPresent()) {

            Shop shop = optionalShop.get();
            List<String> photoUrls = getShopPhotoUrls(shop);
            ShopResponseDto.searchDto resultDto = ShopConverter.toShopResponse(shop, photoUrls);
            resultDto.setRating(getRating(shopId));

            return resultDto;
        } else {
            throw new NoSuchElementException("해당 가게가 존재하지 않습니다.");
        }
    }

    public boolean saveShopPhoto(Long shopId, List<String> photoUrls){

        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();

            photoUrls.stream()
                    .forEach(photoUrl ->{
                        ShopPhoto shopPhoto = ShopPhoto.builder()
                                .url(photoUrl)
                                .shop(shop)
                                .build();

                        shopPhotoRepository.save(shopPhoto);
                    });

            return true;
        } else {
            throw new NoSuchElementException("해당 가게가 존재하지 않습니다.");
        }
    }

    public List<String> getShopPhotoUrls(Shop shop) {
        List<ShopPhoto> shopPhotoList = shopPhotoRepository.findShopPhotosByShop(shop);

        List<String> photoUrls = shopPhotoList.stream()
                .map(ShopPhoto::getUrl)
                .collect(Collectors.toList());

        return photoUrls;
    }

    public double getRating(Long shopId){
        List<Review> reviewList = reviewRepository.findByShopId(shopId);

        return reviewList.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);
    }
}
