package Honzapda.Honzapda_server.shop.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.shop.data.dto.MapRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.MapResponseDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.facade.ShopFacadeService;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
@Slf4j
public class MapController {

    private final ShopFacadeService shopFacadeService;

    @GetMapping
    public ApiResult<List<MapResponseDto.HomeDto>> fetchShops(@RequestBody @Validated MapRequestDto.LocationDto locationDto){
        return ApiResult.onSuccess(shopFacadeService.findShopsByLocation(locationDto));
    }

    @PostMapping("/{shopId}")
    public ApiResult<MapResponseDto.BookmarkResponseDto> addBookmark(@SessionAttribute(name = "user") UserResDto.InfoDto userResDto, @PathVariable(name = "shopId") Long shopId){
        return ApiResult.onSuccess(shopFacadeService.addBookmark(userResDto.getId(), shopId));
    }

    @DeleteMapping("/{shopId}")
    public ApiResult<MapResponseDto.BookmarkResponseDto> deleteBookmark(@SessionAttribute(name = "user") UserResDto.InfoDto userResDto, @PathVariable(name = "shopId") Long shopId){
        return ApiResult.onSuccess(shopFacadeService.deleteBookmark(userResDto.getId(), shopId));
    }
}
