package Honzapda.Honzapda_server.user.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferResDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ApiResult<UserResponseDto.searchDto> searchUser(@SessionAttribute UserResDto user){
        return ApiResult.onSuccess(userService.searchUser(user.getId()));
    }

    @PostMapping("/")
    public ApiResult<UserResponseDto.searchDto> updateUser(@RequestBody @Valid UserRequestDto.updateDto request, @SessionAttribute UserResDto user){
        return ApiResult.onSuccess(userService.updateUser(request, user.getId()));
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
