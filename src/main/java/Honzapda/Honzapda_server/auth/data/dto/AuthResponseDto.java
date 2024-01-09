package Honzapda.Honzapda_server.auth.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
특수) api + dto -> 회원가입 dto : registerDto
일반) method + 대상 + dto -> 리뷰 리스트 조회 dto : GetReviewList
*/
public class AuthResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login{
        Long memberId;
    }
}
