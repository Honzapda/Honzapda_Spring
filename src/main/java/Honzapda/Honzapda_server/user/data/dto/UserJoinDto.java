package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.user.validation.annotation.UniqueEmail;
import Honzapda.Honzapda_server.user.validation.annotation.UnqiueNickName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserJoinDto {

    @NotBlank
    @UnqiueNickName
    private String name;
    @Email
    @UniqueEmail
    private String email;
    @NotBlank
    private String password;
    private String socialToken;
}
