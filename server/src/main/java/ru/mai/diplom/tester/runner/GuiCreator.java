package ru.mai.diplom.tester.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.component.CommonGuiComponent;
import ru.mai.diplom.tester.component.actionlistener.SaveTestButtonListener;
import ru.mai.diplom.tester.component.actionlistener.StartTestButtonListener;
import ru.mai.diplom.tester.component.gui.*;

import javax.swing.*;
import java.awt.*;

@Component
public class GuiCreator {

    @Autowired
    private SourceCodePanelBuilder sourceCodePanelBuilder;

    @Autowired
    private TestCodePanelBuilder testCodePanelBuilder;

    @Autowired
    private MutationOptionPanelBuilder mutationOptionPanelBuilder;

    @Autowired
    private StartTestPanelBuilder startTestPanelBuilder;

    @Autowired
    private StartTestButtonListener startTestButtonListener;

    @Autowired
    private SaveTestButtonListener saveTestButtonListener;


    public void build() {
        JFrame mainFrame = createMainFrame();
        JPanel mainPanel = createMainPanel();
        JPanel sourceCodePanel = sourceCodePanelBuilder.build();
        JPanel testCodePanelWithOptions = createTestCodePanelWithOptions();

        mainPanel.add(sourceCodePanel);
        mainPanel.add(testCodePanelWithOptions);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);

        CommonGuiComponent.startTestButton.addActionListener(startTestButtonListener);
        CommonGuiComponent.saveTestButton.addActionListener(saveTestButtonListener);
    }

    private JFrame createMainFrame() {
        JFrame result = new JFrame("Мутационное тестирование");
        result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //result.setSize((int) (CommonGuiComponent.screenWidth / 1.5d), CommonGuiComponent.screenHeight - 100);
        result.setMinimumSize(new Dimension(CommonGuiComponent.appDefaultWidth, CommonGuiComponent.appDefaultHeight));
        result.setPreferredSize(new Dimension(CommonGuiComponent.appDefaultWidth, CommonGuiComponent.appDefaultHeight));
        result.setLocationRelativeTo(null);
        result.setResizable(false);
        CommonGuiComponent.sourceCodeEditorPane.setText("package ru.mai.diplom.tester.utils;\n" +
                "\n" +
                "/**\n" +
                " * Created by Alex on 17.02.2019.\n" +
                " */\n" +
                "public class Multiply {\n" +
                "\n" +
                "    public Long multiply(Long a, Long b) {\n" +
                "        Long result = null;\n" +
                "        if (a < b) result = a * b;\n" +
                "        if (a > b) result = a + b;\n" +
                "        return result;\n" +
                "    }\n" +
                "}");
        CommonGuiComponent.testCodeEditorPane.setText("package ru.mai.diplom.tester.utils;\n" +
                "\n" +
                "import org.junit.Assert;\n" +
                "import org.junit.Test;\n" +
                "\n" +
                "/**\n" +
                " * Created by Alex on 17.02.2019.\n" +
                " */\n" +
                "public class MultiplyTest {\n" +
                "    @Test\n" +
                "    public void multiply() throws Exception {\n" +
                "        Multiply multiply = new Multiply();\n" +
                "        Long result = multiply.multiply(1l, 2l);\n" +
                "        Assert.assertEquals(2l, result.longValue());\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "}");
        return result;
    }

    private JPanel createMainPanel() {
        JPanel result = new JPanel(new GridLayout(1, 2, 1, 1));
        result.setVisible(true);
        return result;
    }

    private JPanel createTestCodePanelWithOptions() {
        JPanel result = new JPanel(new BorderLayout());
        JPanel testCodePanel = testCodePanelBuilder.build();
//        testCodePanel.setPreferredSize(new Dimension(1024, 600));
        result.add(testCodePanel, BorderLayout.NORTH);

        JPanel mutationOptionPanel = mutationOptionPanelBuilder.build();
//        mutationOptionPanel.setPreferredSize(new Dimension(1024, 768));
        result.add(mutationOptionPanel, BorderLayout.CENTER);

        JPanel startTestPanel = startTestPanelBuilder.build();
        result.add(startTestPanel, BorderLayout.SOUTH);

        return result;
    }

}
