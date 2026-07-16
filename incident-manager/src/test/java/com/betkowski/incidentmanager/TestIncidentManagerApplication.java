package com.betkowski.incidentmanager;

import org.springframework.boot.SpringApplication;

public class TestIncidentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.from(IncidentManagerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

