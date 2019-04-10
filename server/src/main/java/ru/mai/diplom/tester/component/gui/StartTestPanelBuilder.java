package ru.mai.diplom.tester.component.gui;

import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.component.CommonGuiComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Билдер панели "Запуска тестирования"
 */
@Component
public class StartTestPanelBuilder implements GuiPanelBuilderInterface {

    @Override
    public JPanel build() {
        JPanel result = new JPanel(new BorderLayout());
        result.setBorder(CommonGuiComponent.marginBorder);
        CommonGuiComponent.startTestButton.setFont(CommonGuiComponent.createFunctionalFont());
        result.add(CommonGuiComponent.startTestButton, BorderLayout.SOUTH);
        return result;
    }
}
