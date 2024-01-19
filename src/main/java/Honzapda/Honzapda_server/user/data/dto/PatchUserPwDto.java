package Honzapda.Honzapda_server.user.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PatchUserPwDto {
    @NotBlank
    private String password;
}
