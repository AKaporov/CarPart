package ru.hw.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.hw.service.value_source.CarPartValueSource;

@Service
public class RunnerApp implements CommandLineRunner {
    private final CarPartValueSource valueSource;

    public RunnerApp(CarPartValueSource valueSource) {
        this.valueSource = valueSource;
    }

    @Override
    public void run(String... args) {
        valueSource.generate();
    }
}
