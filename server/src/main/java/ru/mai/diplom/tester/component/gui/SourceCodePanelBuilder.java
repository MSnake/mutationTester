package ru.mai.diplom.tester.component.gui;

import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.component.CommonGuiComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Билдер панели "Исходный код для тестирования"
 */
@Component
public class SourceCodePanelBuilder implements GuiPanelBuilderInterface {

    @Override
    public JPanel build() {
        JPanel result = new JPanel(new BorderLayout());
        result.setBorder(CommonGuiComponent.marginBorder);
        result.add(CommonGuiComponent.createTitlePanel("Исходный код для тестирования", BorderLayout.WEST), BorderLayout.NORTH);
        JScrollPane codeEditorPanel = CommonGuiComponent.createCodeEditorPanel();
        result.add(codeEditorPanel, BorderLayout.CENTER);
        result.setVisible(true);
        return result;
    }
}
