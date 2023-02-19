package com.ilcle.ilcle_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.ilcle.ilcle_back"})
public class IlcleBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(IlcleBackApplication.class, args);
	}

}
