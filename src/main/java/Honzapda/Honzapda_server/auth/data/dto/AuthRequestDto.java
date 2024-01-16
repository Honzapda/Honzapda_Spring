package Honzapda.Honzapda_server.auth.data.dto;

import Honzapda.Honzapda_server.auth.validation.annotation.CheckId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/*
특수) api -> 회원가입 : register
일반) method + 대상 -> 리뷰 리스트 조회 : GetReviewList
 */
public class AuthRequestDto {
    @Getter
    public static class GetEmail{

        @Size(max=50)
        @CheckId
        @NotBlank
        @Email
        String email;
    }
    @Getter
    public static class Register{

        @Email
        @CheckId
        @NotBlank
        @Size(max=50)
        String email;
        @NotBlank
        @Size(max=50)
        String password;
    }
    @Getter
    public static class Login{

        @Email
        @NotBlank
        String email;
        @NotBlank
        String password;
    }

}
