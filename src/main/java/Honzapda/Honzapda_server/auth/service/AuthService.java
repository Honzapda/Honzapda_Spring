package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;

public interface AuthService {
    boolean isEMail(String email);
    void registerUser(AuthRequestDto.Register request);
    String genName();
}
