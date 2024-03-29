package Honzapda.Honzapda_server.shop.service.shop_congestion;

import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.ShopDayCongestion;

import java.util.List;
import java.util.Map;

public interface ShopCongestionService {
    ShopResponseDto.SearchDto registerCongestion(ShopRequestDto.RegisterDto coordinatesDto, ShopResponseDto.SearchDto registeredShop);

    ShopResponseDto.SearchDto findShopCongestion(ShopResponseDto.SearchDto searchDto);

}
