package ru.mai.diplom.tester.component.gui;

import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.component.CommonGuiComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Билдер панели "Мутационные преобразования"
 */
@Component
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
        JCheckBox removeRowsCheckBox =  new JCheckBox("Удаление строк");
        removeRowsCheckBox.setFont(CommonGuiComponent.createCheckBoxFont());
        result.add(removeRowsCheckBox, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        panel.setLayout(layout);

        JLabel label = new JLabel("Номера строк:");
        label.setFont(CommonGuiComponent.createMainNormalFont());

        JTextField rowNumbersTextField = new JTextField();
        rowNumbersTextField.setFont(CommonGuiComponent.createMainItalicFont());
        rowNumbersTextField.setEnabled(true);
        rowNumbersTextField.setEditable(true);
        rowNumbersTextField.setColumns(15);

        panel.add(label);
        panel.add(rowNumbersTextField);

        panel.setBorder(CommonGuiComponent.marginBorder);

        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    private JPanel createRevertRowsPanel() {
        JPanel result = new JPanel(new BorderLayout());

        JCheckBox removeRowsCheckBox =  new JCheckBox("Отрицание условных операторов");
        removeRowsCheckBox.setFont(CommonGuiComponent.createCheckBoxFont());

        result.add(removeRowsCheckBox, BorderLayout.CENTER);

        return result;
    }

    private JPanel createReplaceRowsPanel() {
        JPanel result = new JPanel(new BorderLayout());

        JCheckBox removeRowsCheckBox =  new JCheckBox("Подмена знаков");
        removeRowsCheckBox.setFont(CommonGuiComponent.createCheckBoxFont());

        JPanel panel = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        panel.setLayout(layout);

        JLabel label1 = new JLabel("Замена");
        label1.setFont(CommonGuiComponent.createMainNormalFont());

        JTextField replacedCharTextField = new JTextField();
        replacedCharTextField.setFont(CommonGuiComponent.createMainItalicFont());
        replacedCharTextField.setEnabled(true);
        replacedCharTextField.setEditable(true);
        replacedCharTextField.setColumns(15);

        JLabel label2 = new JLabel("на");
        label2.setFont(CommonGuiComponent.createMainNormalFont());

        JTextField replaceToCharTextField = new JTextField();
        replaceToCharTextField.setFont(CommonGuiComponent.createMainItalicFont());
        replaceToCharTextField.setEnabled(true);
        replaceToCharTextField.setEditable(true);
        replaceToCharTextField.setColumns(15);

        panel.add(label1);
        panel.add(replacedCharTextField);
        panel.add(label2);
        panel.add(replaceToCharTextField);

        panel.setBorder(CommonGuiComponent.marginBorder);

        result.add(removeRowsCheckBox, BorderLayout.NORTH);
        result.add(panel, BorderLayout.CENTER);

        return result;
    }
}
