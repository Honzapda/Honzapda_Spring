package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.user.data.entity.MemberType;
import Honzapda.Honzapda_server.user.data.entity.SocialType;
import lombok.Getter;

/*
특수) api + dto -> 회원가입 dto : registerDto
일반) method + 대상 + dto -> 리뷰 리스트 조회 dto : GetReviewListDto
 */
public class UserRequestDto {

    @Getter
    public static class registerDto{
        private String email;
        private String password;
        private String address;
        private String address_spec;
        private SocialType socialType;
        private MemberType memberType;
        private String name;

    }

    @Getter
    public static class updateDto{
        private String address;
        private String address_spec;
        private MemberType memberType;
        private String name;
    }
}
