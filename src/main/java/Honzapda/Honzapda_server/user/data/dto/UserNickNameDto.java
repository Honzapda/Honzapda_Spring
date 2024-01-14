package Honzapda.Honzapda_server.user.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
@Getter
public class UserNickNameDto {
    @NotBlank
    private String name;
}
