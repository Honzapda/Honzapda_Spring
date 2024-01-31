package Honzapda.Honzapda_server.user.data.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserPreferDto {
    List<String> preferNameList;
}
