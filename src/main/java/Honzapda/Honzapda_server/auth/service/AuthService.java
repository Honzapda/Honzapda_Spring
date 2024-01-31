package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.user.data.dto.UserDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;

public interface AuthService {

    User getUserByEMail(String email);

    void sendTempPasswordByEmail(String email);

    User registerUser(UserDto.JoinDto request);

    User loginUser(UserDto.LoginDto request);

    Object appleLogin(String authorizationCode);

    void revoke(UserResDto.InfoDto userResDto);
}

