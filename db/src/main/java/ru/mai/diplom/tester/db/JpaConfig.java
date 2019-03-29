package ru.mai.diplom.tester.db;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * Добавления JPA-конвертеров из библиотеки в автоскан Hibernate.
 */
@Configuration
@EntityScan(basePackages = {"ru.mai.diplom.tester.db"})
public class JpaConfig {
}
