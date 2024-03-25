package Honzapda.Honzapda_server.shop.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.facade.ShopFacadeService;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopFacadeService shopFacadeService;

    @GetMapping("/{shopId}")
    public ApiResult<ShopResponseDto.SearchDto> searchShop(@PathVariable(name = "shopId") Long shopId,
                        @SessionAttribute UserResDto.InfoDto user){
        return ApiResult.onSuccess(shopFacadeService.findShop(shopId, user.getId()));
    }

    @PostMapping("/register")
    public ApiResult<ShopResponseDto.SearchDto> registerShop(
            @RequestBody @Valid ShopRequestDto.RegisterDto request)
    {
        return ApiResult.onSuccess(shopFacadeService.registerShop(request));
    }


    @PostMapping("/login")
    public ApiResult<ShopResponseDto.OwnerInfoDto> loginShop(
            @RequestBody @Valid ShopRequestDto.LoginDto request)
    {
        return ApiResult.onSuccess(shopFacadeService.loginShop(request));
    }



    @PostMapping("/search")
    public ApiResult<Slice<ShopResponseDto.SearchByNameDto>> searchShopSlice(
            @RequestBody @Valid ShopRequestDto.SearchDto request,
            @PageableDefault()@Parameter(hidden = true) Pageable pageable)
    {
        return ApiResult.onSuccess(shopFacadeService.searchShop(request, pageable));
    }


}
