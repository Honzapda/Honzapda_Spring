package Honzapda.Honzapda_server.user.data;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferResDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

public class UserConverter {

    public static User toUser(UserJoinDto request, PasswordEncoder encoder){
        return User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .name(request.getName())
                .inactiveDate(LocalDateTime.now())
                .build();
    }

    public static UserResDto toUserResponse(User user) {
        return UserResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .signUpType(user.getSignUpType())
                .build();
    }

    public static UserPreferResDto toUserPreferResponse(List<String> preferNameList){
        return UserPreferResDto.builder()
                .preferNameList(preferNameList)
                .build();
    }
}
