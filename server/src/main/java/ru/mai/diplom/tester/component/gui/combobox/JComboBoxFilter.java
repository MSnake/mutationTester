package ru.mai.diplom.tester.component.gui.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.db.model.TestResultData;
import ru.mai.diplom.tester.service.TestResultDataService;

import javax.swing.*;

@Component
public class JComboBoxFilter {

    @Autowired
    TestResultDataService testResultDataService;

    public JComboBox createComboBox() {
        java.util.List<TestResultData> testes = testResultDataService.findAll();
        JComboBox<TestResultData> comboBox = new JComboBox(
                testes.toArray(new TestResultData[testes.size()]));

        ComboBoxFilterDecorator<TestResultData> decorate = ComboBoxFilterDecorator.decorate(comboBox,
                CustomComboRenderer::getTestDisplayText,
                JComboBoxFilter::testesFilter);

        comboBox.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));
        return comboBox;
    }

    private static boolean testesFilter(TestResultData testResultData, String textToFilter) {
        if (textToFilter.isEmpty()) {
            return true;
        }
        return CustomComboRenderer.getTestDisplayText(testResultData).toLowerCase()
                .contains(textToFilter.toLowerCase());
    }
}