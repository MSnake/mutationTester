package ru.mai.diplom.tester.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GuiRunner implements CommandLineRunner {

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

}
