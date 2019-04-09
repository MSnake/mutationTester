package ru.mai.diplom.tester.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mai.diplom.tester.db.dao.TestResultDataDao;
import ru.mai.diplom.tester.db.model.MutationData;
import ru.mai.diplom.tester.db.model.TestCodeData;
import ru.mai.diplom.tester.db.model.TestResultData;
import ru.mai.diplom.tester.db.model.TestResultStatusType;

import java.util.Optional;

/**
 * Сервис для работы с результатами тестирования
 **/
@Slf4j
@Service
public class TestResultDataService {

    @Autowired
    TestResultDataDao dao;

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

    public TestResultData create(@NonNull TestCodeData testCodeData, @NonNull MutationData mutationData) {
        TestResultData data = createTestResultData(testCodeData, mutationData);
        return dao.saveAndFlush(data);
    }

    public Optional<TestResultData> findById(@NonNull Long id) {
        return dao.findById(id);
    }

    public TestResultData getById(@NonNull Long id) {
        return dao.getOne(id);
    }

}
