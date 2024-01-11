package Honzapda.Honzapda_server.shop.data;

import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;

import java.time.LocalDateTime;

public class ShopConverter {

    public static Shop toShop(ShopRequestDto.registerDto request, User user) {
        return Shop.builder()
                .name(request.getName())
                .description(request.getDescription())
                .otherDetails(request.getOtherDetails())
                .phoneNumber(request.getPhoneNumber())
                .rating(0.0)
                .address(request.getAddress())
                .address_spec(request.getAddress_spec())
                .inactiveDate(LocalDateTime.now())
                .user(user)
                .build();
    }

    public static ShopResponseDto.searchDto toShopResponse(Shop shop){
        return ShopResponseDto.searchDto.builder()
                .shopId(shop.getId())
                .name(shop.getName())
                .description(shop.getDescription())
                .otherDetails(shop.getOtherDetails())
                .phoneNumber(shop.getPhoneNumber())
                .rating(shop.getRating())
                .address(shop.getAddress())
                .address_spec(shop.getAddress_spec())
                .inactiveDate(shop.getInactiveDate())
                .reviewList(null)
                .build();
    }

}
