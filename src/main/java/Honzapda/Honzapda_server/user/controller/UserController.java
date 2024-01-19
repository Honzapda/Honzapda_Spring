package Honzapda.Honzapda_server.user.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;

import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.dto.LikeResDto;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferResDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferJoinDto;

import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


    @GetMapping("/likeshops")
    public ApiResult<List<ShopResponseDto.searchDto>> getLikeshops(@SessionAttribute("user") UserResDto userResDto) {
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
