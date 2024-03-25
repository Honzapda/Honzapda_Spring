package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.user.validation.annotation.UniqueEmail;
import Honzapda.Honzapda_server.user.validation.annotation.UnqiueNickName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

public class UserDto {

    @Getter
    public static class EmailDto {
        @Email
        @UniqueEmail
        @Schema(example = "example@gmail.com")
        private String email;
    }

    @Getter
    public static class JoinDto {

        @NotBlank
        @UnqiueNickName
        @Schema(example = "김관주")
        private String name;
        @Email
        @UniqueEmail
        @Schema(example = "kkj6235@ajou.ac.kr")
        private String email;
        @NotBlank
        @Schema(example = "6235")
        private String password;

        private String socialToken;
    }

    @Getter
    public static class UserLoginDto {
        @Email
        @Schema(example = "kkj6235@ajou.ac.kr")
        private String email;
        @Schema(example = "6235")
        private String password;
    }
    @Getter
    public static class FindPwDto {
        @Email
        @Schema(example = "kkj6235@ajou.ac.kr")
        private String email;
    }
    @Getter
    public static class PatchUserPwDto {
        @NotBlank
        @Schema(example = "6235")
        private String password;
    }


}
