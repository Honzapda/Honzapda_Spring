package Honzapda.Honzapda_server.shop.data;

import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;

import java.time.LocalDateTime;
import java.util.List;

public class ShopConverter {

    public static Shop toShop(ShopRequestDto.registerDto request) {
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
                .build();
    }

    public static ShopResponseDto.searchDto toShopResponse(Shop shop, List<String> photoUrls){
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
                .photoUrls(photoUrls)
                .reviewList(null)
                .build();
    }

}
