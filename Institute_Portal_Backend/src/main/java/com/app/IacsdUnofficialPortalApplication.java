package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan(basePackages = "com.app.services")
@SpringBootApplication
public class IacsdUnofficialPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(IacsdUnofficialPortalApplication.class, args);
	}

}
