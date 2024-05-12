package Honzapda.Honzapda_server.review.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.SuccessStatus;
import Honzapda.Honzapda_server.review.data.dto.ReviewImageResponseDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewRequestDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.service.ReviewService;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<ReviewResponseDto.ReviewDto> registerReview(
            @SessionAttribute @Parameter(hidden = true) UserResDto.InfoDto user,
            @RequestParam Long shopId,
            @RequestBody @Valid ReviewRequestDto.ReviewRegisterDto requestDto){
        return ApiResult.onSuccess(SuccessStatus._CREATED,reviewService.registerReview(user.getId(), shopId, requestDto));
    }


    @GetMapping("")
    public ApiResult<ReviewResponseDto.ReviewListDto> getReviews(
            @RequestParam Long shopId,
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

    @DeleteMapping("/{reviewId}")
    public ApiResult<String> deleteReview(
            @SessionAttribute UserResDto.InfoDto user,
            @PathVariable Long reviewId) {

        reviewService.deleteReview(user.getId(), reviewId);
        return ApiResult.onSuccess("리뷰를 삭제했습니다.");
    }
}
