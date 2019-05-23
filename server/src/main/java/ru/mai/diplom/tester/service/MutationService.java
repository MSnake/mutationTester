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

import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис для работы мутированием исходного кода
 **/
@Slf4j
@Service
public class MutationService {

    /**
     * ДАО для работы с информацией о преобразованиях
     */
    @Autowired
    private MutationDataDao dao;

    /**
     * JSON преобразования
     */
    @Autowired
    private ObjectMapper mapper;

    /**
     * REGEX переход на новую строку
     */
    private final String NEW_ROW_REGEX = "\\r?\\n";

    /**
     *  Переход на новую строку
     */
    private final String NEW_LINE = "\n";

    /**
     * Сформировать обьект с информацией о преобразованиях
     *
     * @param sourceCodeText исходный код
     * @param options        список преобразований
     * @return обьект с информацией о преобразованиях
     */
    public MutationData createMutationData(@NonNull String sourceCodeText, List<MutationOption> options) {
        MutationData data = new MutationData();
        String mutatedCode = createMutationCodeFromSource(sourceCodeText, options);
        data.setCodeText(mutatedCode);
        data.setMd5Data(DigestUtils.getMd5(mutatedCode));
        data.setJsonData(toJson(options));
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

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Optional<MutationData> findByMd5Data(@NonNull String md5Data) {
        return dao.findByMd5Data(md5Data);
    }

    public MutationData getById(@NonNull Long id) {
        return dao.getOne(id);
    }

    /**
     * Конвертация списка с информацией о преобразованиях кода
     *
     * @param options спиок с информацией о преобразованиях кода
     * @return строка JSON
     */
    private String toJson(@NonNull List<MutationOption> options) {
        String result = null;
        try {
            result = mapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации JSON объекта.");
        }
        return result;
    }

    /**
     * Преобразование исходного кода
     *
     * @param sourceCodeText исходный код
     * @param options        список преобразований
     * @return мутант
     */
    public String createMutationCodeFromSource(@NonNull String sourceCodeText, List<MutationOption> options) {
        long startTime = System.nanoTime();
        // Убираем все коментарии в коде, без потери количества строк
        String sourceCodeCommentNone = removeComment(sourceCodeText);
        final String[] result = {sourceCodeCommentNone};
        if (options != null && !options.isEmpty()) {
            final long[] startSingle = {0};
            final long[] endSingle = {0};
            options.forEach(option -> {
                switch (option.getType()) {
                    case REVERT_IF:
                        startSingle[0] = System.nanoTime();
                        result[0] = revertIf(result[0]);
                        endSingle[0] = System.nanoTime();
                        log.info("REVERT_IF MUTATION TIME MS: " + ((endSingle[0] - startSingle[0])));
                        break;
                    case REPLACE_CHARS:
                        startSingle[0] = System.nanoTime();
                        result[0] = replaceCharacters(result[0], option.getReplaceOptions());
                        endSingle[0] = System.nanoTime();
                        log.info("REPLACE_CHARS MUTATION TIME MS: " + ((endSingle[0] - startSingle[0])));
                        break;
                    case REMOVE_ROWS:
                        startSingle[0] = System.nanoTime();
                        result[0] = removeLines(result[0], option.getRowNumbers());
                        endSingle[0] = System.nanoTime();
                        log.info("REMOVE_ROWS MUTATION TIME MS: " + ((endSingle[0] - startSingle[0])));
                        break;
                }
            });
        }
        long endTime = System.nanoTime();
        log.info("ALL MUTATION TIME MS: " + ((endTime - startTime)));
        return result[0];
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
                String updatedRow = modifyIfOperand(toModifyString);
                while (updatedRow == null) {
                    i++;
                    toModifyString = toModifyString + System.lineSeparator() + sourceCodeTextRows[i];
                    updatedRow = modifyIfOperand(toModifyString);
                }
                mutatedCode.append(updatedRow.toString());
            } else {
                mutatedCode.append(sourceCodeTextRows[i]);
                ;
            }
            i++;
            if (i < sourceCodeTextRows.length) mutatedCode.append(System.lineSeparator());
        }

        result = mutatedCode.toString();
        return result;
    }

    /**
     * Формирование мутанта путем удаления строк исходного кода
     *
     * @param sourceCodeText текст исходного кода
     * @param lineNumbers    номера строк для удаления
     * @return мутант
     */
    public String removeLines(@NonNull final String sourceCodeText, Set<Integer> lineNumbers) {
        String result = sourceCodeText;
        if (lineNumbers != null && !lineNumbers.isEmpty()) {
            String[] sourceCodeTextRows = sourceCodeText.split(NEW_ROW_REGEX);
            StringBuilder mutatedCode = new StringBuilder();
            for (int i = 0; i < sourceCodeTextRows.length; i++) {
                if (!lineNumbers.contains(i+1)) {
                    mutatedCode.append(sourceCodeTextRows[i]);
                    if (i < sourceCodeTextRows.length-1) mutatedCode.append(System.lineSeparator());
                }
            }
            result = (mutatedCode.lastIndexOf(System.lineSeparator()) == mutatedCode.length() - System.lineSeparator().length()) ?
                    mutatedCode.substring(0, mutatedCode.length() - System.lineSeparator().length()) :
                    mutatedCode.toString();
        }
        return result;
    }

    /**
     * Формирование мутанта путем замены исходных символов на указанные
     *
     * @param sourceCodeText текст исходного кода
     * @param replaceMap     соответствие какие символы должны быть заменены на какие(ключ - исходный символ/значение - заменяемый символ)
     * @return мутант
     */
    public String replaceCharacters(@NonNull final String sourceCodeText, Map<String, String> replaceMap) {
        String result = sourceCodeText;
        if (replaceMap != null && !replaceMap.keySet().isEmpty()) {
            String[] sourceCodeTextRows = sourceCodeText.split(NEW_ROW_REGEX);
            StringBuilder mutatedCode = new StringBuilder();
            for (int i = 0; i < sourceCodeTextRows.length; i++) {
                List<String> rowsAfterReplace = new ArrayList<>();
                int finalI = i;
                replaceMap.keySet().forEach(key -> rowsAfterReplace.add(sourceCodeTextRows[finalI].replaceAll(key, replaceMap.get(key))));
                char[] sourceRowChars = new char[sourceCodeTextRows[i].length()];
                for (int j = 0; j < sourceCodeTextRows[i].length(); j++) {
                    char etalonChar = sourceCodeTextRows[i].charAt(j);
                    int finalJ = j;
                    Optional<String> founded = rowsAfterReplace.stream().filter(data -> data.charAt(finalJ) != etalonChar).findFirst();
                    sourceRowChars[j] = (founded.isPresent()) ? founded.get().charAt(j) : etalonChar;
                }
                mutatedCode.append(new String(sourceRowChars));
                if (i < sourceCodeTextRows.length) mutatedCode.append(System.lineSeparator());
            }
            result = (mutatedCode.lastIndexOf(System.lineSeparator()) == mutatedCode.length() - System.lineSeparator().length()) ?
                    mutatedCode.substring(0, mutatedCode.length() - System.lineSeparator().length()) :
                    mutatedCode.toString();
        }
        return result;
    }

    /**
     * Проверка является ли строка сбалансированной(количество открывающихся круглых скобок == количеству закрывающихся круглых скобок)
     *
     * @param input строка с текстом
     * @return true - сбалансированная, иначе false
     */
    private boolean isBalanced(final String input) {
        if (input == null)
            return false;
        int size = input.length();
        int countStartParenthesis = 0;
        int countEndParenthesis = 0;
        for (int i = 0; i < size; i++) {
            Character temp = input.charAt(i);
            if (temp.charValue() == '(') countStartParenthesis++;
            if (temp.charValue() == ')') countEndParenthesis++;
        }
        return (countStartParenthesis == countEndParenthesis);
    }


    /**
     * Преобразование IF операнда в строке исходного кода(отрициание)
     *
     * @param ifString строка исходного кода содержащая IF операнд
     * @return преобразованная строка исходного кода
     */
    private String modifyIfOperand(String ifString) {
        String result = null;
        boolean isIfRowWasEnded = isBalanced(ifString);
        if (isIfRowWasEnded) {
            String[] splitModifyRow = ifString.split(" ");
            for (int i = 0; i < splitModifyRow.length; i++) {
                if (splitModifyRow[i].equalsIgnoreCase("if")) {
                    splitModifyRow[i + 1] = "(!" + splitModifyRow[i + 1];
                    break;
                }
            }
            StringBuilder compileRow = new StringBuilder();
            for (int j = 0; j < splitModifyRow.length; j++) {
                if (j != 0 && !splitModifyRow[j - 1].equalsIgnoreCase(" ")) compileRow.append(" ");
                compileRow.append(splitModifyRow[j]);
            }
            int toCloseInvertIndex = compileRow.lastIndexOf(") ");
            compileRow.insert(toCloseInvertIndex, ")");
            result = compileRow.toString();
        }

        return result;
    }

    /**
     * Убираем все коментарии в коде, без потери количества строк
     *
     * @param sourceCode исходный код
     * @return код после удаления коментариев
     */
    private String removeComment(String sourceCode) {
        String[] splited = sourceCode.split(NEW_ROW_REGEX);
        int i = 0;
        boolean isPartOfMultiplyComment = false;
        String endCommentRegEx = "^(\\*)+(\\/){1,}";
        Pattern p = Pattern.compile(endCommentRegEx);
        while (i < splited.length) {
            boolean toReplace = false;
            String trimedLine = splited[i].trim();
            if (trimedLine.length() > 0) {
                Character firstChar = trimedLine.charAt(0);
                if (firstChar.charValue() == '/') {
                    Character secondChar = trimedLine.charAt(1);
                    if (secondChar.charValue() == '*') {
                        isPartOfMultiplyComment = true;
                        toReplace = true;
                    }
                    if (secondChar.charValue() == '/') {
                        toReplace = true;
                    }
                }
            }
            Matcher m = p.matcher(trimedLine);
            if (m.matches() && m.group().equalsIgnoreCase(trimedLine)) {
                isPartOfMultiplyComment = false;
                toReplace = true;
            }
            if (isPartOfMultiplyComment) toReplace = true;
            splited[i] = (toReplace) ? "" : splited[i];
            i++;
        }
        StringBuilder resultBuilder = new StringBuilder();
        for (int j = 0; j < splited.length; j++) {
            resultBuilder.append(splited[j]);
            if (j <splited.length-1){
                resultBuilder.append(System.lineSeparator());
            }
        }
        return resultBuilder.toString();
    }

}
