package com.academy.outdooractivity;

import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.model.WeatherHour;
import com.academy.outdooractivity.service.IntervalFinder;
import com.academy.outdooractivity.service.SportsConfigLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class OutdoorActivityApplication implements CommandLineRunner {

	private final SportsConfigLoader configLoader;
	private final IntervalFinder intervalFinder;

	public OutdoorActivityApplication(SportsConfigLoader configLoader, IntervalFinder intervalFinder) {
		this.configLoader = configLoader;
		this.intervalFinder = intervalFinder;
	}

	public static void main(String[] args) {
		SpringApplication.run(OutdoorActivityApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Map<String, SportRule> rules = configLoader.loadRules();
		SportRule badmintonRule = rules.get("badminton");
		List<WeatherHour> dummyForecast = List.of(
				new WeatherHour(LocalDateTime.of(2026, 6, 22, 8, 0), 20, 2, 0, 10, true),
				new WeatherHour(LocalDateTime.of(2026, 6, 22, 9, 0), 22, 4, 0, 20, true),
				new WeatherHour(LocalDateTime.of(2026, 6, 22, 10, 0), 23, 20, 0, 20, true),
				new WeatherHour(LocalDateTime.of(2026, 6, 22, 11, 0), 24, 3, 0, 30, true),
				new WeatherHour(LocalDateTime.of(2026, 6, 22, 12, 0), 25, 2, 0, 40, true)
		);

		List<String> result = intervalFinder.findSuitableIntervals(dummyForecast, badmintonRule);

		System.out.println("Intervals: " + result);
	}
}