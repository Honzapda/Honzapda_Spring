package Honzapda.Honzapda_server.userHelpInfo.data;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public class UserHelpInfoConverter {
    public static UserHelpInfo toEntity(UserHelpInfoRequestDto.CreateDto requestDto,User user, Shop shop) {

        return UserHelpInfo.builder()
                .visitDate(requestDto.getVisitDateTime())
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
            UserHelpInfo entity, Long likeCount, Boolean userLike){

        UserResDto.InfoDto user = UserResDto.InfoDto.builder()
                .id(entity.getUser().getId())
                .name(entity.getUser().getName())
                .profileImage(entity.getUser().getProfileImage())
                .email(null)
                .signUpType(null)
                .build();

        UserHelpInfoResponseDto.LikeDto like = UserHelpInfoResponseDto.LikeDto.builder()
                .likeCount(likeCount)
                .userLike(userLike)
                .build();

        return UserHelpInfoResponseDto.UserHelpInfoDto.builder()
                .user(user)
                .visitDateTime(entity.getVisitDate())
                .like(like)
                // 내용
                .congestion(entity.getCongestion().getResponseDescription())
                .light(entity.getLight().getResponseDescription())
                .deskSize(entity.getDeskSize().getResponseDescription())
                .outletCount(entity.getOutletCount().getResponseDescription())
                .outletLocation(entity.getOutletLocation())
                .atmosphere(entity.getAtmosphere())
                .restroomLocation(entity.getRestroomLocation())
                .musicGenre(entity.getMusicGenre())
                // 기타 정보
                .createdAt(entity.getCreatedAt())
                .userHelpInfId(entity.getId())
                .build();
    }
    public static UserHelpInfoResponseDto.UserHelpInfoListDto toUserHelpInfoListDto(
            Page<UserHelpInfo> userHelpInfoPage, List<UserHelpInfoResponseDto.UserHelpInfoDto>userHelpInfoDtos, Integer currentPage){

        return UserHelpInfoResponseDto.UserHelpInfoListDto.builder()
                .userHelpInfoDtoList(userHelpInfoDtos)
                .isFirst(userHelpInfoPage.isFirst())
                .isLast(userHelpInfoPage.isLast())
                .listSize(userHelpInfoPage.getSize())
                .totalElements(userHelpInfoPage.getTotalElements())
                .totalPage(userHelpInfoPage.getTotalPages())
                .currentPage(currentPage)
                .build();
    }
}
