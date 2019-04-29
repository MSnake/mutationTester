package ru.mai.diplom.tester.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mai.diplom.tester.db.dao.TestCodeDataDao;
import ru.mai.diplom.tester.db.model.TestCodeData;
import ru.mai.diplom.tester.utils.DigestUtils;

import java.util.Optional;

/**
 * Сервис для работы с кодом тестирования
 **/
@Slf4j
@Service
public class TestCodeService {

    /**
     * Дао для работы с кодом тестирования
     */
    @Autowired
    private TestCodeDataDao dao;

    /**
     * Сервис для работы с результатами тестирования
     */
    @Autowired
    private TestResultDataService testResultDataService;

    /**
     * Сформировать обьект с информацией о коде тестирования
     *
     * @param codeText код тестирования
     * @return обьект с информацией о коде тестирования
     */
    public TestCodeData createTestCodeData(@NonNull String codeText) {
        TestCodeData data = new TestCodeData();
        String md5 = DigestUtils.getMd5(codeText);
        data.setCodeText(codeText);
        data.setMd5Data(md5);
//        Optional<TestCodeData> byMd5Data = findByMd5Data(md5);
//        if (byMd5Data.isPresent()) {
//            data = byMd5Data.get();
//        } else {
//            data = new TestCodeData();
//            data.setCodeText(codeText);
//            data.setMd5Data(md5);
//        }
        return data;
    }

    public TestCodeData save(@NonNull TestCodeData sourceCodeData) {
        return dao.saveAndFlush(sourceCodeData);
    }

    public TestCodeData save(@NonNull String codeText) {
        TestCodeData data = createTestCodeData(codeText);
        TestCodeData saved = dao.saveAndFlush(data);
        return saved;
    }

    public Optional<TestCodeData> findById(@NonNull Long id) {
        return dao.findById(id);
    }

    public Optional<TestCodeData> findByMd5Data(@NonNull String md5Data) {
        return dao.findByMd5Data(md5Data);
    }

    public TestCodeData getById(@NonNull Long id) {
        return dao.getOne(id);
    }

}
