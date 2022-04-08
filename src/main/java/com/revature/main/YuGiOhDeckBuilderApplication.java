package com.revature.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class YuGiOhDeckBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(YuGiOhDeckBuilderApplication.class, args);
	}

}
