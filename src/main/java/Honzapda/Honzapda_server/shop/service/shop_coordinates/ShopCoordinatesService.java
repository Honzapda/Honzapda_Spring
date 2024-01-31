package Honzapda.Honzapda_server.shop.service.shop_coordinates;

import Honzapda.Honzapda_server.shop.data.dto.MapRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import Honzapda.Honzapda_server.shop.service.shop_coordinates.dto.ShopCoordinatesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ShopCoordinatesService {
    ShopCoordinates registerShopCoordinates(ShopCoordinatesDto request);

    ShopCoordinates findShopCoordinates(Long shopId);

    List<ShopCoordinates> findShopsByLocation(MapRequestDto.LocationDto locationDto);

    Page<ShopCoordinates> findByShopNameContainingAndLocationNear(ShopRequestDto.SearchDto searchDto, Pageable pageable);

    List<ShopCoordinates> findShopsCoordiates(List<Long> mysqlIds);
}
