package com.academy.outdooractivity;

import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.service.SportsConfigLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class OutdoorActivityApplication implements CommandLineRunner {

	private final SportsConfigLoader configLoader;

	public OutdoorActivityApplication(SportsConfigLoader configLoader) {
		this.configLoader = configLoader;
	}

	public static void main(String[] args) {
		SpringApplication.run(OutdoorActivityApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Map<String, SportRule> rules = configLoader.loadRules();

		System.out.println("Sports: " + rules.keySet());
		System.out.println("Badminton rules: " + rules.get("badminton"));
	}
}