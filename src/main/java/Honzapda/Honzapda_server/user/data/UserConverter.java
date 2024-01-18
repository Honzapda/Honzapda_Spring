package Honzapda.Honzapda_server.user.data;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferResDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;

import java.time.LocalDateTime;
import java.util.List;

/*
1. post api 요청 -> to + '엔티티 명' ex) toUser
1. post api 응답 -> to + '엔티티 명' + dto ex)toUserDto

2. patch api 요청  -> to + patch + '엔티티 명' ex) toPatchUser
2. patch api 응답 -> to + patch + '엔티티 명' + dto ex) toPatchUserDto

3. get api 응답 -> to + get + '조회 대상' + dto ex) toGetUserPreferDto

4. delete api 응답 -> to + delete + '삭제 대상' + dto ex)toDeleteUserDto
*/
public class UserConverter {

    public static User toUser(UserJoinDto request){
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .inactiveDate(LocalDateTime.now())
                .build();
    }

    public static UserResDto toUserResponse(User user) {
        return UserResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .signUpType(user.getSignUpType())
                .build();
    }

    public static UserPreferResDto toUserPreferResponse(List<String> preferNameList){
        return UserPreferResDto.builder()
                .preferNameList(preferNameList)
                .build();
    }
}
