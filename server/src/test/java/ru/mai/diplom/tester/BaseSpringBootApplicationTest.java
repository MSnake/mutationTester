package ru.mai.diplom.tester;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Базовый класс для тестирования Spring Boot компонентов
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplicationTest.class)
@TestPropertySource(locations = "classpath:application.yml")
@Slf4j
public class BaseSpringBootApplicationTest {

    @Test
    public void testRunning() {
    }


}
