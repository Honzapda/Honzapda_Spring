package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserLoginDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> appleLogin(String authorizationCode);

    void revoke(UserResDto userResDto);

    UserResDto join(UserJoinDto userJoinDto);

    UserResDto login(UserLoginDto userLoginDto);
}
