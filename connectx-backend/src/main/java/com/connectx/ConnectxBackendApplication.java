package com.connectx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConnectxBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				ConnectxBackendApplication.class,
				args
		);
		System.out.println("Hello World");
	}
}