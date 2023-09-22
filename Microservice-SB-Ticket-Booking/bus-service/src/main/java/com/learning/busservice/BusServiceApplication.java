package com.learning.busservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class BusServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(BusServiceApplication.class, args);
		System.out.println("bus");

	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
