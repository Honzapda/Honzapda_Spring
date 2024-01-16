package Honzapda.Honzapda_server.user.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserRequestDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.dto.UserResponseDto;
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
}
