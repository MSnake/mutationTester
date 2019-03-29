package ru.mai.diplom.tester;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * Стартовый класс приложения
 **/

@SpringBootApplication
public class ApptemplateServerApp {


    /**
     * Стартовый метод приложения
     *
     * @param args аргументы
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(ApptemplateServerApp.class).headless(false).run(args);
    }
}
