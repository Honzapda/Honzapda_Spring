package Honzapda.Honzapda_server.auth.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.SuccessStatus;
import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
import Honzapda.Honzapda_server.auth.data.dto.AuthResponseDto;
import Honzapda.Honzapda_server.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    checkId(@RequestBody @Valid AuthRequestDto.GetEmail request) {
        return ApiResult.onSuccess(true);
    }
    /**
     * 회원가입 api
     * 실패 : 에러
     * 성공 : True
     */
    @PostMapping("/register")
    public ApiResult<Boolean>
    register(@RequestBody @Valid AuthRequestDto.Register request) {
        authService.registerUser(request);
        return ApiResult.onSuccess(SuccessStatus._CREATED,true);
    }
    /**
     * 로그인 api
     * 실패 : 에러
     * 성공 : True
     */
    @PostMapping("/login")
    public ApiResult<AuthResponseDto.Login>
    register(@RequestBody @Valid AuthRequestDto.Login request) {

        return ApiResult.onSuccess(authService.loginUser(request));
    }
}
