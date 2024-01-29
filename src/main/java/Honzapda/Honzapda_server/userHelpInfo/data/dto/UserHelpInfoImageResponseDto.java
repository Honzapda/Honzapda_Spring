package Honzapda.Honzapda_server.userHelpInfo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserHelpInfoImageResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageDto{
        private Long imageId;
        private String url;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageListDto{
        private List<ImageDto> imageDtoList;
        private Integer getNumberOfElements;
        private Boolean hasNext;
        private Boolean hasPrevious;
    }
}
