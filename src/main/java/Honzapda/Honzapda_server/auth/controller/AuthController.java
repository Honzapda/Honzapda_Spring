package Honzapda.Honzapda_server.auth.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.code.status.SuccessStatus;
import Honzapda.Honzapda_server.auth.service.AuthService;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.facade.ShopFacadeService;
import Honzapda.Honzapda_server.user.data.dto.*;
import Honzapda.Honzapda_server.user.data.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final ShopFacadeService shopFacadeService;

    /**
     * id 중복 검사 api
     * 실패 : 에러
     * 성공 : True
     */
    @PostMapping("/checkId")
    public ApiResult<Boolean>
    checkId(@RequestBody @Valid UserEmailDto request) {
        return ApiResult.onSuccess(true);
    }

    @PostMapping("/register")
    public ApiResult<UserResDto>
    register(@RequestBody @Valid UserJoinDto request) {
        User newUser = authService.registerUser(request);
        return ApiResult.onSuccess(SuccessStatus._CREATED, UserResDto.toDTO(newUser));
    }

    @PostMapping("/login")
    public ApiResult<UserResDto>
    login(HttpServletRequest httpRequest, @RequestBody @Valid UserLoginDto request) {

        UserResDto userResDto = UserResDto.toDTO(authService.loginUser(request));
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("user", userResDto);
        session.setMaxInactiveInterval(60 * 30);

        return ApiResult.onSuccess(userResDto);
    }

    @PostMapping("/findPassword")
    public ApiResult<Boolean>
    findPassword(@RequestBody @Valid FindPwDto request) {

        authService.sendTempPasswordByEmail(request.getEmail());
        return ApiResult.onSuccess(true);
    }

    @PostMapping("/apple")
    public ApiResult<?> appleLogin(HttpServletRequest request) {

        String authorizationCode = request.getParameter("code");
        Object serviceDto = authService.appleLogin(authorizationCode);

        if (serviceDto.getClass().equals(UserResDto.class)) {
            UserResDto userResDto = (UserResDto) serviceDto;

            HttpSession session = request.getSession(true);
            session.setAttribute("user", userResDto);
            session.setMaxInactiveInterval(60 * 30);
            return ApiResult.onSuccess(userResDto);
        }
        else{
            return ApiResult.onFailure(ErrorStatus._UNAUTHORIZED,(AppleJoinDto) serviceDto);
        }
    }

    @PostMapping("/register/shop")
    public ApiResult<?> registerShop(
            @RequestBody @Valid ShopRequestDto.RegisterDto request) {
        ShopResponseDto.SearchDto result = shopFacadeService.registerShop(request);
        return ApiResult.onSuccess(result);
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
    public ApiResult<?> revoke(@SessionAttribute UserResDto user) {

        try {
            authService.revoke(user);
            return ApiResult.onSuccess("Revoke Success!");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
