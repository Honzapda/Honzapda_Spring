package Honzapda.Honzapda_server.shop.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.facade.ShopFacadeService;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopFacadeService shopFacadeService;

    @GetMapping("/{shopId}")
    public ApiResult<ShopResponseDto.SearchDto> searchShop(@PathVariable(name = "shopId") Long shopId){
        return ApiResult.onSuccess(shopFacadeService.findShop(shopId));
    }

    @PostMapping("/register")
    public ApiResult<ShopResponseDto.SearchDto> registerShop(
            @RequestBody @Valid ShopRequestDto.RegisterDto request)
    {
        return ApiResult.onSuccess(shopFacadeService.registerShop(request));
    }

    @GetMapping("/search")
    public ApiResult<Slice<ShopResponseDto.SearchDto>> searchShopSlice(
            @SessionAttribute(name = "user") UserResDto userResDto,
            @RequestBody @Valid ShopRequestDto.SearchDto request,
            @PageableDefault() Pageable pageable)
    {
        return ApiResult.onSuccess(shopFacadeService.searchShop(userResDto, request, pageable));
    }
}
