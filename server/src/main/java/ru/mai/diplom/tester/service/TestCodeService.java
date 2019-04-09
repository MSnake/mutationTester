package ru.mai.diplom.tester.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mai.diplom.tester.db.dao.SourceCodeDataDao;
import ru.mai.diplom.tester.db.dao.TestCodeDataDao;
import ru.mai.diplom.tester.db.model.SourceCodeData;
import ru.mai.diplom.tester.db.model.TestCodeData;

import java.util.Optional;

/**
 * Сервис для работы с кодом тестирования
 **/
@Slf4j
@Service
public class TestCodeService {

    @Autowired
    TestCodeDataDao dao;

    public TestCodeData createTestCodeData(@NonNull String codeText) {
        TestCodeData data = new TestCodeData();
        data.setCodeText(codeText);
        return data;
    }

    public TestCodeData save(@NonNull TestCodeData sourceCodeData) {
        return dao.saveAndFlush(sourceCodeData);
    }

    public TestCodeData save(@NonNull String codeText) {
        TestCodeData data = createTestCodeData(codeText);
        return dao.saveAndFlush(data);
    }

    public Optional<TestCodeData> findById(@NonNull Long id) {
        return dao.findById(id);
    }

    public TestCodeData getById(@NonNull Long id) {
        return dao.getOne(id);
    }

}
