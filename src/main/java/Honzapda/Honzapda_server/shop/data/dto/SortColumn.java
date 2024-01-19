package Honzapda.Honzapda_server.shop.data.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public enum SortColumn {

    DISTANCE("distance"),
    REVIEW_COUNT("review"),
    BOOKMARK_COUNT("bookmark"),
    RECOMMEND("recommend"),
    EMPTY("empty");

    private final String column;

    @JsonCreator
    public static SortColumn fromString(String source) {
        if (!StringUtils.hasText(source)) {
            return SortColumn.EMPTY;
        }

        for (SortColumn sortColumn : SortColumn.values()) {
            if (sortColumn.getColumn().equals(source)) {
                return sortColumn;
            }
        }
        return SortColumn.EMPTY;
    }

}
