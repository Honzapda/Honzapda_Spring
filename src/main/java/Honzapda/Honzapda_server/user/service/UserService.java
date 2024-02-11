package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.dto.*;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    boolean isEMail(String email);
    boolean isNickName(String name);

    User getUser(Long id);

    UserResDto.InfoDto patchPassword(UserDto.PatchUserPwDto request, Long userId);

    UserResDto.ProfileDto findUser(Long userId);

    UserResDto.InfoDto updateUser(UserDto.JoinDto request, Long userId);

    LikeResDto likeShop(Long shopId, Long userId);

    LikeResDto deleteLikeShop(Long shopId, Long userId);

    List<ShopResponseDto.SearchDto> getLikeShops(Long id);

    UserPreferDto registerUserPrefer(Long userId, List<String> preferNameList);

    UserPreferDto searchUserPrefer(Long userId);
  
    boolean updateUserPrefer(Long userId, List<String> preferNameList);

    UserResDto.ProfileDto updateUserImage(MultipartFile image, Long userId) throws Exception;
}
