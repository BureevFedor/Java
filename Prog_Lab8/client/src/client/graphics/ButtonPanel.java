package client.graphics;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;  
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;

import java.lang.Exception;

import client.CommandHandler;

public class ButtonPanel extends JPanel {

    static private int FONT_SIZE = 20;
    static private String FONT_NAME = "Arial";

    JButton helpButton;
    JButton infoButton;
    JButton addButton;
    JButton updateButton;
    JButton removeByIdButton;
    JButton removeAtIndexButton;
    JButton removeLowerButton;
    JButton clearButton;
    JButton executeScriptButton;
    JButton sortButton;
    JButton filterStartsWithNameButton;
    JButton printUniqueFuelTypeButton;
    JButton printFieldDescendingTypeButton;

    public ButtonPanel(VehiclesTableModel model, CommandHandler handler) {
        setBackground(Color.lightGray);
        setBounds(25,25,300,850);
        setLayout(new GridLayout(13, 1, 0, 0));
        //setBorder(BorderFactory.createLineBorder(Color.black));

        helpButton = new JButton(Resources.getString(Resources.BUTTON_HELP));
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handler.doHelp();
            }
        });
        helpButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        infoButton = new JButton(Resources.getString(Resources.BUTTON_INFO));
        infoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handler.doInfo();
            }
        });
        infoButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        addButton = new JButton(Resources.getString(Resources.BUTTON_ADD));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VehicleWindow w = new VehicleWindow();
                if (w.run()) {
                    handler.doAdd(w.getVehicleItem().createSerializable());
                }
            }
        });
        addButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        updateButton = new JButton(Resources.getString(Resources.BUTTON_UPDATE));
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = JOptionPane.showInputDialog(Resources.getString(Resources.TEXT_ENTER_ID_UPDATE));
                try {
                    if(str != null) {
                        int id = Integer.parseInt(str);
                        VehicleItem item = model.getObjById(id);
                        if(item == null) {
                            JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ID_IS_NOT_FOUND),
                                Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                        } else {
                            VehicleWindow w = new VehicleWindow(item, true);
                            if (w.run()) {
                                handler.doUpdate(id, w.getVehicleItem().createSerializable());
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_INVALID_ID),
                        Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        updateButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        removeByIdButton = new JButton(Resources.getString(Resources.BUTTON_REMOVE_BY_ID));
        removeByIdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = JOptionPane.showInputDialog(Resources.getString(Resources.TEXT_ENTER_ID_REMOVE));
                try {
                    if(str != null) { 
                        int id = Integer.parseInt(str);
                        handler.doRemoveItemById(id);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_INVALID_ID),
                        Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        removeByIdButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        removeAtIndexButton = new JButton(Resources.getString(Resources.BUTTON_REMOVE_BY_INDEX));
        removeAtIndexButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = JOptionPane.showInputDialog(Resources.getString(Resources.TEXT_ENTER_INDEX_REMOVE));
                try {
                    if(str != null) {
                        int id = Integer.parseInt(str);
                        handler.doRemoveItemByIndex(id);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_INVALID_INDEX),
                        Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        removeAtIndexButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        removeLowerButton = new JButton(Resources.getString(Resources.BUTTON_REMOVE_LOWER));
        removeLowerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = JOptionPane.showInputDialog(Resources.getString(Resources.TEXT_ENTER_ID_REMOVE_LOWER));
                try {
                    if(str != null) {
                        int id = Integer.parseInt(str);
                        VehicleItem item = model.getObjById(id);
                        if(item == null) {
                            JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ID_IS_NOT_FOUND),
                                Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                        } else {
                            handler.doRemoveLowerItems(item.createSerializable());
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_INVALID_ID),
                        Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        removeLowerButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        clearButton = new JButton(Resources.getString(Resources.BUTTON_CLEAR));
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handler.doClear();
            }
        });
        clearButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        executeScriptButton = new JButton(Resources.getString(Resources.BUTTON_EXECUTE_SCRIPT));
        executeScriptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = JOptionPane.showInputDialog(Resources.getString(Resources.TEXT_ENTER_FILE_NAME));
                if(str != null) {
                    handler.doExecuteScript(str);
                }
            }
        });
        executeScriptButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        sortButton = new JButton(Resources.getString(Resources.BUTTON_SORT));
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //new TestTable();
            }
        });
        sortButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        filterStartsWithNameButton = new JButton(Resources.getString(Resources.BUTTON_FILTER_STARTS_WITH_NAME));
        filterStartsWithNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    //dispose();
            }
        });
        filterStartsWithNameButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        printUniqueFuelTypeButton = new JButton(Resources.getString(Resources.BUTTON_PRINT_UNIQUE_FUEL_TYPES));
        printUniqueFuelTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    handler.doPrintUniqueFuelType();
            }
        });
        printUniqueFuelTypeButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        printFieldDescendingTypeButton = new JButton(Resources.getString(Resources.BUTTON_PRINT_FIELD_DESCENDING_TYPE));
        printFieldDescendingTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    //dispose();
            }
        });
        printFieldDescendingTypeButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

        add(helpButton);
        add(infoButton);
        add(addButton);
        add(updateButton);
        add(removeByIdButton);
        add(removeAtIndexButton);
        add(removeLowerButton);
        add(clearButton);
        add(executeScriptButton);
        add(sortButton);
        add(filterStartsWithNameButton);
        add(printUniqueFuelTypeButton);
        add(printFieldDescendingTypeButton);
    }

    public void updateLocale() {
        helpButton.setText(Resources.getString(Resources.BUTTON_HELP));
        infoButton.setText(Resources.getString(Resources.BUTTON_INFO));
        addButton.setText(Resources.getString(Resources.BUTTON_ADD));
        updateButton.setText(Resources.getString(Resources.BUTTON_UPDATE));
        removeByIdButton.setText(Resources.getString(Resources.BUTTON_REMOVE_BY_ID));
        removeAtIndexButton.setText(Resources.getString(Resources.BUTTON_REMOVE_BY_INDEX));
        removeLowerButton.setText(Resources.getString(Resources.BUTTON_REMOVE_LOWER));
        clearButton.setText(Resources.getString(Resources.BUTTON_CLEAR));
        executeScriptButton.setText(Resources.getString(Resources.BUTTON_EXECUTE_SCRIPT));
        sortButton.setText(Resources.getString(Resources.BUTTON_SORT));
        filterStartsWithNameButton.setText(Resources.getString(Resources.BUTTON_FILTER_STARTS_WITH_NAME));
        printUniqueFuelTypeButton.setText(Resources.getString(Resources.BUTTON_PRINT_UNIQUE_FUEL_TYPES));
        printFieldDescendingTypeButton.setText(Resources.getString(Resources.BUTTON_PRINT_FIELD_DESCENDING_TYPE));
    }
}
