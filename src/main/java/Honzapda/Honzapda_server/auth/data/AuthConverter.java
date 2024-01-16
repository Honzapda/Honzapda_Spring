package Honzapda.Honzapda_server.auth.data;

import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
import Honzapda.Honzapda_server.auth.data.dto.AuthResponseDto;
import Honzapda.Honzapda_server.user.data.entity.User;

import java.util.UUID;

/*
1. post api 요청 -> to + '엔티티 명' ex) toUser
1. post api 응답 -> to + '엔티티 명' + dto ex)toUserDto

2. patch api 요청  -> to + patch + '엔티티 명' ex) toPatchUser
2. patch api 응답 -> to + patch + '엔티티 명' + dto ex) toPatchUserDto

3. get api 응답 -> to + get + '조회 대상' + dto ex) toGetUserPreferDto

4. delete api 응답 -> to + delete + '삭제 대상' + dto ex)toDeleteUserDto
*/
public class AuthConverter {
    public static User toUser(AuthRequestDto.Register request, String name){

        return User.builder()
                .name(name)
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
