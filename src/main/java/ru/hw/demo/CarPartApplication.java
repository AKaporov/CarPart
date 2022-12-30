package ru.hw.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CarPartApplication {

    public static void main(String[] args) /*throws SQLException */ {
        ConfigurableApplicationContext ctx = SpringApplication.run(CarPartApplication.class, args);

//		System.out.println("Hello world");
//		Console.main(args);
    }

}
