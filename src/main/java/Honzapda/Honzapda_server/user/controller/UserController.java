package Honzapda.Honzapda_server.user.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.SuccessStatus;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.dto.LikeResDto;
import Honzapda.Honzapda_server.user.data.dto.UserDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ApiResult<?> findUser(@SessionAttribute UserResDto.InfoDto user){
        return ApiResult.onSuccess(userService.findUser(user.getId()));
    }

    @PostMapping("")
    public ApiResult<?> updateUser(@RequestBody @Valid UserDto.JoinDto userJoinDto, @SessionAttribute @Parameter(hidden = true) UserResDto.InfoDto user){
        return ApiResult.onSuccess(userService.updateUser(userJoinDto, user.getId()));
    }

    @PostMapping(value = "/profileImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult<?> updateUserImage(@RequestPart MultipartFile image,@SessionAttribute @Parameter(hidden = true) UserResDto.InfoDto user) throws Exception {
        return ApiResult.onSuccess(userService.updateUserImage(image,user.getId()));
    }




    @PostMapping("/password")
    public ApiResult<?> patchPassword(@RequestBody @Valid UserDto.PatchUserPwDto userPwDto, @SessionAttribute("user") @Parameter(hidden = true) UserResDto.InfoDto user){
        /*
         * flow : 1. 기존 비밀번호 재확인 -> 동일하면, 2.신규 비밀번호 입력으로 변경
         * 일단, MY에 없어서, 2번만 구현하였습니다.
         */
        return ApiResult.onSuccess(userService.patchPassword(userPwDto, user.getId()));
    }

    @PostMapping("/likeshops")
    public ApiResult<Slice<ShopResponseDto.likeDto>> getLikeShops(@SessionAttribute("user") @Parameter(hidden = true) UserResDto.InfoDto userResDto
            , @RequestBody @Valid ShopRequestDto.SearchDto request,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return ApiResult.onSuccess(userService.getLikeShops(userResDto.getId(), request, PageRequest.of(page, size)));
    }

    @PostMapping("/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<LikeResDto> likeShop(@RequestParam Long shopId, @SessionAttribute("user") UserResDto.InfoDto userResDto){
        return ApiResult.onSuccess(SuccessStatus._CREATED,userService.likeShop(shopId, userResDto.getId()));
    }
    @DeleteMapping("/likes")
    public ApiResult<LikeResDto> deleteLikeShop(@RequestParam Long shopId,@SessionAttribute("user") UserResDto.InfoDto userResDto) {
        return ApiResult.onSuccess(userService.deleteLikeShop(shopId, userResDto.getId()));
    }
    @GetMapping("/prefer")
    public ApiResult<?> searchUserPrefer(@SessionAttribute @Parameter(hidden = true) UserResDto.InfoDto user){
        UserPreferDto preferNameList = userService.searchUserPrefer(user.getId());
        return ApiResult.onSuccess(preferNameList);
    }

    @PostMapping("/prefer")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<?> registerUserPrefer(@RequestBody UserPreferDto userPreferDto, @SessionAttribute @Parameter(hidden = true) UserResDto.InfoDto user){
        return ApiResult.onSuccess(SuccessStatus._CREATED,userService.registerUserPrefer(user.getId(), userPreferDto.getPreferNameList()));
    }

    @PatchMapping("/prefer")
    public ApiResult<?> updateUserPrefer(@RequestBody UserPreferDto userPreferDto,@SessionAttribute @Parameter(hidden = true) UserResDto.InfoDto user){
        return ApiResult.onSuccess(userService.updateUserPrefer(user.getId(), userPreferDto.getPreferNameList()));
    }

}
