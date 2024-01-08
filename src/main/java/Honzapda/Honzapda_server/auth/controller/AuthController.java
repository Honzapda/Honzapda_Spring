package Honzapda.Honzapda_server.auth.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.SuccessStatus;
import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
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
     * id 중복 검사
     * 실패 : 에러
     * 성공 : True
     */
    @PostMapping("/checkId")
    public ApiResult<Boolean>
    memberJoin(@RequestBody @Valid AuthRequestDto.GetEmail request) {
        return ApiResult.onSuccess(true);
    }
}
