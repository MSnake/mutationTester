package ru.mai.diplom.tester.component.gui;

import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.component.CommonGuiComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Билдер панели "Код тестирования"
 */
@Component
public class TestCodePanelBuilder implements GuiPanelBuilderInterface {

    @Override
    public JPanel build() {
        JPanel result = new JPanel(new BorderLayout());
        result.setBorder(CommonGuiComponent.marginBorder);
        result.add(CommonGuiComponent.createTitlePanel("Код тестирования", BorderLayout.WEST), BorderLayout.NORTH);
        JScrollPane codeEditorPanel = CommonGuiComponent.createCodeEditorPanel();
        result.add(codeEditorPanel,BorderLayout.CENTER);
        return result;
    }
}
