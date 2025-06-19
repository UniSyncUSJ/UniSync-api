package com.uniSync.uniSync_api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.uniSync.uniSync_api.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class UniSyncApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniSyncApiApplication.class, args);
		System.out.println("Application is running on port: 8080");
	}

}
