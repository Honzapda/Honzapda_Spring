package Honzapda.Honzapda_server.userHelpInfo.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.service.UserHelpInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userHelpInfo")
@Slf4j
public class UserHelpInfoController {

    private final UserHelpInfoService userHelpInfoService;

    @PostMapping("/")
    public ApiResult<?> registerUserHelpInfo(
            @SessionAttribute(name = "user") UserResDto userResDto,
            @RequestParam Long shopId,
            @RequestBody @Valid UserHelpInfoRequestDto.CreateDto requestDto){
        return ApiResult.onSuccess(userHelpInfoService.registerUserHelpInfo(userResDto.getId(), shopId, requestDto));
    }

    @GetMapping("/")
    public ApiResult<?> getUserHelpInfos(
            @RequestParam Long shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ApiResult.onSuccess(userHelpInfoService.getUserHelpInfoListDto(shopId, PageRequest.of(page, size)));
    }

    @GetMapping("/image")
    public ApiResult<?> getUserHelpInfoImages(
            @RequestParam Long shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ApiResult.onSuccess(userHelpInfoService.getUserHelpInfoImageListDto(shopId,PageRequest.of(page,size)));

    }
}
