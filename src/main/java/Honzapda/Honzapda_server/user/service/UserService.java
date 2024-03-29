package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.dto.*;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    Slice<ShopResponseDto.likeDto> getLikeShops(Long id, ShopRequestDto.SearchDto request, Pageable pageable);

    UserPreferDto registerUserPrefer(Long userId, List<String> preferNameList);

    UserPreferDto searchUserPrefer(Long userId);
  
    boolean updateUserPrefer(Long userId, List<String> preferNameList);

    UserResDto.ProfileDto updateUserImage(MultipartFile image, Long userId);
}
