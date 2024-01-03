package ru.hw.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DockerApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DockerApplication.class, args);

        printApplicationEnvironment(run);
    }

    private static void printApplicationEnvironment(ConfigurableApplicationContext run) {
        System.out.println("******** Start application with:");
        System.out.println(run.getEnvironment().getPropertySources().get("server.ports").getSource());
        System.out.println("Active Profiles: " + String.join(", ", run.getEnvironment().getActiveProfiles()));
    }
}
