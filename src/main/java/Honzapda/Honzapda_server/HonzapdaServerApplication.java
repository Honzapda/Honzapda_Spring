package Honzapda.Honzapda_server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
@OpenAPIDefinition(
		servers = {
				@Server(url = "/", description = "Default Server url")
		}
)
@EnableCaching
public class HonzapdaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HonzapdaServerApplication.class, args);
	}

}
