package ru.mai.diplom.tester.component.gui;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.component.CommonGuiComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Билдер панели "Мутационные преобразования"
 */
@Component
@Getter
public class MutationOptionPanelBuilder implements GuiPanelBuilderInterface {

    @Override
    public JPanel build() {
        JPanel result = new JPanel(new BorderLayout());
        JPanel titlePanel = CommonGuiComponent.createTitlePanel("Мутационные преобразования", BorderLayout.CENTER);
        result.add(titlePanel, BorderLayout.NORTH);
        result.add(createOptionPanel(), BorderLayout.CENTER);
        return result;
    }

    private JPanel createOptionPanel() {
        JPanel result = new JPanel();
        LayoutManager layout = new GridLayout(3, 1, 3, 3);
        result.setLayout(layout);

        JPanel removeRowsPanel = createRemoveRowsPanel();

        JPanel revertPanel = createRevertRowsPanel();

        JPanel replacePanel = createReplaceRowsPanel();

        result.add(removeRowsPanel);
        result.add(revertPanel);
        result.add(replacePanel);

        return result;
    }

    private JPanel createRemoveRowsPanel() {
        JPanel result = new JPanel(new BorderLayout());

        CommonGuiComponent.removeRowsCheckBox.setFont(CommonGuiComponent.createCheckBoxFont());
        result.add(CommonGuiComponent.removeRowsCheckBox, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        panel.setLayout(layout);

        JLabel label = new JLabel("Номера строк:");
        label.setFont(CommonGuiComponent.createMainNormalFont());

        CommonGuiComponent.removeRowNumbersTextField.setFont(CommonGuiComponent.createMainItalicFont());
        CommonGuiComponent.removeRowNumbersTextField.setEnabled(true);
        CommonGuiComponent.removeRowNumbersTextField.setEditable(true);
        CommonGuiComponent.removeRowNumbersTextField.setColumns(15);

        panel.add(label);
        panel.add(CommonGuiComponent.removeRowNumbersTextField);

        panel.setBorder(CommonGuiComponent.marginBorder);

        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    private JPanel createRevertRowsPanel() {
        JPanel result = new JPanel(new BorderLayout());

        CommonGuiComponent.revertIfCheckBox.setFont(CommonGuiComponent.createCheckBoxFont());

        result.add(CommonGuiComponent.revertIfCheckBox, BorderLayout.CENTER);

        return result;
    }

    private JPanel createReplaceRowsPanel() {
        JPanel result = new JPanel(new BorderLayout());

        CommonGuiComponent.replaceRowsCheckBox.setFont(CommonGuiComponent.createCheckBoxFont());

        JPanel panel = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        panel.setLayout(layout);

        JLabel label1 = new JLabel("Замена");
        label1.setFont(CommonGuiComponent.createMainNormalFont());

        CommonGuiComponent.replacedCharTextField.setFont(CommonGuiComponent.createMainItalicFont());
        CommonGuiComponent.replacedCharTextField.setEnabled(true);
        CommonGuiComponent.replacedCharTextField.setEditable(true);
        CommonGuiComponent.replacedCharTextField.setColumns(15);

        JLabel label2 = new JLabel("на");
        label2.setFont(CommonGuiComponent.createMainNormalFont());

        CommonGuiComponent.replaceToCharTextField.setFont(CommonGuiComponent.createMainItalicFont());
        CommonGuiComponent.replaceToCharTextField.setEnabled(true);
        CommonGuiComponent.replaceToCharTextField.setEditable(true);
        CommonGuiComponent.replaceToCharTextField.setColumns(15);

        panel.add(label1);
        panel.add(CommonGuiComponent.replacedCharTextField);
        panel.add(label2);
        panel.add(CommonGuiComponent.replaceToCharTextField);

        panel.setBorder(CommonGuiComponent.marginBorder);

        result.add(CommonGuiComponent.replaceRowsCheckBox, BorderLayout.NORTH);
        result.add(panel, BorderLayout.CENTER);

        return result;
    }
}
