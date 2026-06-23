package com.academy.outdooractivity;

import com.academy.outdooractivity.service.ActivityPlannerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OutdoorActivityApplication implements CommandLineRunner {

	private final ActivityPlannerService plannerService;

	public OutdoorActivityApplication(ActivityPlannerService plannerService) {
		this.plannerService = plannerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(OutdoorActivityApplication.class, args);
	}

	@Override
	public void run(String... args) {
		plannerService.printSuitableActivities();
	}
}