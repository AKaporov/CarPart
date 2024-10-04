package ru.hw.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class RunnerApp implements CommandLineRunner {

    private final Command command;

    public RunnerApp(Command command) {
        this.command = command;
    }

    @Override
    public void run(String... args) throws Exception {
        command.execute();
    }
}
