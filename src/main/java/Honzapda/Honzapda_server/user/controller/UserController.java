package Honzapda.Honzapda_server.user.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferResDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferJoinDto;
import Honzapda.Honzapda_server.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ApiResult<?> registerUser(@RequestBody @Valid UserJoinDto request){
        try {
            UserResDto userResDto = userService.registerUser(request);
            return ApiResult.onSuccess(userResDto);
        } catch (Exception e) {
            return ApiResult.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @GetMapping("/{userId}")
    public ApiResult<?> searchUser(@PathVariable(name = "userId") Long userId){
        try {
            UserResDto userResDto = userService.searchUser(userId);
            return ApiResult.onSuccess(userResDto);
        } catch (NoSuchElementException e) {
            return ApiResult.onFailure(ErrorStatus._BAD_REQUEST.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            return ApiResult.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping("/{userId}")
    public ApiResult<?> updateUser(@RequestBody @Valid UserJoinDto request, @PathVariable(name = "userId") Long userId){
        try {
            UserResDto userResDto = userService.updateUser(request, userId);
            return ApiResult.onSuccess(userResDto);
        } catch (NoSuchElementException e) {
            return ApiResult.onFailure(ErrorStatus._BAD_REQUEST.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            return ApiResult.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @GetMapping("/prefer")
    public ApiResult<?> searchUserPrefer(@SessionAttribute UserResDto user){

        try {
            UserPreferResDto preferNameList = userService.searchUserPrefer(user.getId());
            return ApiResult.onSuccess(preferNameList);
        } catch (NoSuchElementException e) {
            return ApiResult.onFailure(ErrorStatus._BAD_REQUEST.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            return ApiResult.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping("/prefer")
    public ApiResult<?> registerUserPrefer(@SessionAttribute UserResDto user, @RequestBody UserPreferJoinDto request){
        try {
            Boolean result = userService.registerUserPrefer(user.getId(), request.getPreferNameList());
            return ApiResult.onSuccess(result);
        } catch (NoSuchElementException e) {
            return ApiResult.onFailure(ErrorStatus._BAD_REQUEST.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            return ApiResult.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PatchMapping("/prefer")
    public ApiResult<?> updateUserPrefer(@SessionAttribute UserResDto user, @RequestBody UserPreferJoinDto request){
        try {
            Boolean result = userService.updateUserPrefer(user.getId(), request.getPreferNameList());
            return ApiResult.onSuccess(result);
        } catch (NoSuchElementException e) {
            return ApiResult.onFailure(ErrorStatus._BAD_REQUEST.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            return ApiResult.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        }
    }
}
