package Honzapda.Honzapda_server.user.data.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserLoginDto {
    @Email
    private String email;
    private String password;
}
