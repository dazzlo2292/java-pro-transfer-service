package ru.otus.java.pro.mt.core.transfers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CoreTransfersApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CoreTransfersApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(CoreTransfersApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
