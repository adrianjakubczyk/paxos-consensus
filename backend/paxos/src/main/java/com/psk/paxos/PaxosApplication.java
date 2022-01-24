package com.psk.paxos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PaxosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaxosApplication.class, args);
	}

}
