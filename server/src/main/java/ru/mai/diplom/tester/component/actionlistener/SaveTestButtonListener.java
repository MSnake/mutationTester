package ru.mai.diplom.tester.component.actionlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Слушатель события нажатия кнопки "Сохранить тестирование"
 */
@Slf4j
@Component
public class SaveTestButtonListener implements ActionListener {

    private final CommonUI commonUI;

    public SaveTestButtonListener(CommonUI commonUI) {
        this.commonUI = commonUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        commonUI.saveDataFromForm();
    }


}
