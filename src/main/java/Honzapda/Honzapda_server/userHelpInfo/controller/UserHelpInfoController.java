package Honzapda.Honzapda_server.userHelpInfo.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.review.data.dto.ReviewImageResponseDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewRequestDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.service.ReviewService;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.service.UserHelpInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import static Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userhelpinfo")
@Slf4j
public class UserHelpInfoController {

    //TODO: 삭제
    private final ReviewService reviewService;

    private final UserHelpInfoService userHelpInfoService;

    @PostMapping("/shop/{shopId}")
    public ApiResult<ReviewResponseDto.ReviewDto> registerUserHelpInfo(
            @SessionAttribute(name = "user") UserResDto userResDto,
            @PathVariable Long shopId,
            @RequestBody @Valid UserHelpInfoCreateRequest requestDto){
        return ApiResult.onSuccess(userHelpInfoService.registerUserHelpInfo(userResDto.getId(), shopId, requestDto));
    }


    @GetMapping("/")
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
}
