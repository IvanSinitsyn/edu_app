package ru.hoff.edu.service.core;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ParcelCounter {

    public List<String> countParcels(List<String> parcelNames) {
        Map<String, Long> countMap = parcelNames.stream()
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

        return countMap.entrySet().stream()
                .map(entry -> String.format("\"%s\";%d", entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
