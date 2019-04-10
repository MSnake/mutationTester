package ru.mai.diplom.tester.component.actionlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.mai.diplom.tester.component.CommonGuiComponent;
import ru.mai.diplom.tester.db.model.MutationData;
import ru.mai.diplom.tester.db.model.SourceCodeData;
import ru.mai.diplom.tester.db.model.TestCodeData;
import ru.mai.diplom.tester.model.MutationOption;
import ru.mai.diplom.tester.model.MutationType;
import ru.mai.diplom.tester.service.MutationService;
import ru.mai.diplom.tester.service.SourceCodeService;
import ru.mai.diplom.tester.service.TestCodeService;
import ru.mai.diplom.tester.service.TestResultDataService;

import javax.transaction.Transactional;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Слушатель события нажатия кнопки "Заупустить тестирование
 */
@Slf4j
@Component
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class StartTestButtonListener implements ActionListener {

    @Autowired
    private SourceCodeService sourceCodeService;

    @Autowired
    private TestCodeService testCodeService;

    @Autowired
    private MutationService mutationService;

    @Autowired
    private TestResultDataService testResultDataService;

    @Override
    public void actionPerformed(ActionEvent e) {
        SourceCodeData data = sourceCodeService.createSourceCodeData(CommonGuiComponent.sourceCodeEditorPane.getText());
        TestCodeData testCodeData = testCodeService.createTestCodeData(CommonGuiComponent.testCodeEditorPane.getText());
        data.addTestCodeData(testCodeData);
        MutationData mutationData = mutationService.createMutationData(data.getCodeText(), createMutationOptions());
        data.addMutationData(mutationData);
        sourceCodeService.save(data);
        testResultDataService.create(testCodeData, mutationData);
    }

    private List<MutationOption> createMutationOptions() {
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

}
