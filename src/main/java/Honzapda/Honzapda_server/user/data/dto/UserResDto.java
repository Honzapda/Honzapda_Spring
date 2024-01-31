package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;
import lombok.*;

import java.util.List;


public class UserResDto {
    @Data
    @Builder
    public static class InfoDto{
        private Long id;
        private String name;
        private String email;
        private User.SignUpType signUpType;
    }
    @Data
    @Builder
    public static class ProfileDto{
        private Long id;
        private String name;
        private String profileImage;
        private List<String> preferNameList;
        private List<ShopResponseDto.SimpleSearchDto> likeShops;
    }

}
