package ru.mai.diplom.tester.component;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Общие компоненты для построения графического интерфейса
 */
public class CommonGuiComponent {


    public static JCheckBox removeRowsCheckBox = new JCheckBox("Удаление строк");

    public static JCheckBox revertIfCheckBox = new JCheckBox("Отрицание условных операторов");

    public static JCheckBox replaceRowsCheckBox = new JCheckBox("Подмена знаков");

    public static JTextField removeRowNumbersTextField = new JTextField();

    public static JTextField replacedCharTextField = new JTextField();

    public static JTextField replaceToCharTextField = new JTextField();

    public static JTextArea sourceCodeEditorPane = new JTextArea();

    public static JTextArea testCodeEditorPane = new JTextArea();

    public static JButton saveTestButton = new JButton("Сохранить тест");

    public static JButton startTestButton = new JButton("Запустить тест");

    public static JTextArea resultEditorPane = new JTextArea();


    /**
     * Ширина экрана в px
     */
    public static Integer screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

    /**
     * Высота экрана в px
     */
    public static Integer screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    /**
     * Ширина графического интерфейса приложения по умолчанию
     */
    public static Integer appDefaultWidth = 1024;

    /**
     * Высота графического интерфейса приложения по умолчанию
     */
    public static Integer appDefaultHeight = 768;

    /**
     * Видимая граница из линии
     */
    public static AbstractBorder visibleLineBorder = new LineBorder(Color.black, 3);

    /**
     * Пустая граница, представляет собой отсуп
     */
    public static EmptyBorder marginBorder = new EmptyBorder(5, 5, 5, 5);

    /**
     * Название шрифта
     */
    public static String GUI_FONT_NAME = "Serif";

    /**
     * Создание панели заголовка
     *
     * @param titleText  текст заголовка
     * @param textLayout расположение текста заголовка из BorderLayout
     * @return панель заголовка с текстом
     */
    public static JPanel createTitlePanel(String titleText, String textLayout, Font font) {
        JPanel result = new JPanel();
        LayoutManager layout = new BorderLayout();
        result.setLayout(layout);
        result.setAlignmentX(25f);
        result.setAlignmentY(25f);
        JLabel title = new JLabel(titleText);
        title.setFont(font);
        result.add(title, textLayout);
        return result;
    }

    /**
     * Создание стиля для текста заголовка
     *
     * @return стиль текста заголовка
     */
    public static Font createTitleFont() {
        return new Font(GUI_FONT_NAME, Font.BOLD, 20);
    }

    /**
     * Создание стиля для текста второстепенного заголовка
     *
     * @return стиль текста второстепенного заголовка
     */
    public static Font createSubTitleFont() {
        return new Font(GUI_FONT_NAME, Font.ITALIC, 20);
    }

    /**
     * Создание стиля для текста функий
     *
     * @return стиль текста функий
     */
    public static Font createFunctionalFont() {
        return new Font(GUI_FONT_NAME, Font.BOLD + Font.ITALIC, 20);
    }

    /**
     * Создание стиля для текста чек-боксов
     *
     * @return стиль текста чек-боксов
     */
    public static Font createCheckBoxFont() {
        return new Font(GUI_FONT_NAME, Font.PLAIN, 16);
    }

    /**
     * Создание стиля для основного текста
     *
     * @return стиль для основного текста
     */
    public static Font createMainNormalFont() {
        return new Font(GUI_FONT_NAME, Font.PLAIN, 14);
    }

    /**
     * Создание стиля для основного текста с наклоном
     *
     * @return стиль для основного текста с наклоном
     */
    public static Font createMainItalicFont() {
        return new Font(GUI_FONT_NAME, Font.ITALIC, 14);
    }

    /**
     * Создание панели редактирования кода
     *
     * @return панель редактирования кода
     */
    public static JScrollPane createCodeScrollEditorPanel(JTextArea editorPane) {
        editorPane.setEditable(true);
        editorPane.setBorder(visibleLineBorder);
        JScrollPane result = new JScrollPane(editorPane);
        result.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        result.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        TextLineN tln = new TextLineNumber(textPane);
//        JScrollPane.setRowHeaderView(tln);
        return result;
    }

    /**
     * Создание панели вывода результатов
     *
     * @return панель вывода результатов
     */
    public static JScrollPane createLogScrollEditorPanel(JTextArea editorPane) {
        editorPane.setEditable(false);
        editorPane.setBorder(visibleLineBorder);
        JScrollPane result = new JScrollPane(editorPane);
        result.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        result.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        TextLineN tln = new TextLineNumber(textPane);
//        JScrollPane.setRowHeaderView(tln);
        return result;
    }
}
