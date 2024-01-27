package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserLoginDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;

public interface AuthService {

    User getUserByEMail(String email);

    void sendTempPasswordByEmail(String email);

    User registerUser(UserJoinDto request);

    User loginUser(UserLoginDto request);

    Object appleLogin(String authorizationCode);

    void revoke(UserResDto userResDto);
}

