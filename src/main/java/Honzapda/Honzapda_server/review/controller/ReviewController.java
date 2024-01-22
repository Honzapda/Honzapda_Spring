package Honzapda.Honzapda_server.review.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.review.data.dto.ReviewImageResponseDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewRequestDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.service.ReviewService;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/{shopId}")
    public ApiResult<ReviewResponseDto.ReviewListDto> getReviews(
            @PathVariable(name = "shopId") Long shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ApiResult.onSuccess(reviewService.getReviewListDto(shopId, PageRequest.of(page, size)));
    }

    @GetMapping("/image")
    public ApiResult<ReviewImageResponseDto.ImageListDto> getReviewImages(
            @RequestParam Long shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ApiResult.onSuccess(reviewService.getReviewImageListDto(shopId,PageRequest.of(page,size)));

    }
}
