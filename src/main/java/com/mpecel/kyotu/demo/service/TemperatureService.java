package com.mpecel.kyotu.demo.service;

import com.mpecel.kyotu.demo.dto.AverageTempByYear;
import com.mpecel.kyotu.demo.repository.InMemoryAverageTemperatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemperatureService {
    private final InMemoryAverageTemperatureRepository inMemoryAverageTemperatureRepository;

    public List<AverageTempByYear> averageTempByYears(String city) {
        return inMemoryAverageTemperatureRepository.averageTempByYearsForCity(city);
    }

    public void triggerIndexing() throws IOException {
        inMemoryAverageTemperatureRepository.indexInMemory();
    }

}
