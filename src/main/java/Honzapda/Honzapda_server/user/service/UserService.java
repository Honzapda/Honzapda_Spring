package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;

public interface UserService {

    UserResDto registerUser(UserJoinDto request);

    UserResDto searchUser(Long userId);

    UserResDto updateUser(UserJoinDto request, Long userId);
}
