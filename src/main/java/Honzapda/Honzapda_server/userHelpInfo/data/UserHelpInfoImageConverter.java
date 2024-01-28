package Honzapda.Honzapda_server.userHelpInfo.data;

import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoImageResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfoImage;

import java.util.List;

public class UserHelpInfoImageConverter {
    public static UserHelpInfoImage toImageEntity(String imageUrl,UserHelpInfo userHelpInfo){
        return UserHelpInfoImage.builder()
                .url(imageUrl)
                .userHelpInfo(userHelpInfo)
                .build();
    }
    public static List<UserHelpInfoImage> toImages(UserHelpInfoRequestDto.CreateDto createDto, UserHelpInfo savedUserHelpInfo) {
        return createDto.getImageUrls().stream()
                .map(imageUrl -> UserHelpInfoImageConverter.toImageEntity(imageUrl, savedUserHelpInfo))
                .toList();
    }
    public static UserHelpInfoImageResponseDto.ImageDto toImageDto(UserHelpInfoImage entity){
        return UserHelpInfoImageResponseDto.ImageDto.builder()
                .imageId(entity.getId())
                .url(entity.getUrl())
                .build();
    }
}
