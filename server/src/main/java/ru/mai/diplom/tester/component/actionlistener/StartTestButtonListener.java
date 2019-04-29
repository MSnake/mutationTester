package ru.mai.diplom.tester.component.actionlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.db.model.TestResultData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Слушатель события нажатия кнопки "Заупустить тестирование"
 */
@Slf4j
@Component
public class StartTestButtonListener implements ActionListener {

    private final CommonUI commonUI;

    public StartTestButtonListener(CommonUI commonUI) {
        this.commonUI = commonUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TestResultData testResultData = commonUI.saveDataFromForm();
        if (testResultData != null) {
            commonUI.runTest(testResultData);
        } else {
            commonUI.createLogErrorInfo("Не удалось запустить тестирование");
        }
    }

}
