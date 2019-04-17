package ru.mai.diplom.tester.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mai.diplom.tester.db.dao.MutationDataDao;
import ru.mai.diplom.tester.db.model.MutationData;
import ru.mai.diplom.tester.model.MutationOption;
import ru.mai.diplom.tester.utils.DigestUtils;

import java.util.List;
import java.util.Optional;

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

    private static final String NEW_ROW_REGEX = "\\r?\\n";

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

    /**
     * Формирование мутанта путем отрицания условных операторов
     *
     * @param sourceCodeText текст исходного кода
     * @return мутант
     */
    public String revertIf(@NonNull final String sourceCodeText) {
        String result = null;
        String[] sourceCodeTextRows = sourceCodeText.split(NEW_ROW_REGEX);
        StringBuilder mutatedCode = new StringBuilder();
        int i = 0;
        while (i < sourceCodeTextRows.length) {
            if (sourceCodeTextRows[i].contains("if")) {
                String toModifyString = sourceCodeTextRows[i];
                String whiteSpacePrefix = toModifyString.substring(0, toModifyString.indexOf("if"));
                String updatedRow = modifyIfOperand(toModifyString.trim());
                while (updatedRow == null) {
                    i++;
                    toModifyString = toModifyString + sourceCodeTextRows[i];
                    updatedRow = modifyIfOperand(toModifyString.trim());
                }
                mutatedCode.append(updatedRow.toString());
                i++;
            } else {
                mutatedCode.append(sourceCodeTextRows[i]);
                i++;
            }
        }
        result = mutatedCode.toString();
        return result;
    }

    /**
     * Формирование мутанта путем удаления строк исходного кода
     *
     * @param sourceCodeText текст исходного кода
     * @param lineNumbers    номер строк для удаления
     * @return мутант
     */
    private String removeLines(@NonNull final String sourceCodeText, List<Integer> lineNumbers) {
        String result = sourceCodeText;
        if (lineNumbers != null && !lineNumbers.isEmpty()) {
            String[] sourceCodeTextRows = sourceCodeText.split(NEW_ROW_REGEX);
            StringBuilder mutatedCode = new StringBuilder();
            for (int i = 0; i < sourceCodeTextRows.length; i++) {
                if (!lineNumbers.contains(i)) mutatedCode.append(sourceCodeTextRows[i]);
            }
            result = mutatedCode.toString();
        }
        return result;
    }

    public static boolean isBalanced(String input) {
        if (input == null)
            return false;
        int size = input.length();
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < size; i++) {
            Character temp = input.charAt(i);
            if (temp.charValue() == '(') count1++;
            if (temp.charValue() == ')') count2++;
        }

        return (count1 == count2);
    }

    private String modifyIfOperand(String ifString) {
        String result = null;
        boolean isIfRowWasEnded = isBalanced(ifString);
        if (isIfRowWasEnded) {
            String[] splitModifyRow = ifString.split(" ");
            splitModifyRow[1] = "(!" + splitModifyRow[1];
            StringBuilder compileRow = new StringBuilder();
            for (int j = 0; j < splitModifyRow.length; j++) {
                if (j != 0) compileRow.append(" ");
                compileRow.append(splitModifyRow[j]);
            }
            compileRow.lastIndexOf(") ");
            compileRow.insert(compileRow.lastIndexOf(") "), ")");
            result = compileRow.toString();
        }
        return result;
    }

}
