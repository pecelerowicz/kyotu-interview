package com.mpecel.kyotu.demo.repository;

import com.mpecel.kyotu.demo.dto.AverageTempByYear;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InMemoryAverageRepository {
    private final FileRepository fileRepository;

    private final Map<String, Map<Integer, AverageSampleSizePair>> map = new HashMap<>(); // city, year, currentAverage, sampleSize

    public void indexInMemory() throws IOException {
        System.out.println("Indexing started");
        fileRepository.readDataRowsAsStream().forEach(r -> {
            if(!map.containsKey(r.getCity())) {
                AverageSampleSizePair averageSize =
                        AverageSampleSizePair.builder().average(r.getTemperature()).size(1).build();
                Map<Integer, AverageSampleSizePair> yearAverage = new HashMap<>();
                yearAverage.put(r.getDateTime().getYear(), averageSize);
                map.put(r.getCity(), yearAverage);
            } else {
                Map<Integer, AverageSampleSizePair> yearAverage = map.get(r.getCity());
                if(!yearAverage.containsKey(r.getDateTime().getYear())) {
                    AverageSampleSizePair averageSize =
                            AverageSampleSizePair.builder().average(r.getTemperature()).size(1).build();
                    yearAverage.put(r.getDateTime().getYear(), averageSize);
                } else {
                    AverageSampleSizePair averageSize = yearAverage.get(r.getDateTime().getYear());
                    double updatedAverage =
                            (averageSize.getAverage() * averageSize.getSize() + r.getTemperature()) / (averageSize.getSize() + 1);
                    averageSize.setAverage(updatedAverage);
                    averageSize.setSize(averageSize.getSize() + 1);
                }
            }
        });
        System.out.println("Indexing finished");

        System.out.println(map);
    }

    public List<AverageTempByYear> averageTempByYearsForCity(String city) {
        return map.get(city).entrySet()
                .stream()
                .map(e -> AverageTempByYear.builder()
                                           .year(e.getKey()+"")
                                           .averageTemperature(e.getValue().getAverage())
                                            .build())
                .collect(Collectors.toList());
    }

}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
class AverageSampleSizePair {
    private double average;
    private int size;
}