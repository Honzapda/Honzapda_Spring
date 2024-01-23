package Honzapda.Honzapda_server.user.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.dto.*;
import Honzapda.Honzapda_server.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ApiResult<?> searchUser(@SessionAttribute UserResDto user){
        return ApiResult.onSuccess(userService.searchUser(user.getId()));
    }

    @PostMapping("/")
    public ApiResult<?> updateUser(@RequestBody @Valid UserJoinDto userJoinDto, @SessionAttribute UserResDto user){
        return ApiResult.onSuccess(userService.updateUser(userJoinDto, user.getId()));
    }
    @PostMapping("/password")
    public ApiResult<?> patchPassword(@RequestBody @Valid PatchUserPwDto userPwDto, @SessionAttribute("user") UserResDto user){
        /*
         * flow : 1. 기존 비밀번호 재확인 -> 동일하면, 2.신규 비밀번호 입력으로 변경
         * 일단, MY에 없어서, 2번만 구현하였습니다.
         */
        return ApiResult.onSuccess(userService.patchPassword(userPwDto, user.getId()));
    }

    @GetMapping("/likeshops")
    public ApiResult<List<ShopResponseDto.SearchDto>> getLikeshops(@SessionAttribute("user") UserResDto userResDto) {
        return ApiResult.onSuccess(userService.getLikeShops(userResDto.getId()));
    }

    @PostMapping("/likes")
    public ApiResult<LikeResDto> likeShop(@RequestParam Long shopId, @SessionAttribute("user") UserResDto userResDto){
        return ApiResult.onSuccess(userService.likeShop(shopId, userResDto.getId()));
    }
    @DeleteMapping("/likes")
    public ApiResult<LikeResDto> deleteLikeShop(@RequestParam Long shopId,@SessionAttribute("user") UserResDto userResDto) {
        return ApiResult.onSuccess(userService.deleteLikeShop(shopId, userResDto.getId()));
    }
    @GetMapping("/prefer")
    public ApiResult<?> searchUserPrefer(@SessionAttribute UserResDto user){
        UserPreferResDto preferNameList = userService.searchUserPrefer(user.getId());
        return ApiResult.onSuccess(preferNameList);
    }

    @PostMapping("/prefer")
    public ApiResult<?> registerUserPrefer(@SessionAttribute UserResDto user, @RequestBody UserPreferJoinDto request){
        Boolean result = userService.registerUserPrefer(user.getId(), request.getPreferNameList());
        return ApiResult.onSuccess(result);
    }

    @PatchMapping("/prefer")
    public ApiResult<?> updateUserPrefer(@SessionAttribute UserResDto user, @RequestBody UserPreferJoinDto request){
        Boolean result = userService.updateUserPrefer(user.getId(), request.getPreferNameList());
        return ApiResult.onSuccess(result);

    }

}
