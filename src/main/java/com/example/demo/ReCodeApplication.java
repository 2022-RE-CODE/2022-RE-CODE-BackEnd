package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = ContextStackAutoConfiguration.class)
@ConfigurationPropertiesScan
public class ReCodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReCodeApplication.class, args);
	}
}
