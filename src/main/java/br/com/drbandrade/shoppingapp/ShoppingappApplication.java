package br.com.drbandrade.shoppingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

@SpringBootApplication
public class ShoppingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingappApplication.class, args);
	}

}
