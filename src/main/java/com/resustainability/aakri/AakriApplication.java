package com.resustainability.aakri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = "com.resustainability.aakri")
@EnableJpaRepositories(basePackages = "com.resustainability.aakri.Dao.impl")
@EntityScan(basePackages = "com.resustainability.aakri.entity")
public class AakriApplication {

	public static void main(String[] args) {
		SpringApplication.run(AakriApplication.class, args);
	}

}
