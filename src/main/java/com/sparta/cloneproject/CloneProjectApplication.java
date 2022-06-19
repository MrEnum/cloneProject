package com.sparta.cloneproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@SpringBootApplication
public class CloneProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloneProjectApplication.class, args);

	}

}
