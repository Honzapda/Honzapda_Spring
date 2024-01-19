package Honzapda.Honzapda_server.shop.data;

import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopConverter {

    public static Shop toShop(ShopRequestDto.RegisterDto request) {
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

    public static ShopResponseDto.SearchDto toShopResponse(Shop shop){
        return ShopResponseDto.SearchDto.builder()
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

    public static Map<Long, ShopResponseDto.SearchDto> toShopResponseMap(List<ShopResponseDto.SearchDto> searchDtos) {
        return searchDtos.stream()
                .collect(Collectors.toMap(
                        ShopResponseDto.SearchDto::getShopId,
                        searchDto -> searchDto)
                );
    }
}
