package com.academy.outdooractivity;

import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.model.WeatherHour;
import com.academy.outdooractivity.service.IntervalFinder;
import com.academy.outdooractivity.service.SportsConfigLoader;
import com.academy.outdooractivity.service.WeatherApiClient;
import com.academy.outdooractivity.service.WeatherMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class OutdoorActivityApplication implements CommandLineRunner {

	private final WeatherApiClient weatherApiClient;
	private final WeatherMapper weatherMapper;
	private final SportsConfigLoader configLoader;
	private final IntervalFinder intervalFinder;

	public OutdoorActivityApplication(WeatherApiClient weatherApiClient,
									  WeatherMapper weatherMapper,
									  SportsConfigLoader configLoader,
									  IntervalFinder intervalFinder) {
		this.weatherApiClient = weatherApiClient;
		this.weatherMapper = weatherMapper;
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
		var response = weatherApiClient.getForecast();
		List<WeatherHour> weatherHours = weatherMapper.map(response);
		List<String> result = intervalFinder.findSuitableIntervals(weatherHours, badmintonRule);
		System.out.println("For Sofia: " + result);
	}
}