package com.mpecel.kyotu.demo;

import com.mpecel.kyotu.demo.repository.InMemoryAverageTemperatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@AllArgsConstructor
@EnableAsync
public class DemoApplication implements CommandLineRunner {
	private final InMemoryAverageTemperatureRepository inMemoryAverageTemperatureRepository;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		inMemoryAverageTemperatureRepository.indexInMemory();
	}

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(4);
		executor.setThreadNamePrefix("my_async_task_executor_thread");
		executor.initialize();
		return executor;
	}

}



