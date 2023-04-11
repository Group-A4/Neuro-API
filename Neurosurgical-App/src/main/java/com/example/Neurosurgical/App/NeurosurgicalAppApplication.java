package com.example.Neurosurgical.App;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.*"})
@EnableJpaRepositories(basePackages = "com.example.Neurosurgical.App.dao")
@OpenAPIDefinition
public class NeurosurgicalAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(NeurosurgicalAppApplication.class, args);
	}
}
