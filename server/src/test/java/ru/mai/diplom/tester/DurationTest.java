package ru.mai.diplom.tester;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mai.diplom.tester.ApptemplateServerApp;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ApptemplateServerApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class DurationTest {

    @Test
    public void contextTest() {
        System.out.println("TEST");
    }
}
