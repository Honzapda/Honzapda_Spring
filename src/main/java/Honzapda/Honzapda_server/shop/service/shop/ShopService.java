package Honzapda.Honzapda_server.shop.service.shop;

import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Map;

public interface ShopService {
    ShopResponseDto.SearchDto registerShop(ShopRequestDto.RegisterDto request);

    ShopResponseDto.SearchDto findShop(Long shopId);

    Map<Long, ShopResponseDto.SearchDto> findShopsByShopIds(List<Long> mysqlIds);

    Slice<ShopResponseDto.SearchDto> searchShopByShopNameContainingSortByReview(ShopRequestDto.SearchDto request, Pageable pageable);

    Slice<ShopResponseDto.SearchDto> searchShopByShopNameContainingSortByBookmark(ShopRequestDto.SearchDto request, Pageable pageable);
}
