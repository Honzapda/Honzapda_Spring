package Honzapda.Honzapda_server.review.data;

import Honzapda.Honzapda_server.review.data.dto.ReviewImageResponseDto;
import Honzapda.Honzapda_server.review.data.entity.ReviewImage;
import org.springframework.data.domain.Slice;

import java.util.List;

public class ReviewImageConverter {
    public static ReviewImageResponseDto.ImageDto toImageDto(ReviewImage image){

        return ReviewImageResponseDto.ImageDto.builder()
                .imageId(image.getId())
                .url(image.getUrl())
                .build();
    }

    public static ReviewImageResponseDto.ImageListDto toImageListDto(Slice<ReviewImage> imageSlice){

        List<ReviewImageResponseDto.ImageDto> imageDtoList =
                imageSlice.getContent().stream().map(ReviewImageConverter::toImageDto).toList();

        return ReviewImageResponseDto.ImageListDto.builder()
                .imageDtoList(imageDtoList)
                .hasNext(imageSlice.hasNext())
                .hasPrevious(imageSlice.hasPrevious())
                .getNumberOfElements(imageSlice.getNumberOfElements())
                .build();
    }
}
