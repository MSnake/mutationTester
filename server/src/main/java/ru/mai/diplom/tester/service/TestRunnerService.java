package ru.mai.diplom.tester.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mai.diplom.tester.db.model.TestResultData;
import ru.mai.diplom.tester.db.model.TestResultStatusType;
import ru.mai.diplom.tester.utils.StringToClassUtils;

import java.io.IOException;

/**
 * Сервис для работы с результатами тестирования
 **/
@Slf4j
@Service
public class TestRunnerService {

    /**
     * Дао для работы с результатами тестирования
     */
    @Autowired
    private TestResultDataService testResultDataService;

    private final String defaultPackage = "ru.mai.diplom.tester.data.temp";

    /**
     * Запуск тестирования
     *
     * @param testResultData информация о тестируемых обьектах
     * @return результат тестирования
     */
    public TestResultData runTest(TestResultData testResultData) {
        TestResultData result = testResultData;
        try {
            result.setStatus(TestResultStatusType.PROCESSED);
            testResultDataService.save(result);
            StringToClassUtils.load(testResultData.getMutationData().getCodeText());
            Class testedClass = StringToClassUtils.load(testResultData.getTestCodeData().getCodeText());
            Result resultTest = JUnitCore.runClasses(testedClass);
            StringBuilder resultTestStringBuilder = new StringBuilder();
            if (!resultTest.getFailures().isEmpty()) {
                resultTestStringBuilder.append("Тест не пройден. Детали: ");
                for (Failure failure : resultTest.getFailures()) {
                    resultTestStringBuilder.append(System.lineSeparator() + " ");
                    resultTestStringBuilder.append(failure.getMessage());
                }
                result.setStatus(TestResultStatusType.DONE);
            } else {
                result.setStatus(TestResultStatusType.SUCCESS);
                resultTestStringBuilder.append("Тест успешно пройден.");
            }
            result.setResultText(resultTestStringBuilder.toString());
        } catch (IOException|RuntimeException e ) {
            result.setStatus(TestResultStatusType.ERROR);
            result.setResultText(e.getMessage());
            log.error("Cant generate classes from sources: mutated code data and test code data. Details: " + e.getMessage());
        }
        return testResultDataService.save(result);
    }
}
