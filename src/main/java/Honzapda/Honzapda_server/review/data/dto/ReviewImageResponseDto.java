package Honzapda.Honzapda_server.review.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReviewImageResponseDto {
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
        private Integer currentPage;
        private Boolean hasNext;
        private Boolean hasPrevious;
    }


}
