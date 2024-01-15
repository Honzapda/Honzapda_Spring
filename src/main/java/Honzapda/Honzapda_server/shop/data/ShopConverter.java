package Honzapda.Honzapda_server.shop.data;

import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;

import java.time.LocalDateTime;

public class ShopConverter {

    public static Shop toShop(ShopRequestDto.registerDto request, User user) {
        return Shop.builder()
                .shopName(request.getShopName())
                .adminName(request.getAdminName())
                .description(request.getDescription())
                .otherDetails(request.getOtherDetails())
                .shopPhoneNumber(request.getShopPhoneNumber())
                .adminPhoneNumber(request.getAdminPhoneNumber())
                .rating(0.0)
                .address(request.getAddress())
                .address_spec(request.getAddress_spec())
                .businessNumber(request.getBusinessNumber())
                .inactiveDate(LocalDateTime.now())
                .user(user)
                .build();
    }

    public static ShopResponseDto.searchDto toShopResponse(Shop shop){
        return ShopResponseDto.searchDto.builder()
                .shopId(shop.getId())
                .shopName(shop.getShopName())
                .adminName(shop.getAdminName())
                .description(shop.getDescription())
                .otherDetails(shop.getOtherDetails())
                .shopPhoneNumber(shop.getShopPhoneNumber())
                .adminPhoneNumber(shop.getAdminPhoneNumber())
                .rating(shop.getRating())
                .address(shop.getAddress())
                .address_spec(shop.getAddress_spec())
                .inactiveDate(shop.getInactiveDate())
                .businessNumber(shop.getBusinessNumber())
                .reviewList(null)
                .build();
    }

}
