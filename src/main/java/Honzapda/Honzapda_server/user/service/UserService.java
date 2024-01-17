package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.dto.LikeResDto;
import Honzapda.Honzapda_server.user.data.dto.UserRequestDto;
import Honzapda.Honzapda_server.user.data.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    boolean isEMail(String email);
    boolean isNickName(String name);
  
    UserResponseDto.searchDto searchUser(Long userId);

    UserResponseDto.searchDto updateUser(UserRequestDto.updateDto request, Long userId);

    LikeResDto likeShop(Long shopId, Long userId);

    LikeResDto deleteLikeShop(Long shopId, Long userId);

    List<ShopResponseDto.searchDto> getLikeShops(Long id);
}
