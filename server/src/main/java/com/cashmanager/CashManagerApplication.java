package com.cashmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CashManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashManagerApplication.class, args);
	}

}
