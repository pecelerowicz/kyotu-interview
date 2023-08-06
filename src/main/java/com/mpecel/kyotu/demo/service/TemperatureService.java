package com.mpecel.kyotu.demo.service;

import com.mpecel.kyotu.demo.dto.AverageTempByYear;
import com.mpecel.kyotu.demo.repository.InMemoryAverageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemperatureService {
    private final InMemoryAverageRepository inMemoryAverageRepository;

    public List<AverageTempByYear> averageTempByYears(String city) {
        return inMemoryAverageRepository.averageTempByYearsForCity(city);
    }

    public void triggerIndexing() throws IOException {
        inMemoryAverageRepository.indexInMemory();
    }

}
