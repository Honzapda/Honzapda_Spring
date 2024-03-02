package Honzapda.Honzapda_server.auth.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.code.status.SuccessStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.auth.service.AuthService;
import Honzapda.Honzapda_server.user.data.UserConverter;
import Honzapda.Honzapda_server.user.data.dto.*;
import Honzapda.Honzapda_server.user.data.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * id 중복 검사 api
     * 실패 : 에러
     * 성공 : True
     */
    @PostMapping("/checkId")
    public ApiResult<Boolean>
    checkId(@RequestBody @Valid UserDto.EmailDto request) {
        return ApiResult.onSuccess(true);
    }

    @PostMapping("/register")
    public ApiResult<UserResDto.InfoDto>
    register(@RequestBody @Valid UserDto.JoinDto request) {
        User newUser = authService.registerUser(request);
        return ApiResult.onSuccess(SuccessStatus._CREATED, UserConverter.toUserInfo(newUser));
    }

    @PostMapping("/login")
    public ApiResult<UserResDto.InfoDto>
    login(HttpServletRequest httpRequest, @RequestBody @Valid UserDto.LoginDto request) {

        UserResDto.InfoDto userResDto = UserConverter.toUserInfo(authService.loginUser(request));

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("user", userResDto);
        session.setMaxInactiveInterval(60 * 30);

        return ApiResult.onSuccess(userResDto);
    }

    @PostMapping("/findPassword")
    public ApiResult<Boolean>
    findPassword(@RequestBody @Valid UserDto.FindPwDto request) {
        authService.sendTempPasswordByEmail(request.getEmail());
        return ApiResult.onSuccess(true);
    }

    @PostMapping("/apple")
    public ApiResult<?> appleLogin(HttpServletRequest request) {

        String authorizationCode = request.getParameter("code");
        Object serviceDto = authService.appleLogin(authorizationCode);

        if (serviceDto.getClass().equals(UserResDto.InfoDto.class)) {
            UserResDto.InfoDto userResDto = (UserResDto.InfoDto) serviceDto;

            HttpSession session = request.getSession(true);
            session.setAttribute("user", userResDto);
            session.setMaxInactiveInterval(60 * 30);
            return ApiResult.onSuccess(userResDto);
        }
        else{
            return ApiResult.onFailure(ErrorStatus._UNAUTHORIZED,(AppleJoinDto) serviceDto);
        }
    }


    @GetMapping("/logout")
    public ApiResult<String> logout(HttpSession session) {
        try {
            session.invalidate();
            return ApiResult.onSuccess("logout Success!");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/revoke")
    public ApiResult<?> revoke(@SessionAttribute UserResDto.InfoDto user) {

        try {
            authService.revoke(user);
            return ApiResult.onSuccess("Revoke Success!");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/expired")
    public void expired() {
            throw new GeneralException(ErrorStatus.SESSION_EXPIRED);
    }

}
