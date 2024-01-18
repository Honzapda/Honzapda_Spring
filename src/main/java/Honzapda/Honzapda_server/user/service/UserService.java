package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;

import java.util.List;

public interface UserService {

    UserResDto registerUser(UserJoinDto request);

    UserResDto searchUser(Long userId);

    UserResDto updateUser(UserJoinDto request, Long userId);

    boolean registerUserPrefer(Long userId, List<String> preferNameList);

    List<String> searchUserPrefer(Long userId);

    boolean updateUserPrefer(Long userId, List<String> preferNameList);
}
