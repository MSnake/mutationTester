package ru.mai.diplom.tester.runner;

import javax.swing.*;

public class GUIBuilder {
    private JEditorPane sourceCodeEditPane;
    private JEditorPane testCodeEditorPane;
    private JCheckBox removeRowsCheckBox;
    private JCheckBox revertIfCheckBox;
    private JCheckBox replaceCharCheckBox;
    private JPanel contentPanel;
    private JPanel sourceCodePanel;
    private JPanel testCodePanel;
    private JPanel testPanel;
    private JPanel testTitlePanel;
    private JScrollPane testCodeArea;
    private JScrollPane sourceCodeArea;
    private JPanel mutationOptionsPanel;
    private JPanel metationOptionsTitlePanel;
    private JPanel removeRowsPanel;
    private JPanel revertPanel;
    private JPanel replacePanel;
    private JPanel startTestPanel;
    private JButton startTestButton;
    private JTextField replacedCharTextField;
    private JTextField replaceToCharTextField;
    private JTextField rowNumbersTextField;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
