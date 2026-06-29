package com.betkowski.incident_manager;

import org.springframework.boot.SpringApplication;

public class TestIncidentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.from(IncidentManagerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
