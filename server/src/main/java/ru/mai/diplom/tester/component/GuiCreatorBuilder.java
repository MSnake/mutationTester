package ru.mai.diplom.tester.component;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Сервис формирования графического интерфейса
 */
public class GuiCreatorBuilder {

    private AbstractBorder visibleBorder = new LineBorder(Color.black, 3);
    private EmptyBorder marginBorder = new EmptyBorder(10, 10, 10, 10);

    /**
     * Ширина экрана в px
     */
    private Integer screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

    /**
     * Высота экрана в px
     */
    private Integer screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public void build() {
        JFrame mainFrame = createMainFrame();
        JPanel mainPanel = createMainPanel();
        JPanel sourceCodePanel = createSourceCodePanel();
        JPanel testCodePanel = createTestCodePanel();
        mainPanel.add(sourceCodePanel);
        mainPanel.add(testCodePanel);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }

    public JFrame createMainFrame() {
        JFrame result = new JFrame("Мутационное тестирование");
        result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        result.setSize((int) (screenWidth / 1.5d), screenHeight - 100);
        result.setMinimumSize(new Dimension(800, 600));
        return result;
    }

    public JPanel createMainPanel() {
        JPanel result = new JPanel(new GridLayout(1, 2, 3, 3));
        result.setVisible(true);
        return result;
    }

    public JPanel createSourceCodePanel() {
        JPanel result = new JPanel(new BorderLayout());
        result.setBorder(marginBorder);

        JPanel titlePanel = createTitlePanel("Исходный код для тестирования");
        result.add(titlePanel, BorderLayout.NORTH);

        JScrollPane codeEditorPanel = createCodeEditorPanel();
        result.add(codeEditorPanel, BorderLayout.CENTER);

        result.setVisible(true);
        return result;
    }

    // TODO
    public JPanel createTestCodePanel() {
        JPanel result = new JPanel(new BorderLayout());
        result.setBorder(marginBorder);
        JPanel titlePanel = createTitlePanel("Код тестирования");
        result.add(titlePanel, BorderLayout.NORTH);

        JPanel testContentPanel = new JPanel(new BorderLayout());
        JScrollPane codeEditorPanel = createCodeEditorPanel();
        testContentPanel.add(codeEditorPanel, BorderLayout.NORTH);

        JPanel testOptionsPanel = new JPanel(new BorderLayout());
        testOptionsPanel.setBorder(visibleBorder);
        JPanel titleMutationPanel = createTitlePanel("Преобразования");
        testOptionsPanel.add(titleMutationPanel, BorderLayout.NORTH);

        JPanel testMutationOptionPanel = createMutationOptionPanel();
        testOptionsPanel.add(testMutationOptionPanel);
        testOptionsPanel.add(createsStartTestPanel());

        testContentPanel.add(testOptionsPanel);
        result.setVisible(true);
        return result;
    }

    private JScrollPane createCodeEditorPanel(){
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(true);
        editorPane.setBorder(visibleBorder);
        JScrollPane result = new JScrollPane(editorPane);
        result.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        TextLineN tln = new TextLineNumber(textPane);
//        JScrollPane.setRowHeaderView(tln);
        return result;
    }

    private JPanel createTitlePanel(String titleText) {
        JPanel result = new JPanel(new BorderLayout());
        JLabel title = new JLabel(titleText);
        Font font = new Font("Serif",Font.BOLD,25);
        title.setFont(font);
        result.add(title, BorderLayout.WEST);
        return result;
    }

    private JLabel createMutationLabel(String titleText) {
        JLabel result = new JLabel(titleText);
        Font font = new Font("Sans Serif",Font.ITALIC,12);
        result.setFont(font);
        return result;
    }

    private JPanel createMutationOptionPanel() {
        JPanel result = new JPanel(new GridLayout(3, 1, 3, 3));

        JPanel removeLinePanel = new JPanel(new GridLayout(2, 1, 3, 3));
        JCheckBox removeLineСheckBox = new JCheckBox("Удаление строк");
        removeLinePanel.add(removeLineСheckBox);
        JPanel removeLineOptionsPanel = new JPanel(new BorderLayout());
        JTextField removeLineOptionsText = new JTextField();
        removeLineOptionsPanel.add(createMutationLabel("Номера строк через запятую:"), BorderLayout.WEST);
        removeLineOptionsPanel.add(removeLineOptionsText, BorderLayout.CENTER);
        removeLinePanel.add(removeLineOptionsPanel);

        JCheckBox revertInIfCheckBox = new JCheckBox("Отрицание условных операторов");
        JCheckBox changeSymbolsCheckBox = new JCheckBox("Подмена знаков");

        result.add(removeLinePanel);
        result.add(revertInIfCheckBox);
        result.add(changeSymbolsCheckBox);
        return result;
    }

    private JPanel createsStartTestPanel() {
        JPanel result = new JPanel();
        JButton runTestButton = new JButton("Начать тестирование");
        result.add(runTestButton, BorderLayout.EAST);
        return result;
    }
}
