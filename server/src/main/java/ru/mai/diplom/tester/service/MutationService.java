package ru.mai.diplom.tester.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.mai.diplom.tester.component.CommonGuiComponent;
import ru.mai.diplom.tester.db.dao.MutationDataDao;
import ru.mai.diplom.tester.db.model.MutationData;
import ru.mai.diplom.tester.db.model.SourceCodeData;
import ru.mai.diplom.tester.model.MutationOption;
import ru.mai.diplom.tester.model.MutationType;
import ru.mai.diplom.tester.utils.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы мутированием исходного кода
 **/
@Slf4j
@Service
public class MutationService {

    @Autowired
    private MutationDataDao dao;

    @Autowired
    private ObjectMapper mapper;

    public MutationData createMutationData(@NonNull String sourceCodeText, List<MutationOption> options) {
        MutationData data = null;
        String mutatedCode = createMutationCodeFromSource(sourceCodeText, options);
        String md5 = DigestUtils.getMd5(mutatedCode);
        Optional<MutationData> byMd5Data = findByMd5Data(md5);
        if (byMd5Data.isPresent()) {
            data = byMd5Data.get();
        } else {
            data = new MutationData();
            data.setCodeText(mutatedCode);
            data.setMd5Data(DigestUtils.getMd5(mutatedCode));
            data.setJsonData(toJson(options));
        }
        return data;
    }

    public MutationData save(@NonNull MutationData mutationData) {
        return dao.saveAndFlush(mutationData);
    }

    public MutationData save(@NonNull String testCodeText, List<MutationOption> options) {
        MutationData data = createMutationData(testCodeText, options);
        return dao.saveAndFlush(data);
    }

    public Optional<MutationData> findById(@NonNull Long id) {
        return dao.findById(id);
    }

    public Optional<MutationData> findByMd5Data(@NonNull String md5Data) {
        return dao.findByMd5Data(md5Data);
    }

    public MutationData getById(@NonNull Long id) {
        return dao.getOne(id);
    }

    private String toJson(@NonNull List<MutationOption> options) {
        String result = null;
        try {
            result = mapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации JSON объекта.");
        }
        return result;
    }

    private String createMutationCodeFromSource(@NonNull String sourceCodeText, List<MutationOption> options) {
        String result = null;
        // TODO произвести мутации в зависимости от опций мутирования
        result = sourceCodeText;
        return result;
    }

}
