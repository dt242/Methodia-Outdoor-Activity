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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		var response = weatherApiClient.getForecast();
		List<WeatherHour> weatherHours = weatherMapper.map(response);
		Map<LocalDate, List<WeatherHour>> weatherByDate = weatherHours.stream()
				.collect(Collectors.groupingBy(
						hour -> hour.time().toLocalDate()
				));

		for (Map.Entry<String, SportRule> sportEntry : rules.entrySet()) {
			String sportName = sportEntry.getKey();
			SportRule rule = sportEntry.getValue();
			System.out.printf("%n=== %s ===%n", sportName.toUpperCase());
			weatherByDate.entrySet().stream()
					.sorted(Map.Entry.comparingByKey())
					.forEach(dayEntry -> {
						LocalDate date = dayEntry.getKey();
						List<WeatherHour> hoursForDay = dayEntry.getValue();
						List<String> intervals = intervalFinder.findSuitableIntervals(hoursForDay, rule);
						if (!intervals.isEmpty()) {
							System.out.println(date + " -> " + intervals);
						}
					});
		}
	}
}