package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.auth.data.AuthConverter;
import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
import Honzapda.Honzapda_server.auth.repository.AuthRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService{
    private final AuthRepository authRepository;
    @Override
    public boolean isEMail(String email) {
        return authRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void registerUser(AuthRequestDto.Register request) {
        User newUser = AuthConverter.toUser(request,genName());
        authRepository.save(newUser);
    }
    @Override
    public String genName() {
        String name;
        do {
            name = UUID.randomUUID().toString().substring(0, 24);
        }while(authRepository.existsByName(name));

        return name;
    }
}
