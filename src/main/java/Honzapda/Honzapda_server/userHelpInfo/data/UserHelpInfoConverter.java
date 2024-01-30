package Honzapda.Honzapda_server.userHelpInfo.data;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoImageResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfoImage;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class UserHelpInfoConverter {
    public static UserHelpInfo toEntity(UserHelpInfoRequestDto.CreateDto requestDto,User user, Shop shop) {

        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(requestDto.getVisitDateTime());
            // 성공적으로 파싱된 경우에 수행할 작업
        } catch (DateTimeParseException ex) {
            // 커스텀 예외로 감싸서 throw
            throw new GeneralException(ErrorStatus.INVALID_DATE_TIME_FORMAT);
        }

        return UserHelpInfo.builder()
                .visitDate(dateTime)
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
            UserHelpInfo entity, List<UserHelpInfoImage> imageList, Long likeCount){

        List<UserHelpInfoImageResponseDto.ImageDto> imageDtoList = imageList.stream().
                map(UserHelpInfoImageConverter::toImageDto).toList();

        return UserHelpInfoResponseDto.UserHelpInfoDto.builder()
                .congestion(entity.getCongestion().getResponseDescription())
                .light(entity.getLight().getResponseDescription())
                .deskSize(entity.getDeskSize().getResponseDescription())
                .outletCount(entity.getOutletCount().getResponseDescription())
                .outletLocation(entity.getOutletLocation())
                .atmosphere(entity.getAtmosphere())
                .restroomLocation(entity.getRestroomLocation())
                .musicGenre(entity.getMusicGenre())
                .visitDateTime(entity.getVisitDate())
                .imageDtoList(imageDtoList)
                .createdAt(entity.getCreatedAt())
                .likeCount(likeCount)
                .userHelpInfId(entity.getId())
                .build();
    }
    public static UserHelpInfoResponseDto.UserHelpInfoListDto toUserHelpInfoListDto(
            Page<UserHelpInfo> userHelpInfoPage, List<UserHelpInfoResponseDto.UserHelpInfoDto>userHelpInfoDtos){

        return UserHelpInfoResponseDto.UserHelpInfoListDto.builder()
                .userHelpInfoDtoList(userHelpInfoDtos)
                .isFirst(userHelpInfoPage.isFirst())
                .isLast(userHelpInfoPage.isLast())
                .listSize(userHelpInfoPage.getSize())
                .totalElements(userHelpInfoPage.getTotalElements())
                .totalPage(userHelpInfoPage.getTotalPages())
                .build();
    }
}
