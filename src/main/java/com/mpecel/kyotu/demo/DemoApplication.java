package com.mpecel.kyotu.demo;

import com.mpecel.kyotu.demo.repository.InMemoryAverageRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class DemoApplication implements CommandLineRunner {
	private final InMemoryAverageRepository inMemoryAverageRepository;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		inMemoryAverageRepository.indexInMemory();
	}

}



