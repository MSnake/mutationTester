package ru.mai.diplom.tester.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mai.diplom.tester.db.dao.TestResultDataDao;
import ru.mai.diplom.tester.db.model.MutationData;
import ru.mai.diplom.tester.db.model.TestCodeData;
import ru.mai.diplom.tester.db.model.TestResultData;
import ru.mai.diplom.tester.db.model.TestResultStatusType;
import ru.mai.diplom.tester.utils.StringToClazzUtils;

import java.io.IOException;
import java.util.Optional;

/**
 * Сервис для работы с результатами тестирования
 **/
@Slf4j
@Service
public class TestResultDataService {

    /**
     * Дао для работы с результатами тестирования
     */
    @Autowired
    private TestResultDataDao dao;

    /**
     * Сформировать обьект с информацией о результате тестирования
     *
     * @param testCodeData информация о коде тестирования
     * @param mutationData информация о преобразованиях
     * @return обьект с информацией о результате тестирования
     */
    public TestResultData createTestResultData(@NonNull TestCodeData testCodeData, @NonNull MutationData mutationData) {
        TestResultData data = new TestResultData();
        data.setTestCodeData(testCodeData);
        data.setMutationData(mutationData);
        data.setStatus(TestResultStatusType.NOT_RUNNING);
        return data;
    }

    public TestResultData save(@NonNull TestResultData testResultData) {
        return dao.saveAndFlush(testResultData);
    }

    public TestResultData save(@NonNull TestCodeData testCodeData, @NonNull MutationData mutationData) {
        TestResultData result = null;
        if (testCodeData.getId() == null || mutationData.getId() == null) {
            throw new RuntimeException("Test code data or mutation data not defined in the system");
        }
        Optional<TestResultData> founded = findByTestCodeDataAndAndMutationData(testCodeData, mutationData);
        if (founded.isPresent()) {
            result = founded.get();
        } else {
            TestResultData data = createTestResultData(testCodeData, mutationData);
            result = dao.saveAndFlush(data);
        }
        return result;
    }

    public Optional<TestResultData> findByTestCodeDataAndAndMutationData(TestCodeData testCodeData, MutationData mutationData) {
        return dao.findByTestCodeDataAndAndMutationData(testCodeData, mutationData);
    }

    public Optional<TestResultData> findById(@NonNull Long id) {
        return dao.findById(id);
    }

    public TestResultData getById(@NonNull Long id) {
        return dao.getOne(id);
    }

}
