package Honzapda.Honzapda_server.userHelpInfo.data.entity;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * 혼잡도: 10%, 20%, 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%
 */
@RequiredArgsConstructor
@Getter
@Slf4j
public enum Congestion {


    TEN("10", "10%"),
    TWENTY("20", "20%"),
    THIRTY("30", "30%"),
    FORTY("40", "40%"),
    FIFTY("50", "50%"),
    SIXTY("60", "60%"),
    SEVENTY("70", "70%"),
    EIGHTY("80", "80%"),
    NINETY("90", "90%"),
    HUNDRED("100", "100%");

    private final String requestDescription;
    private final String responseDescription;

    @JsonCreator
    public static Congestion fromString(String source) {

        if (!StringUtils.hasText(source)) {
            //throw new GeneralException(ErrorStatus.INVALID_CONGESTION_EMPTY);
            return null;
        }

        return Arrays.stream(Congestion.values())
                .filter(congestion -> congestion.requestDescription.equals(source))
                .findFirst()
                //.orElseThrow(() -> new GeneralException(ErrorStatus.INVALID_CONGESTION));
                .orElse(null);
    }
}
