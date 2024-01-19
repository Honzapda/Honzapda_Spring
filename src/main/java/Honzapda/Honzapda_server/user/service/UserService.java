package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.dto.LikeResDto;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferResDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;

import java.util.List;


import java.util.List;

public interface UserService {

    boolean isEMail(String email);
    boolean isNickName(String name);
  
    UserResDto searchUser(Long userId);

    UserResDto updateUser(UserJoinDto request, Long userId);

    LikeResDto likeShop(Long shopId, Long userId);

    LikeResDto deleteLikeShop(Long shopId, Long userId);

    List<ShopResponseDto.searchDto> getLikeShops(Long id);

    boolean registerUserPrefer(Long userId, List<String> preferNameList);

    UserPreferResDto searchUserPrefer(Long userId);
  
    boolean updateUserPrefer(Long userId, List<String> preferNameList);

}
