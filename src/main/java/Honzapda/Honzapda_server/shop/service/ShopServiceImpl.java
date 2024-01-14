package Honzapda.Honzapda_server.shop.service;

import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.repository.ReviewRepository;
import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.ShopRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {

    private final UserRepository userRepository;

    private final ShopRepository shopRepository;

    private final ReviewRepository reviewRepository;

    public ShopResponseDto.searchDto registerShop(ShopRequestDto.registerDto request){

        Optional<User> user = userRepository.findById(request.getUserId());

        if(user.isPresent()){
            Shop shop = ShopConverter.toShop(request, user.get());
            shopRepository.save(shop);
            return ShopConverter.toShopResponse(shop);
        } else {
            throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");
        }
    }

    public ShopResponseDto.searchDto findShop(Long shopId){
        Optional<Shop> shop = shopRepository.findById(shopId);

        if (shop.isPresent()) {
            ShopResponseDto.searchDto resultDto = ShopConverter.toShopResponse(shop.get());
            resultDto.setRating(getRating(shopId));

            return resultDto;
        } else {
            throw new NoSuchElementException("해당 가게가 존재하지 않습니다.");
        }
    }

    public double getRating(Long shopId){
        List<Review> reviewList = reviewRepository.findByShopId(shopId);

        return reviewList.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);
    }
}
