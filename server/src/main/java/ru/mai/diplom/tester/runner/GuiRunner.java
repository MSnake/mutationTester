package ru.mai.diplom.tester.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class GuiRunner implements CommandLineRunner {

    private Integer screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

    private Integer screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    @Autowired
    GuiCreator guiCreator;

    @Override
    public void run(String... args) {
        guiCreator.build();
//        JFrame mainFrame = createMainFrame();
//        GUIBuilder gui = new GUIBuilder();
//        mainFrame.setContentPane(gui.getContentPanel());
//        mainFrame.setVisible(true);
//        guiCreatorBuilder.build();
    }

    public JFrame createMainFrame() {
        JFrame result = new JFrame("Мутационное тестирование");
        result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        result.setSize((int) (screenWidth / 1.5d), screenHeight - 100);
        result.setLocationRelativeTo(null);
        return result;
    }
}
