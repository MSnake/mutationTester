package ru.mai.diplom.tester;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Тестовый стартер приложения
 *
 */
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ApptemplateServerApp.class, CommandLineRunner.class}))
@SpringBootApplication
public class SpringBootApplicationTest {

    /**
     * Стартовый метод приложения
     *
     * @param args аргументы
     */
    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{SpringBootApplicationTest.class}, args);
    }
}
