package Honzapda.Honzapda_server.auth.apple;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppleFeignClientConfiguration {

//    @Autowired
//    private ObjectMapper objectMapper;
    @Bean
    public AppleFeignClientErrorDecoder appleFeignClientErrorDecoder() {
        return new AppleFeignClientErrorDecoder(new ObjectMapper());
    }
}