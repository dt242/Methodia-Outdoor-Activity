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
		String jsonResponse = weatherApiClient.getForecastJson();
		System.out.println(jsonResponse);
	}
}