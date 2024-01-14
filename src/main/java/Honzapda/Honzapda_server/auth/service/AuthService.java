package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserLoginDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    User getUserByEMail(String email);

    User getUserByNickName(String nickname);

    User registerUser(UserJoinDto request);

    User loginUser(UserLoginDto request);

    ResponseEntity<?> appleLogin(String authorizationCode);

    void revoke(UserResDto userResDto);
/*
    UserResDto join(UserJoinDto userJoinDto);

    UserResDto login(UserLoginDto userLoginDto);
 */
}

