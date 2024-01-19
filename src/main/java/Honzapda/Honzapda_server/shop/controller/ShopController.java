package Honzapda.Honzapda_server.shop.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/{shopId}")
    public ApiResult<?> searchShop(@PathVariable(name = "shopId") Long shopId){
        ShopResponseDto.searchDto responseDto = shopService.findShop(shopId);
        return ApiResult.onSuccess(responseDto);
    }
}
