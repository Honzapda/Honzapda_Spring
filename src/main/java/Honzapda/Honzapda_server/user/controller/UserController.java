package Honzapda.Honzapda_server.user.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.user.data.dto.UserRequestDto;
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

    @GetMapping("/{userId}")
    public ApiResult<UserResponseDto.searchDto> searchUser(@PathVariable(name = "userId") Long userId){
        return ApiResult.onSuccess(userService.searchUser(userId));
    }

    @PostMapping("/{userId}")
    public ApiResult<UserResponseDto.searchDto> updateUser(@RequestBody @Valid UserRequestDto.updateDto request, @PathVariable(name = "userId") Long userId){
        return ApiResult.onSuccess(userService.updateUser(request, userId));
    }
}
