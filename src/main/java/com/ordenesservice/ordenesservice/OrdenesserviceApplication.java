package com.ordenesservice.ordenesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrdenesserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdenesserviceApplication.class, args);
	}

}
