package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.user.validation.annotation.UniqueEmail;
import Honzapda.Honzapda_server.user.validation.annotation.UnqiueNickName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

public class UserDto {
    @Getter
    public static class EmailDto {
        @Email
        @UniqueEmail
        private String email;
    }

    @Getter
    public static class JoinDto {

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

    @Getter
    public static class LoginDto {
        //@Email TODO: 데모데이 이후 활성화
        private String email;
        private String password;
    }
    @Getter
    public static class FindPwDto {
        @Email
        private String email;
    }
    @Getter
    public static class PatchUserPwDto {
        @NotBlank
        private String password;
    }


}
