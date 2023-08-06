package com.mpecel.kyotu.demo.repository;

import com.mpecel.kyotu.demo.dto.DataRow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FileRepository {
    @Value("${elasticsearch.file-name}")
    private String fileName;

    public List<DataRow> readDataRows(int numberOfLines) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            return lines.limit(numberOfLines)
                    .map(this::convertLineToDataRow)
                    .collect(Collectors.toList());
        }
    }

    public Stream<DataRow> readDataRowsAsStream() throws IOException {
        Path path = Paths.get(fileName);
        return Files.lines(path).map(this::convertLineToDataRow);
    }

    private DataRow convertLineToDataRow(String line) {
        String[] parts = line.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return new DataRow(parts[0], LocalDateTime.parse(parts[1], formatter), Double.parseDouble(parts[2]));
    }

}
