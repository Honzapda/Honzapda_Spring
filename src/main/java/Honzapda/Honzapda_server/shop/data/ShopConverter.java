package Honzapda.Honzapda_server.shop.data;

import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShopConverter {

    public static Shop toShop(ShopRequestDto.RegisterDto request, PasswordEncoder encoder) {
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
                .loginId(request.getLoginId())
                .password(encoder.encode(request.getPassword()))
                .shopMainImage(request.getShopMainImage())
                .build();
    }

    public static ShopResponseDto.SearchDto toShopResponse(Shop shop){
        return ShopResponseDto.SearchDto.builder()
                .shopId(shop.getId())
                .shopName(shop.getShopName())
                .description(shop.getDescription())
                .otherDetails(shop.getOtherDetails())
                .shopPhoneNumber(shop.getShopPhoneNumber())
                .rating(shop.getRating())
                .address(shop.getAddress())
                .address_spec(shop.getAddress_spec())
                .inactiveDate(shop.getInactiveDate())
                .photoUrl(shop.getShopMainImage())
                .reviewList(null)
                .build();
    }

    public static ShopResponseDto.SimpleSearchDto toShopSimpleResponse(Shop shop){
        return ShopResponseDto.SimpleSearchDto.builder()
                .shopId(shop.getId())
                .shopName(shop.getShopName())
                .shopMainImage(shop.getShopMainImage())
                .build();
    }
    public static Map<Long, ShopResponseDto.SearchDto> toShopResponseMap(List<Long> mysqlIds, List<ShopResponseDto.SearchDto> searchDtos) {
        return mysqlIds.stream()
                .flatMap(id -> searchDtos.stream().filter(dto -> dto.getShopId().equals(id)))
                .collect(Collectors.toMap(
                        ShopResponseDto.SearchDto::getShopId,
                        Function.identity(),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }
    public static Map<Long, ShopResponseDto.SearchByNameDto> toSearchResponseMap(List<Long> mysqlIds, List<ShopResponseDto.SearchByNameDto> searchByNameDtos) {
        return mysqlIds.stream()
                .flatMap(id -> searchByNameDtos.stream().filter(dto -> dto.getShopId().equals(id)))
                .collect(Collectors.toMap(
                        ShopResponseDto.SearchByNameDto::getShopId,
                        Function.identity(),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    public static ShopResponseDto.BusinessHoursResDTO toShopBusinessHourDto(ShopBusinessHour shopBusinessHour) {
        return ShopResponseDto.BusinessHoursResDTO.builder()
                .isOpen(shopBusinessHour.isOpen())
                .dayOfWeek(shopBusinessHour.getDayOfWeek())
                .openHours(shopBusinessHour.getOpenHours())
                .closeHours(shopBusinessHour.getCloseHours())
                .build();
    }
}
