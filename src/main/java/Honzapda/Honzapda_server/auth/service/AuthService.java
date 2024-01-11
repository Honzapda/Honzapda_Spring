package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
import Honzapda.Honzapda_server.auth.data.dto.AuthResponseDto;
import Honzapda.Honzapda_server.user.data.entity.User;

public interface AuthService {
    boolean isEMail(String email);
    void registerUser(AuthRequestDto.Register request);
    String genName();
    AuthResponseDto.Login loginUser(AuthRequestDto.Login request);
}
