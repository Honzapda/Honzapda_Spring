package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserRequestDto;
import Honzapda.Honzapda_server.user.data.dto.UserResponseDto;

public interface UserService {

    boolean isEMail(String email);
    boolean isNickName(String name);
  
    UserResponseDto.searchDto searchUser(Long userId);

    UserResponseDto.searchDto updateUser(UserRequestDto.updateDto request, Long userId);
}
