package Honzapda.Honzapda_server.shop.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/{shopId}")
    public ApiResult<?> searchShop(@PathVariable(name = "shopId") Long shopId){

        try {
            ShopResponseDto.searchDto responseDto = shopService.findShop(shopId);
            return ApiResult.onSuccess(responseDto);
        } catch (NoSuchElementException e) {
            return ApiResult.onFailure(ErrorStatus._BAD_REQUEST.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            return ApiResult.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        }
    }
}
