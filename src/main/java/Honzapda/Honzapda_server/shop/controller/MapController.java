package Honzapda.Honzapda_server.shop.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.SuccessStatus;
import Honzapda.Honzapda_server.shop.data.dto.HomeDtos;
import Honzapda.Honzapda_server.shop.data.dto.MapRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.MapResponseDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.facade.ShopFacadeService;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
@Slf4j
public class MapController {

    private final ShopFacadeService shopFacadeService;

    @PostMapping
    public ApiResult<HomeDtos> fetchShops(@RequestBody @Validated MapRequestDto.LocationDto locationDto) {
        return ApiResult.onSuccess(shopFacadeService.findShopsByLocation(locationDto));
    }


    @PostMapping("/shop/{shopId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<MapResponseDto.BookmarkResponseDto> addBookmark(@SessionAttribute(name = "user") UserResDto.InfoDto userResDto, @PathVariable(name = "shopId") Long shopId) {
        return ApiResult.onSuccess(SuccessStatus._CREATED,shopFacadeService.addBookmark(userResDto.getId(), shopId));
    }

    @DeleteMapping("/shop/{shopId}")
    public ApiResult<MapResponseDto.BookmarkResponseDto> deleteBookmark(@SessionAttribute(name = "user") UserResDto.InfoDto userResDto, @PathVariable(name = "shopId") Long shopId) {
        return ApiResult.onSuccess(shopFacadeService.deleteBookmark(userResDto.getId(), shopId));
    }

    @GetMapping("/shop")
    public ApiResult<Slice<MapResponseDto.UserBookmarkShopResponseDto>> fetchBookmarks(@SessionAttribute(name = "user") UserResDto.InfoDto userResDto, Pageable pageable) {
        return ApiResult.onSuccess(shopFacadeService.findBookmarks(userResDto.getId(), pageable));
    }
}
