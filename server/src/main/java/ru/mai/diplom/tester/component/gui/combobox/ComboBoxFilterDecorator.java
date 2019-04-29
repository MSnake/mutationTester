package ru.mai.diplom.tester.component.gui.combobox;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mai.diplom.tester.db.model.TestResultData;
import ru.mai.diplom.tester.service.TestResultDataService;
import ru.mai.diplom.tester.utils.StaticApplicationContext;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class ComboBoxFilterDecorator<T> {
    private JComboBox<TestResultData> comboBox;
    private BiPredicate<TestResultData, String> userFilter;
    private Function<TestResultData, String> comboDisplayTextMapper;
    java.util.List<TestResultData> originalItems;
    private Object selectedItem;
    private FilterEditor filterEditor;

    public ComboBoxFilterDecorator(JComboBox<TestResultData> comboBox,
                                   BiPredicate<TestResultData, String> userFilter,
                                   Function<TestResultData, String> comboDisplayTextMapper) {
        this.comboBox = comboBox;
        this.userFilter = userFilter;
        this.comboDisplayTextMapper = comboDisplayTextMapper;
    }

    public static <T> ComboBoxFilterDecorator<T> decorate(JComboBox<T> comboBox,
                                                          Function<T, String> comboDisplayTextMapper,
                                                          BiPredicate<T, String> userFilter) {
        log.info("CREATE ComboBoxFilterDecorator");
        ComboBoxFilterDecorator decorator =
                new ComboBoxFilterDecorator(comboBox, userFilter, comboDisplayTextMapper);
        decorator.init();
        return decorator;
    }

    private void init() {
        log.info("init ComboBoxFilterDecorator");
        prepareComboFiltering();
        initComboPopupListener();
        initComboKeyListener();
    }

    private void prepareComboFiltering() {
        log.info("prepareComboFiltering ComboBoxFilterDecorator");
        DefaultComboBoxModel<TestResultData> model = (DefaultComboBoxModel<TestResultData>) comboBox.getModel();
        this.originalItems = new ArrayList<>();
        for (int i = 0; i < model.getSize(); i++) {
            this.originalItems.add(model.getElementAt(i));
        }


        filterEditor = new FilterEditor(comboDisplayTextMapper, new Consumer<Boolean>() {
            //editing mode (commit/cancel) change listener
            @Override
            public void accept(Boolean aBoolean) {
                log.info("ComboBoxFilterDecorator->prepareComboFiltering-> filterEditor accpet");
                if (aBoolean) {//commit
                    log.info("ComboBoxFilterDecorator->prepareComboFiltering-> filterEditor accpet - > commit");
                    selectedItem = comboBox.getSelectedItem();
                } else {//rollback to the last one
                    log.info("ComboBoxFilterDecorator->prepareComboFiltering-> filterEditor accpet - > rollback");
                    TestResultData data = new TestResultData();
                    //data.setTestName(comboBox.getEditor().getItem().toString());
                    comboBox.setSelectedItem(data);
                    filterEditor.endSearch();
                    filterEditor.setItem(data);
//                    comboBox.setSelectedItem(selectedItem);
//                    filterEditor.setItem(selectedItem);
                }
            }
        });

        JLabel filterLabel = filterEditor.getFilterLabel();
        filterLabel.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                log.info("ComboBoxFilterDecorator->prepareComboFiltering-> filterLabel FocusListener - > focusGained");
                filterLabel.setBorder(BorderFactory.createLoweredBevelBorder());
            }

            @Override
            public void focusLost(FocusEvent e) {
                log.info("ComboBoxFilterDecorator->prepareComboFiltering-> filterLabel FocusListener - > focusLost");
                //filterLabel.setBorder(UIManager.getBorder("TextField.border"));
                //resetFilterComponent();
            }
        });
        log.info("ComboBoxFilterDecorator->prepareComboFiltering-> end");
        comboBox.setEditor(filterEditor);
        comboBox.setEditable(true);
    }

    private void initComboKeyListener() {
        log.info("ComboBoxFilterDecorator->initComboKeyListener");
        filterEditor.getFilterLabel().addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        char keyChar = e.getKeyChar();
                        if (!Character.isDefined(keyChar)) {
                            return;
                        }
                        int keyCode = e.getKeyCode();
                        switch (keyCode) {
                            case KeyEvent.VK_DELETE:
                                log.info("ComboBoxFilterDecorator->initComboKeyListener-> KeyEvent - VK_DELETE");
                                return;
                            case KeyEvent.VK_ENTER:
                                log.info("ComboBoxFilterDecorator->initComboKeyListener-> KeyEvent - VK_ENTER");
                                TestResultData data = new TestResultData();
                                //data.setTestName(comboBox.getEditor().getItem().toString());
                                originalItems.add(data);
                                comboBox.addItem(data);
                                //comboBox.setSelectedItem(data);
                                filterEditor.endSearch();
                                filterEditor.setItem(data);
                                //selectedItem = comboBox.getSelectedItem();
                                //resetFilterComponent();
                                return;
                            case KeyEvent.VK_ESCAPE:
                                log.info("ComboBoxFilterDecorator->initComboKeyListener-> KeyEvent - VK_ESCAPE");
                                //resetFilterComponent();
                                return;
                            case KeyEvent.VK_BACK_SPACE:
                                log.info("ComboBoxFilterDecorator->initComboKeyListener-> KeyEvent - VK_BACK_SPACE");
                                filterEditor.removeCharAtEnd();
                                break;
                            default:
                                log.info("ComboBoxFilterDecorator->initComboKeyListener-> KeyEvent - default");
                                filterEditor.addChar(keyChar);
                        }
                        if (!comboBox.isPopupVisible()) {
                            log.info("ComboBoxFilterDecorator->initComboKeyListener-> !comboBox.isPopupVisible()");
                            comboBox.showPopup();
                        }
                        if (filterEditor.isEditing() && filterEditor.getText().length() > 0) {
                            log.info("ComboBoxFilterDecorator->initComboKeyListener-> applyFilter");
                            applyFilter();
                        } else {
                            log.info("ComboBoxFilterDecorator->initComboKeyListener-> hidePopup");
                            comboBox.hidePopup();
                            //resetFilterComponent();
                        }
                    }
                }
        );
    }

    public Supplier<String> getFilterTextSupplier() {
        log.info("ComboBoxFilterDecorator->getFilterTextSupplier");
        return () -> {
            if (filterEditor.isEditing()) {
                log.info("ComboBoxFilterDecorator->getFilterTextSupplier->filterEditor is editing");
                return filterEditor.getFilterLabel().getText();
            }
            log.info("ComboBoxFilterDecorator->getFilterTextSupplier->filterEditor is no editing");
            return "";
        };
    }

    private void initComboPopupListener() {
        log.info("ComboBoxFilterDecorator-> initComboPopupListener");
        comboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                log.info("ComboBoxFilterDecorator-> initComboPopupListener -> popupMenuWillBecomeInvisible");
                resetFilterComponent();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                log.info("ComboBoxFilterDecorator-> initComboPopupListener -> popupMenuCanceled");
                resetFilterComponent();
            }
        });
    }

    private void resetFilterComponent() {
        log.info("ComboBoxFilterDecorator-> resetFilterComponent");
        if (!filterEditor.isEditing()) {
            log.info("ComboBoxFilterDecorator-> resetFilterComponent -> filterEditor not editable");
            return;
        }
        //restore original order
        log.info("ComboBoxFilterDecorator-> resetFilterComponent -> filterEditor restore original order");
       // DefaultComboBoxModel<T> model = (DefaultComboBoxModel<T>) comboBox.getModel();
       // model.removeAllElements();
       // for (T item : originalItems) {
       //     model.addElement(item);
        //}
        log.info("ComboBoxFilterDecorator-> resetFilterComponent -> filterEditor reset");
        //filterEditor.reset();
    }

    private void updateFilterComponent() {
        if (!filterEditor.isEditing()) {
            return;
        }
        //restore original order
        DefaultComboBoxModel<TestResultData> model = (DefaultComboBoxModel<TestResultData>) comboBox.getModel();
        TestResultData data = new TestResultData();
        //data.setTestName(comboBox.getEditor().getItem().toString());
        model.removeAllElements();
        for (TestResultData item : originalItems) {
            model.addElement(item);
        }
//        filterEditor.reset();
    }

    private void applyFilter() {
        log.info("ComboBoxFilterDecorator -> applyFilter");
        DefaultComboBoxModel<TestResultData> model = (DefaultComboBoxModel<TestResultData>) comboBox.getModel();
        model.removeAllElements();
        java.util.List<TestResultData> filteredItems = new ArrayList<>();
        //add matched items at top
        for (TestResultData item : originalItems) {
            if (userFilter.test(item, filterEditor.getFilterLabel().getText())) {
                model.addElement(item);
            } else {
                filteredItems.add(item);
            }
        }

        //red color when no match
        filterEditor.getFilterLabel()
                    .setForeground(model.getSize() == 0 ?
                            Color.green : UIManager.getColor("Label.foreground"));
        //add unmatched items
        filteredItems.forEach(model::addElement);
    }
}