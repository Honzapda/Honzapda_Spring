package Honzapda.Honzapda_server.shop.service.shop;

import Honzapda.Honzapda_server.shop.data.dto.MapResponseDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Map;

public interface ShopService {
    ShopResponseDto.SearchDto registerShop(ShopRequestDto.RegisterDto request);

    ShopResponseDto.OwnerInfoDto loginShop(ShopRequestDto.LoginDto request);

    ShopResponseDto.SearchDto findShop(Long shopId, Long userId);

    Map<Long, MapResponseDto.HomeDto> findShopsByShopIds(List<Long> mysqlIds);

    Map<Long, ShopResponseDto.SearchByNameDto> findShopsByShopIdsSorted(List<Long> mysqlIds);

    Slice<ShopResponseDto.SearchByNameDto> searchShopByShopNameContainingSortByReview(ShopRequestDto.SearchDto request, Pageable pageable);

    Slice<ShopResponseDto.SearchByNameDto> searchShopByShopNameContainingSortByBookmark(ShopRequestDto.SearchDto request, Pageable pageable);

    Slice<ShopResponseDto.SearchByNameDto> searchShopByShopNameContaining(ShopRequestDto.SearchDto request, Pageable pageable);

    List<ShopBusinessHour> getShopBusinessHours(Shop shop);

    boolean getOpenNow(List<ShopBusinessHour> businessHours);

    Long getShopBookMarkCount(Shop shop);

    Long getShopReviewCount(Shop shop);
}
