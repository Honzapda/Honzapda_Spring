package Honzapda.Honzapda_server.userHelpInfo.data;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoImageResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;

import java.time.LocalDateTime;
import java.util.List;

public class UserHelpInfoConverter {
    public static UserHelpInfo toEntity(UserHelpInfoRequestDto.CreateDto requestDto,User user, Shop shop) {


        return UserHelpInfo.builder()
                .visitDate(LocalDateTime.parse(requestDto.getVisitDateTime()))
                .congestion(requestDto.getCongestion())
                .deskSize(requestDto.getDeskSize())
                .outletCount(requestDto.getOutletCount())
                .light(requestDto.getLight())
                .outletLocation(requestDto.getOutletLocation())
                .restroomLocation(requestDto.getRestroomLocation())
                .musicGenre(requestDto.getMusicGenre())
                .atmosphere(requestDto.getAtmosphere())
                .user(user)
                .shop(shop)
                .build();
    }


    public  static UserHelpInfoResponseDto.UserHelpInfoDto toUserHelpInfoDto(
            UserHelpInfo entity, List<UserHelpInfoImageResponseDto.ImageDto> imageListDto){

        return UserHelpInfoResponseDto.UserHelpInfoDto.builder()
                .congestion(entity.getCongestion().getResponseDescription())
                .visitDateTime(entity.getVisitDate())
                .imageDtoList(imageListDto)
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
