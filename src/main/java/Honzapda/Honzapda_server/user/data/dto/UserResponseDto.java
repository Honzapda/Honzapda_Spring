package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.user.data.entity.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
특수) api + dto -> 회원가입 dto : registerDto
일반) method + 대상 + dto -> 리뷰 리스트 조회 dto : GetReviewListDto
*/
public class UserResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class searchDto{
        Long id;
        String address;
        String address_spec;
        String name;
        MemberType memberType;
    }
}
