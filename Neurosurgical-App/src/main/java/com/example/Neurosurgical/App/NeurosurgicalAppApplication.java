package com.example.Neurosurgical.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.*"})
@EnableJpaRepositories(basePackages = "com.example.Neurosurgical.App.dao")
public class NeurosurgicalAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(NeurosurgicalAppApplication.class, args);
	}

}
