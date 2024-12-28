package com.concertly.concertly_legacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ConcertlyLegacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcertlyLegacyApplication.class, args);
	}

}
