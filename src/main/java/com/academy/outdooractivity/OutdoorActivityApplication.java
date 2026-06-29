package com.academy.outdooractivity;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.service.ActivityPlannerService;
import com.academy.outdooractivity.ui.ConsolePrinter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class OutdoorActivityApplication implements CommandLineRunner {

	private final ActivityPlannerService plannerService;
	private final ConsolePrinter consolePrinter;

	public OutdoorActivityApplication(ActivityPlannerService plannerService, ConsolePrinter consolePrinter) {
		this.plannerService = plannerService;
		this.consolePrinter = consolePrinter;
	}

	public static void main(String[] args) {
		SpringApplication.run(OutdoorActivityApplication.class, args);
	}

	@Override
	public void run(String... args) {
		List<ActivityResult> results = plannerService.findSuitableActivities();
		consolePrinter.print(results);
	}
}