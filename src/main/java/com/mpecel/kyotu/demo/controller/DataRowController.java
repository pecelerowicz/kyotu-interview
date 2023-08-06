package com.mpecel.kyotu.demo.controller;

import com.mpecel.kyotu.demo.dto.AverageTempByYear;
import com.mpecel.kyotu.demo.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/temperature")
public class DataRowController {
    private final TemperatureService temperatureService;

    @GetMapping("/{city}")
    public ResponseEntity<List<AverageTempByYear>> getAverageTempByYear(@PathVariable("city") String city) {
        return ResponseEntity.ok(temperatureService.averageTempByYears(city));
    }
}
