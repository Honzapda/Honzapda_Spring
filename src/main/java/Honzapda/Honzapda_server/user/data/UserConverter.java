package Honzapda.Honzapda_server.user.data;

import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.dto.UserDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.LikeData;
import Honzapda.Honzapda_server.user.data.entity.Prefer;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.data.entity.UserPrefer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserConverter {

    @Value("${honzapda.basic-image.url}")
    private static String basicImageUrl;


    public static User toUser(UserDto.JoinDto request, PasswordEncoder encoder){
        return User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .name(request.getName())
                .inactiveDate(LocalDateTime.now())
                .profileImage(basicImageUrl)
                .build();
    }

    public static UserResDto.InfoDto toUserInfo(User user) {
        return UserResDto.InfoDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .signUpType(user.getSignUpType())
                .build();
    }

    public static UserPreferDto toUserPreferResponse(List<String> preferNameList){
        return UserPreferDto.builder()
                .preferNameList(preferNameList)
                .build();
    }
    public static UserResDto.ProfileDto toUserProfile(User user){
        List<LikeData> likes = user.getLikes();
        List<ShopResponseDto.SimpleSearchDto> likeShops = new ArrayList<>();
        List<String> preferNameList = getPreferNameListByUserPreferList(user.getUserPrefers());

        likes.forEach(likeData ->{
            Shop shop = likeData.getShop();
            likeShops.add(ShopConverter.toShopSimpleResponse(shop));
        });

        return UserResDto.ProfileDto.builder()
                .id(user.getId())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .preferNameList(preferNameList)
                .likeShops(likeShops)
                .build();
    }

    private static List<String> getPreferNameListByUserPreferList(Set<UserPrefer> userPreferList){

        List<String> preferNameList = new ArrayList<>();

        for (UserPrefer userPrefer : userPreferList) {
            Prefer prefer = userPrefer.getPrefer();
            String preferName = prefer.getPreferName();
            preferNameList.add(preferName);
        }

        return preferNameList;
    }
}
