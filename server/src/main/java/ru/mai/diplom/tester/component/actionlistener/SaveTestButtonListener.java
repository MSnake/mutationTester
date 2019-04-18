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
 * Слушатель события нажатия кнопки "Сохранить тестирование"
 */
@Slf4j
@Component
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class SaveTestButtonListener implements ActionListener {

    @Autowired
    private SourceCodeService sourceCodeService;

    @Autowired
    private TestCodeService testCodeService;

    @Autowired
    private MutationService mutationService;

    @Autowired
    private TestResultDataService testResultDataService;

    @Autowired
    private CommonUI commonUI;

    @Override
    public void actionPerformed(ActionEvent e) {
        commonUI.saveDataFromForm();
}


}
