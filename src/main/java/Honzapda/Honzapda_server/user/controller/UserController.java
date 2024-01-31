package Honzapda.Honzapda_server.user.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.dto.LikeResDto;
import Honzapda.Honzapda_server.user.data.dto.UserDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ApiResult<?> findUser(@SessionAttribute UserResDto.InfoDto user){
        return ApiResult.onSuccess(userService.findUser(user.getId()));
    }

    @PostMapping("/")
    public ApiResult<?> updateUser(@RequestBody @Valid UserDto.JoinDto userJoinDto, @SessionAttribute UserResDto.InfoDto user){
        return ApiResult.onSuccess(userService.updateUser(userJoinDto, user.getId()));
    }

    @PostMapping("/profileImage")
    public ApiResult<?> updateUserImage(@RequestPart MultipartFile image,@SessionAttribute UserResDto.InfoDto user) throws Exception {
        return ApiResult.onSuccess(userService.updateUserImage(image,user.getId()));
    }




    @PostMapping("/password")
    public ApiResult<?> patchPassword(@RequestBody @Valid UserDto.PatchUserPwDto userPwDto, @SessionAttribute("user") UserResDto.InfoDto user){
        /*
         * flow : 1. 기존 비밀번호 재확인 -> 동일하면, 2.신규 비밀번호 입력으로 변경
         * 일단, MY에 없어서, 2번만 구현하였습니다.
         */
        return ApiResult.onSuccess(userService.patchPassword(userPwDto, user.getId()));
    }

    @GetMapping("/likeshops")
    public ApiResult<List<ShopResponseDto.SearchDto>> getLikeShops(@SessionAttribute("user") UserResDto.InfoDto userResDto) {
        return ApiResult.onSuccess(userService.getLikeShops(userResDto.getId()));
    }

    @PostMapping("/likes")
    public ApiResult<LikeResDto> likeShop(@RequestParam Long shopId, @SessionAttribute("user") UserResDto.InfoDto userResDto){
        return ApiResult.onSuccess(userService.likeShop(shopId, userResDto.getId()));
    }
    @DeleteMapping("/likes")
    public ApiResult<LikeResDto> deleteLikeShop(@RequestParam Long shopId,@SessionAttribute("user") UserResDto.InfoDto userResDto) {
        return ApiResult.onSuccess(userService.deleteLikeShop(shopId, userResDto.getId()));
    }
    @GetMapping("/prefer")
    public ApiResult<?> searchUserPrefer(@SessionAttribute UserResDto.InfoDto user){
        UserPreferDto preferNameList = userService.searchUserPrefer(user.getId());
        return ApiResult.onSuccess(preferNameList);
    }

    @PostMapping("/prefer")
    public ApiResult<?> registerUserPrefer(@SessionAttribute UserResDto.InfoDto user, @RequestBody UserPreferDto request){
        Boolean result = userService.registerUserPrefer(user.getId(), request.getPreferNameList());
        return ApiResult.onSuccess(result);
    }

    @PatchMapping("/prefer")
    public ApiResult<?> updateUserPrefer(@SessionAttribute UserResDto.InfoDto user, @RequestBody UserPreferDto request){
        Boolean result = userService.updateUserPrefer(user.getId(), request.getPreferNameList());
        return ApiResult.onSuccess(result);

    }

}
