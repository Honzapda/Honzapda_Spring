package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.user.data.dto.UserRequestDto;
import Honzapda.Honzapda_server.user.data.dto.UserResponseDto;

public interface UserService {

    UserResponseDto.searchDto registerUser(UserRequestDto.registerDto request);

    UserResponseDto.searchDto searchUser(Long userId);

    UserResponseDto.searchDto updateUser(UserRequestDto.updateDto request, Long userId);
}
