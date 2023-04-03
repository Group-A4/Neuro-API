package com.example.Neurosurgical.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class NeurosurgicalAppApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(NeurosurgicalAppApplication.class, args);
	}

}
