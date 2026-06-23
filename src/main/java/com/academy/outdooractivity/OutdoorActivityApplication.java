package com.academy.outdooractivity;

import com.academy.outdooractivity.service.WeatherApiClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OutdoorActivityApplication implements CommandLineRunner {

	private final WeatherApiClient weatherApiClient;

	public OutdoorActivityApplication(WeatherApiClient weatherApiClient) {
		this.weatherApiClient = weatherApiClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(OutdoorActivityApplication.class, args);
	}

	@Override
	public void run(String... args) {
		var response = weatherApiClient.getForecast();

		System.out.println("Forecast days: " + response.forecast().forecastday().size());

		var firstHour = response.forecast().forecastday().get(0).hour().get(0);
		System.out.println(firstHour.tempC());
		System.out.println(firstHour.gustKph());
		System.out.println(firstHour.time());
		System.out.println(firstHour.chanceOfRain());
		System.out.println(firstHour.cloudCover());
		System.out.println(firstHour.isDay());
	}
}