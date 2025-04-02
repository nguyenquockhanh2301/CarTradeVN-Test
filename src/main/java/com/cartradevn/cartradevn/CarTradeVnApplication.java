package com.cartradevn.cartradevn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@EnableSpringDataWebSupport
@OpenAPIDefinition(
		info = @Info(title = "CarTradeVN API", version = "1.0", description = "API for CarTradeVN application"),
		tags = {
				@Tag(name = "Vehicle", description = "Operations related to vehicles"),
				@Tag(name = "User", description = "Operations related to users"),
				@Tag(name = "Transaction", description = "Operations related to transactions")
		}
)
public class CarTradeVnApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarTradeVnApplication.class, args);
	}

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
