package Honzapda.Honzapda_server.shop.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/{shopId}")
    public ApiResult<ShopResponseDto.searchDto> searchShop(@PathVariable(name = "shopId") Long shopId){
        return ApiResult.onSuccess(shopService.findShop(shopId));
    }

    @PostMapping("/")
    public ApiResult<ShopResponseDto.searchDto> registerShop(
            @RequestBody @Valid ShopRequestDto.registerDto request)
    {
        return ApiResult.onSuccess(shopService.registerShop(request));
    }
}
