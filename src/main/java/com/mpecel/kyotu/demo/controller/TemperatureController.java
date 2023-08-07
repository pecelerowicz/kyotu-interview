package com.mpecel.kyotu.demo.controller;

import com.mpecel.kyotu.demo.dto.AverageTempByYear;
import com.mpecel.kyotu.demo.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/temperature")
public class TemperatureController {
    private final TemperatureService temperatureService;

    @GetMapping("/{city}")
    public ResponseEntity<List<AverageTempByYear>> getAverageTempByYear(@PathVariable("city") String city) {
        return ResponseEntity.ok(temperatureService.averageTempByYears(city));
    }

    @PostMapping("/indexing")
    public ResponseEntity<String> triggerIndexing() throws IOException {
        temperatureService.triggerIndexing();
        return ResponseEntity.ok("Indexing scheduled");
    }
}
