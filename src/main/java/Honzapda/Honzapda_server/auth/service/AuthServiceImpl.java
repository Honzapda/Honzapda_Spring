package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.handler.LoginHandler;
import Honzapda.Honzapda_server.auth.data.AuthConverter;
import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
import Honzapda.Honzapda_server.auth.data.dto.AuthResponseDto;
import Honzapda.Honzapda_server.auth.repository.AuthRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        String name = null;
        List<String> adjective = Arrays.asList(
                "행복한", "슬픈", "게으른", "슬기로운", "수줍은",
                "그리운", "더러운", "섹시한", "배고픈", "배부른",
                "부자", "재벌", "웃고있는", "깨발랄한", "프로",
                "잔잔한","아늑한","시끄러운","코딩","깨끗한"
        );
        List<String> midName = Arrays.asList(
                "카페","커피머신","조명","가격","책상","공간","짐깅","로딩",
                "웅이","젬마","제로","휘리릭","맥구","체리","이제");
        do {
            String number = (int)(Math.random() * 99)+1 +"";
            Collections.shuffle(adjective);
            Collections.shuffle(midName);
            name = adjective.get(0)+midName.get(0)+number;

        }while(authRepository.existsByName(name));
        return name;
    }
    @Override
    public AuthResponseDto.Login loginUser(AuthRequestDto.Login request) {

        User getUser = authRepository.findByEmail(request.getEmail())
                .filter(find -> find.getPassword().equals(request.getPassword()))
                .orElseThrow(() -> new LoginHandler(ErrorStatus.LOGIN_NOT_MATCH));

        return AuthResponseDto.Login.builder()
                .memberId(getUser.getId())
                .build();
    }
}
