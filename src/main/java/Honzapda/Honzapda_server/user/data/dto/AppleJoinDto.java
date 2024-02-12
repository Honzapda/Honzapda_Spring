package Honzapda.Honzapda_server.user.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppleJoinDto {
    private String email;
    private String refreshToken;
    public static AppleJoinDto toDTO(String email, String refreshToken){
        return AppleJoinDto.builder()
                .email(email)
                .refreshToken(refreshToken)
                .build();
    }
}
