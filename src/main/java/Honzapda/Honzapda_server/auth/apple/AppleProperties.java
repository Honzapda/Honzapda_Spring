package Honzapda.Honzapda_server.auth.apple;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@ConfigurationProperties(prefix = "social-login.provider.apple")
@Getter
@Setter
public class AppleProperties {

    private String grantType;
    private String clientId;
    private String keyId;
    private String teamId;
    private String audience;
    private String privateKeyPath;
    public String getPrivateKey() {
        try {
            // privateKeyPath 변수를 사용하여 p8 키 파일의 위치를 지정
            byte[] privateKeyBytes = Files.readAllBytes(Paths.get(privateKeyPath));
            return new String(privateKeyBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error reading private key file", e);
        }
    }
}