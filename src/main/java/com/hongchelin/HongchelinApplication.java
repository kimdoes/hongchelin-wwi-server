package com.hongchelin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration
public class HongchelinApplication {

	public static void main(String[] args) {
		System.out.println("HongchelinApplication");
		SpringApplication.run(HongchelinApplication.class, args);
	}

}
