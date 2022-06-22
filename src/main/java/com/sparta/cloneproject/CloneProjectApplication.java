package com.sparta.cloneproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;

// Email Enable 비동기
@Slf4j
@SpringBootApplication
public class CloneProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloneProjectApplication.class, args);

	}

}
