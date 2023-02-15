package com.ilcle.ilcle_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class IlcleBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(IlcleBackApplication.class, args);
	}

}
