package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
import Honzapda.Honzapda_server.auth.data.dto.AuthResponseDto;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserLoginDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    boolean isEMail(String email);

    void registerUser(AuthRequestDto.Register request);

    String genName();

    AuthResponseDto.Login loginUser(AuthRequestDto.Login request);

    ResponseEntity<?> appleLogin(String authorizationCode);

    void revoke(UserResDto userResDto);

    UserResDto join(UserJoinDto userJoinDto);

    UserResDto login(UserLoginDto userLoginDto);
}

