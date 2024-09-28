package com.github.cokothon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.github.cokothon.common.property.JwtProperty;
import com.github.cokothon.common.property.RedisProperty;

@SpringBootApplication
@EnableAsync
@EnableMongoAuditing
@EnableConfigurationProperties({JwtProperty.class, RedisProperty.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
