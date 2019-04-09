package ru.mai.diplom.tester.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mai.diplom.tester.db.dao.SourceCodeDataDao;
import ru.mai.diplom.tester.db.model.SourceCodeData;
import ru.mai.diplom.tester.utils.DigestUtils;

import java.util.Optional;

/**
 * Сервис для работы с исходным кодом
 **/
@Slf4j
@Service
public class SourceCodeService {

    @Autowired
    SourceCodeDataDao dao;

    public SourceCodeData createSourceCodeData(@NonNull String codeText) {
        SourceCodeData data = new SourceCodeData();
        data.setCodeText(codeText);
        data.setMd5Data(DigestUtils.getMd5(codeText));
        return data;
    }

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

    public SourceCodeData getById(@NonNull Long id) {
        return dao.getOne(id);
    }

    private long getNextId(){
        return dao.getNextSourceCodeId();
    }
}
