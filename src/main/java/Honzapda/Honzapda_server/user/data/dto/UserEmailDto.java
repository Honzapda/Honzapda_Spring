package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.user.validation.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserEmailDto {
    @Email
    @UniqueEmail
    private String email;
}
