package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.user.data.entity.User;
import lombok.*;

@Data
@Builder
public class UserResDto {

    private Long id;

    private String name;

    private String email;

    private User.SignUpType signUpType;

    public static UserResDto toDTO(User user){
        return UserResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .signUpType(user.getSignUpType())
                .build();
    }

}
