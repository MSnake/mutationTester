package ru.mai.diplom.tester.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mai.diplom.tester.db.dao.SourceCodeDataDao;
import ru.mai.diplom.tester.db.model.SourceCodeData;
import ru.mai.diplom.tester.utils.DigestUtils;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Сервис для работы с исходным кодом
 **/
@Slf4j
@Service
public class SourceCodeService {

    /**
     * Дао для работы с информацией об исходном коде
     */
    @Autowired
    private SourceCodeDataDao dao;

    /**
     * Сформировать обьект с информацией об исходном коде
     *
     * @param sourceCodeText исходный код
     * @return обьект с информацией об исходном коде
     */
    public SourceCodeData createSourceCodeData(@NonNull String sourceCodeText) {
        SourceCodeData data = null;
        String md5 = DigestUtils.getMd5(sourceCodeText);
        Optional<SourceCodeData> byMd5Data = findByMd5Data(md5);
        if (byMd5Data.isPresent()) {
            data = byMd5Data.get();
        } else {
            data = new SourceCodeData();
            data.setCodeText(sourceCodeText);
            data.setMd5Data(md5);
        }
        return data;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public SourceCodeData save(@NonNull SourceCodeData sourceCodeData) {
        return dao.saveAndFlush(sourceCodeData);
    }

    public SourceCodeData save(@NonNull String codeText) {
        SourceCodeData data = createSourceCodeData(codeText);
        return dao.saveAndFlush(data);
    }

    public Optional<SourceCodeData> findById(@NonNull Long id) {
        return dao.findById(id);
    }

    public Optional<SourceCodeData> findByMd5Data(@NonNull String md5Data) {
        return dao.findByMd5Data(md5Data);
    }

    public SourceCodeData getById(@NonNull Long id) {
        return dao.getOne(id);
    }

    public long getNextId() {
        return dao.getNextSourceCodeId();
    }
}
