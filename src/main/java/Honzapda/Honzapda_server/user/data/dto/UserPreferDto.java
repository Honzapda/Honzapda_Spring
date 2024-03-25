package Honzapda.Honzapda_server.user.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferDto {
    @Schema(example = "[\n" +
            "\t\t\t\t\"적당한 공간\", \"어두운 조명\", \"재즈 음악\", \"깨끗한\"\n" +
            "\t\t]")
    List<String> preferNameList;
}
