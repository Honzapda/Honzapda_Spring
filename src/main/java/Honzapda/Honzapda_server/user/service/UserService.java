package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.dto.*;
import Honzapda.Honzapda_server.user.data.entity.User;

import java.util.List;

public interface UserService {

    boolean isEMail(String email);
    boolean isNickName(String name);

    User getUser(Long id);

    UserResDto patchPassword(PatchUserPwDto request, Long userId);

    UserResDto searchUser(Long userId);

    UserResDto updateUser(UserJoinDto request, Long userId);

    LikeResDto likeShop(Long shopId, Long userId);

    LikeResDto deleteLikeShop(Long shopId, Long userId);

    List<ShopResponseDto.SearchDto> getLikeShops(Long id);

    boolean registerUserPrefer(Long userId, List<String> preferNameList);

    UserPreferResDto searchUserPrefer(Long userId);
  
    boolean updateUserPrefer(Long userId, List<String> preferNameList);

}
