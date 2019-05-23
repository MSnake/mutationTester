package ru.mai.diplom.tester.component.actionlistener;

import com.sun.tools.internal.ws.wsdl.framework.ValidationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.mai.diplom.tester.component.CommonGuiComponent;
import ru.mai.diplom.tester.db.model.MutationData;
import ru.mai.diplom.tester.db.model.SourceCodeData;
import ru.mai.diplom.tester.db.model.TestCodeData;
import ru.mai.diplom.tester.db.model.TestResultData;
import ru.mai.diplom.tester.model.MutationOption;
import ru.mai.diplom.tester.model.MutationType;
import ru.mai.diplom.tester.service.*;

import javax.swing.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Базовые методы взаимодействия с пользовательским интерфейсом
 */
@Component
public class CommonUI {

    /**
     *  Переход на новую строку
     */
    private final String NEW_LINE = "\n";

    private final SourceCodeService sourceCodeService;
    private final TestCodeService testCodeService;
    private final MutationService mutationService;
    private final TestResultDataService testResultDataService;
    private final TestRunnerService testRunnerService;
    private final CommonGuiComponent guiComponent;

    public CommonUI(SourceCodeService sourceCodeService, TestCodeService testCodeService, MutationService mutationService,
                    TestResultDataService testResultDataService, TestRunnerService testRunnerService, CommonGuiComponent guiComponent) {
        this.sourceCodeService = sourceCodeService;
        this.testCodeService = testCodeService;
        this.mutationService = mutationService;
        this.testResultDataService = testResultDataService;
        this.testRunnerService = testRunnerService;
        this.guiComponent = guiComponent;

    }

    public List<MutationOption> createMutationOptions() {
        List<MutationOption> result = new ArrayList<>();
        if (CommonGuiComponent.removeRowsCheckBox.isSelected() && StringUtils.hasText(CommonGuiComponent.removeRowNumbersTextField.getText())) {
            MutationOption mutation = new MutationOption();
            mutation.setType(MutationType.REMOVE_ROWS);
            String[] split = CommonGuiComponent.removeRowNumbersTextField.getText().split(",");
            mutation.addRowNumbers(Arrays.asList(split).stream().map(data -> Integer.valueOf(data)).collect(Collectors.toList()));
            result.add(mutation);
        }
        if (CommonGuiComponent.replaceRowsCheckBox.isSelected()
                && StringUtils.hasText(CommonGuiComponent.replacedCharTextField.getText())
                && StringUtils.hasText(CommonGuiComponent.replaceToCharTextField.getText())) {
            MutationOption mutation = new MutationOption();
            mutation.setType(MutationType.REPLACE_CHARS);
            String[] splitReplaced = CommonGuiComponent.replacedCharTextField.getText().split(",");
            String[] splitReplaceTo = CommonGuiComponent.replaceToCharTextField.getText().split(",");
            for (int i = 0; i < splitReplaced.length; i++) {
                mutation.putReplaceOption(splitReplaced[i], splitReplaceTo[i]);
            }
            result.add(mutation);
        }
        if (CommonGuiComponent.revertIfCheckBox.isSelected()) {
            MutationOption mutation = new MutationOption();
            mutation.setType(MutationType.REVERT_IF);
            result.add(mutation);
        }

        return result;
    }

    public TestResultData saveDataFromForm() {
        TestResultData result = null;
        SourceCodeData data = sourceCodeService.createSourceCodeData(CommonGuiComponent.sourceCodeEditorTextPane.getText());
        TestCodeData testCodeData = testCodeService.createTestCodeData(CommonGuiComponent.testCodeEditorTextPane.getText());
        MutationData mutationData = mutationService.createMutationData(data.getCodeText(), createMutationOptions());
        if (data.getId() != null) {
            if (data.getTestCodeDataList() != null && !data.getTestCodeDataList().isEmpty()) {
                TestCodeData finalTestCodeData = testCodeData;
                Optional<TestCodeData> foundedTest = data.getTestCodeDataList().stream()
                        .filter(test -> test.getMd5Data().equalsIgnoreCase(finalTestCodeData.getMd5Data())).findAny();
                if (foundedTest.isPresent()) {
                    testCodeData = foundedTest.get();
                } else {
                    data.addTestCodeData(testCodeData);
                }
            }
            if (data.getMutationDataList() != null && !data.getMutationDataList().isEmpty()) {
                MutationData finalMutationData = mutationData;
                Optional<MutationData> foundedMutation = data.getMutationDataList().stream()
                        .filter(mutation -> mutation.getMd5Data().equalsIgnoreCase(finalMutationData.getMd5Data())).findAny();
                if (foundedMutation.isPresent()) {
                    mutationData = foundedMutation.get();
                } else {
                    data.addMutationData(mutationData);
                }
            } else {
                data.addMutationData(mutationData);
            }
        } else {
            data.addTestCodeData(testCodeData);
            data.addMutationData(mutationData);
        }
        sourceCodeService.save(data);
        result = testResultDataService.save(testCodeService.findByMd5Data(testCodeData.getMd5Data()).get(), mutationService.findByMd5Data(mutationData.getMd5Data()).get());
        // TODO
//        try {
//            TestResultData testNameItem = (TestResultData) guiComponent.testNameComboBox.getEditor().getItem();
//            if (StringUtils.hasText(testNameItem.getTestName())) {
//                sourceCodeService.save(data);
//                result = testResultDataService.save(testNameItem.getTestName(), testCodeData, mutationData);
//            } else {
//                createLogErrorInfo("Внимание! Информация о тестировании не может быть сохранена в системе, так как отсутствует название тестирования.");
//            }
//        } catch (ValidationException ex) {
//            createLogErrorInfo(ex.getKey());
//        } catch (Exception ex) {
//            createLogErrorInfo(ex.getMessage());
//        }
//        List<TestResultData> testes = testResultDataService.findAll();
//        guiComponent.testNameComboBox.setModel(new DefaultComboBoxModel<TestResultData>(testes.toArray(new TestResultData[testes.size()])));
        return result;
    }

    @Async
    public void runTest(TestResultData data) {
        TestResultData testResult = testRunnerService.runTest(data);
        createResultTestInfo(testResult);
    }

    private void createResultTestInfo(TestResultData testResult) {
        CommonGuiComponent.resultEditorPane.append("***************************************************************************************");
        CommonGuiComponent.resultEditorPane.append(NEW_LINE);
        CommonGuiComponent.resultEditorPane.append("Тестирование завершено");
        CommonGuiComponent.resultEditorPane.append(NEW_LINE);
        CommonGuiComponent.resultEditorPane.append("Статус проведенного теста: " + testResult.getStatus().name());
        CommonGuiComponent.resultEditorPane.append(NEW_LINE);
        CommonGuiComponent.resultEditorPane.append("Заключение о проведенном тесте: ->");
        CommonGuiComponent.resultEditorPane.append(NEW_LINE);
        String[] split = testResult.getResultText().split(System.lineSeparator());
        for (int i = 0; i < split.length; i++) {
            CommonGuiComponent.resultEditorPane.append("                                  ");
            CommonGuiComponent.resultEditorPane.append(split[i]);
            CommonGuiComponent.resultEditorPane.append(NEW_LINE);
        }
    }

    public void createLogErrorInfo(String errorString) {
        CommonGuiComponent.resultEditorPane.append("***************************************************************************************");
        CommonGuiComponent.resultEditorPane.append(NEW_LINE);
        CommonGuiComponent.resultEditorPane.append("Ошибка обработки данных");
        CommonGuiComponent.resultEditorPane.append(NEW_LINE);
        String[] split = errorString.split(System.lineSeparator());
        for (int i = 0; i < split.length; i++) {
            CommonGuiComponent.resultEditorPane.append(split[i]);
            CommonGuiComponent.resultEditorPane.append(NEW_LINE);
        }
    }

}
