package Honzapda.Honzapda_server.userHelpInfo.data;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoImageResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfoImage;
import org.springframework.data.domain.Slice;

import java.util.List;

public class UserHelpInfoImageConverter {
    public static UserHelpInfoImage toImageEntity(String imageUrl, UserHelpInfo userHelpInfo, Shop shop){
        return UserHelpInfoImage.builder()
                .url(imageUrl)
                .userHelpInfo(userHelpInfo)
                .shop(shop)
                .build();
    }
    public static List<UserHelpInfoImage> toImages(UserHelpInfoRequestDto.CreateDto createDto, UserHelpInfo savedUserHelpInfo, Shop shop) {
        return createDto.getImageUrls().stream()
                .map(imageUrl -> UserHelpInfoImageConverter.toImageEntity(imageUrl, savedUserHelpInfo, shop))
                .toList();
    }
    public static UserHelpInfoImageResponseDto.ImageDto toImageDto(UserHelpInfoImage entity){
        return UserHelpInfoImageResponseDto.ImageDto.builder()
                .imageId(entity.getId())
                .url(entity.getUrl())
                .build();
    }
    public static UserHelpInfoImageResponseDto.ImageListDto toImageListDto(Slice<UserHelpInfoImage> userHelpInfoImageSlice){

        List<UserHelpInfoImageResponseDto.ImageDto> imageDtoList = userHelpInfoImageSlice.stream()
                .map(UserHelpInfoImageConverter::toImageDto)
                .toList();

        return UserHelpInfoImageResponseDto.ImageListDto.builder()
                .imageDtoList(imageDtoList)
                .getNumberOfElements(userHelpInfoImageSlice.getNumberOfElements())
                .hasNext(userHelpInfoImageSlice.hasNext())
                .hasPrevious(userHelpInfoImageSlice.hasPrevious())
                .build();

    }
}
