package Honzapda.Honzapda_server.shop.service;

import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;

public interface ShopService {
    ShopResponseDto.searchDto registerShop(ShopRequestDto.registerDto request);

    ShopResponseDto.searchDto findShop(Long shopId);
}
