package com.mpecel.kyotu.demo.repository;

import com.mpecel.kyotu.demo.dto.AverageTempByYear;
import com.mpecel.kyotu.demo.dto.DataRow;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InMemoryAverageTemperatureRepository {
    private final FileRepository fileRepository;

    private final Map<String, Map<Integer, AverageSampleSizePair>> map = new HashMap<>(); // city, year, currentAverage, sampleSize

    @Async
    public synchronized void indexInMemory() throws IOException {
        log.info("Indexing started");
        map.clear();
        fileRepository.readDataRowsAsStream().forEach(r -> {
            if(!map.containsKey(r.getCity())) {
                AverageSampleSizePair averageSize = new AverageSampleSizePair(r.getTemperature());
                Map<Integer, AverageSampleSizePair> yearAverage = new HashMap<>();
                yearAverage.put(r.getDateTime().getYear(), averageSize);
                map.put(r.getCity(), yearAverage);
            } else {
                Map<Integer, AverageSampleSizePair> yearAverage = map.get(r.getCity());
                if(!yearAverage.containsKey(r.getDateTime().getYear())) {
                    AverageSampleSizePair averageSize = new AverageSampleSizePair(r.getTemperature());
                    yearAverage.put(r.getDateTime().getYear(), averageSize);
                } else {
                    AverageSampleSizePair averageSize = yearAverage.get(r.getDateTime().getYear());
                    averageSize.updateAverage(r.getTemperature());
                }
            }
        });
        log.info("Indexing finished");

        // todo remove
        System.out.println(map);
    }

    public synchronized List<AverageTempByYear> averageTempByYearsForCity(String city) {
        Map<Integer, AverageSampleSizePair> cityMap = map.get(city);
        if(cityMap == null) {
            return new LinkedList<>();
        }
        return cityMap.entrySet()
                .stream()
                .map(e -> AverageTempByYear.builder()
                        .year(e.getKey()+"")
                        .averageTemperature(e.getValue().getRoundedAverage())
                        .build())
                .collect(Collectors.toList());
    }

    private double updateAverage(double currentAverage, int sampleSize, double newValue) {
        return (currentAverage * sampleSize + newValue)/(sampleSize + 1);
    }


}
class AverageSampleSizePair {
    AverageSampleSizePair(double average) {
        this.average = average;
        this.size = 1;
    }
    private double average;
    private int size;

    public void updateAverage(double newValue) {
        this.average = (average * size + newValue)/(size + 1);
        this.size = size + 1;
    }

    public double getRoundedAverage() {
        return roundToOneDecimal(average);
    }
    private double roundToOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    @Override
    public String toString() {
        return "AverageSampleSizePair{" +
                "rounded average=" + getRoundedAverage() +
                '}';
    }
}