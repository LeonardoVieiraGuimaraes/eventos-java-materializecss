package com.evento.evento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication(scanBasePackages = "com.evento.evento.controllers")
public class EventoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoApplication.class, args);
	}

}
