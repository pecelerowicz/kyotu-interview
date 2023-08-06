package com.mpecel.kyotu.demo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class AverageTempByYear {
    private String year;
    private double averageTemperature;
}
