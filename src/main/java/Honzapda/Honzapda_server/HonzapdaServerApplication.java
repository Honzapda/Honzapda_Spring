package Honzapda.Honzapda_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
@EnableJpaAuditing
public class HonzapdaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HonzapdaServerApplication.class, args);
	}

}
