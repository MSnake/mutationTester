package ru.mai.diplom.tester.component.actionlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.component.CommonGuiComponent;
import ru.mai.diplom.tester.db.model.TestResultData;
import ru.mai.diplom.tester.service.MutationService;
import ru.mai.diplom.tester.service.SourceCodeService;
import ru.mai.diplom.tester.service.TestCodeService;
import ru.mai.diplom.tester.service.TestResultDataService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Слушатель события нажатия кнопки "Заупустить тестирование"
 */
@Slf4j
@Component
public class StartTestButtonListener implements ActionListener {

    @Autowired
    private CommonUI commonUI;

    @Override
    public void actionPerformed(ActionEvent e) {
        TestResultData testResultData = commonUI.saveDataFromForm();
        commonUI.runTest(testResultData);
    }

}
