package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.auth.apple.AppleSocialTokenInfoResponse;
import lombok.Data;

@Data
public class UserJoinDto {
    private String name;
    private String email;
    private String password;
    private String socialToken;
}
