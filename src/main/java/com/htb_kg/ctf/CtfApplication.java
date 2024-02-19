package com.htb_kg.ctf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling

public class CtfApplication {
	public static void main(String[] args) {
		SpringApplication.run(CtfApplication.class, args);
	}
}
