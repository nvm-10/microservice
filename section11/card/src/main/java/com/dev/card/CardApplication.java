package com.dev.card;

import com.dev.card.dto.CardContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {CardContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice.",
				description = "Cards service have crud api for cards related operations.",
				version = "v1.0.0"
		)
)
public class CardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardApplication.class, args);
	}

}
