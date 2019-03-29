package ru.mai.diplom.tester.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.component.CommonGuiComponent;
import ru.mai.diplom.tester.component.gui.GuiPanelBuilderInterface;

import javax.swing.*;
import java.awt.*;

@Component
public class GuiCreator {

    @Autowired
    private GuiPanelBuilderInterface sourceCodePanelBuilder;

    @Autowired
    private GuiPanelBuilderInterface testCodePanelBuilder;

    @Autowired
    private GuiPanelBuilderInterface mutationOptionPanelBuilder;

    @Autowired
    private GuiPanelBuilderInterface startTestPanelBuilder;

    public void build() {
        JFrame mainFrame = createMainFrame();
        JPanel mainPanel = createMainPanel();
        JPanel sourceCodePanel = sourceCodePanelBuilder.build();
        JPanel testCodePanelWithOptions = createTestCodePanelWithOptions();

        mainPanel.add(sourceCodePanel);
        mainPanel.add(testCodePanelWithOptions);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }

    private JFrame createMainFrame() {
        JFrame result = new JFrame("Мутационное тестирование");
        result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        result.setSize((int) (CommonGuiComponent.screenWidth / 1.5d), CommonGuiComponent.screenHeight - 100);
        result.setMinimumSize(new Dimension(800, 600));
        return result;
    }

    private JPanel createMainPanel() {
        JPanel result = new JPanel(new GridLayout(1, 2, 3, 3));
        result.setVisible(true);
        return result;
    }

    private JPanel createTestCodePanelWithOptions() {
        JPanel result = new JPanel(new BorderLayout());
        JPanel testCodePanel = testCodePanelBuilder.build();
        testCodePanel.setPreferredSize(new Dimension(1024, 600));
        result.add(testCodePanel, BorderLayout.NORTH);

        JPanel mutationOptionPanel = mutationOptionPanelBuilder.build();
        mutationOptionPanel.setPreferredSize(new Dimension(1024, 768));
        result.add(mutationOptionPanel, BorderLayout.CENTER);

        JPanel startTestPanel = startTestPanelBuilder.build();
        result.add(startTestPanel, BorderLayout.SOUTH);

        return result;
    }

}
