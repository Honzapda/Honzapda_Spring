package Honzapda.Honzapda_server.user.data.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class FindPwDto {
    @Email
    private String email;
}
