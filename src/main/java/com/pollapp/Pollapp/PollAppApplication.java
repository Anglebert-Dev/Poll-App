package com.pollapp.Pollapp;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.util.TimeZone;

//set default timezone to UTC

@SpringBootApplication
@EntityScan(basePackageClasses = {
		PollAppApplication.class,
		Jsr310JpaConverters.class
})
public class PollAppApplication {
	@PostConstruct
	void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(PollAppApplication.class, args);
	}
}
