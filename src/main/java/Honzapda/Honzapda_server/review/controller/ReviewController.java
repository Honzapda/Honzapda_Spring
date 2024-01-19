package Honzapda.Honzapda_server.review.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.review.data.dto.ReviewRequestDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.service.ReviewService;
import Honzapda.Honzapda_server.shop.data.dto.MapRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.MapResponseDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.facade.ShopFacadeService;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{shopId}")
    public ApiResult<ReviewResponseDto.ReviewDto> registerReview(
            @SessionAttribute(name = "user") UserResDto userResDto,
            @PathVariable(name = "shopId") Long shopId,
            @RequestBody @Valid ReviewRequestDto.ReviewRegisterDto requestDto){
        return ApiResult.onSuccess(reviewService.registerReview(userResDto.getId(), shopId, requestDto));
    }
}
