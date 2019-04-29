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

    private final CommonGuiComponent guiComponent;

    public SourceCodePanelBuilder(CommonGuiComponent guiComponent){
        this.guiComponent = guiComponent;
    }

    @Override
    public JPanel build() {
        JPanel result = new JPanel(new BorderLayout());
        JPanel sourceCodePanel = new JPanel(new BorderLayout());
        JPanel titleSourceCodePanel = CommonGuiComponent.createTitlePanel("Исходный код для тестирования", BorderLayout.WEST, CommonGuiComponent.createTitleFont());
        // TODO
        //titleSourceCodePanel.add(CommonGuiComponent.creteNameComboBoxPanel(guiComponent.testNameComboBox), BorderLayout.EAST);
        sourceCodePanel.setBorder(CommonGuiComponent.marginBorder);
        JScrollPane codeEditorPanel = CommonGuiComponent.createCodeScrollEditorPanel(CommonGuiComponent.sourceCodeEditorTextPane);
        sourceCodePanel.add(titleSourceCodePanel, BorderLayout.NORTH);
        sourceCodePanel.add(codeEditorPanel, BorderLayout.CENTER);
        sourceCodePanel.setPreferredSize(new Dimension(0, 450));

        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(CommonGuiComponent.marginBorder);
        logPanel.add(CommonGuiComponent.createTitlePanel("Результат тестирования", BorderLayout.WEST, CommonGuiComponent.createSubTitleFont()), BorderLayout.NORTH);
        JScrollPane resultEditorPanel = CommonGuiComponent.createLogScrollEditorPanel(CommonGuiComponent.resultEditorPane);
        logPanel.add(resultEditorPanel, BorderLayout.CENTER);

        result.add(sourceCodePanel, BorderLayout.NORTH);
        result.add(logPanel, BorderLayout.CENTER);
        result.setVisible(true);
        return result;
    }

}
