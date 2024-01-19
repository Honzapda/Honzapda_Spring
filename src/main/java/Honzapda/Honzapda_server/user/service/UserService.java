package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferResDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;

import java.util.List;

public interface UserService {

    boolean isEMail(String email);
    boolean isNickName(String name);
  
    UserResDto searchUser(Long userId);

    UserResDto updateUser(UserJoinDto request, Long userId);

    boolean registerUserPrefer(Long userId, List<String> preferNameList);

    UserPreferResDto searchUserPrefer(Long userId);
  
    boolean updateUserPrefer(Long userId, List<String> preferNameList);
}
