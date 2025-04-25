package client.graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

import client.Client;
import client.CommandHandler;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;

public class CollectionWindow extends JFrame{
    private CommandHandler handler;
    private ObjectViewPanel objectView;
    private TableViewPanel tableView;
    private ButtonPanel buttonPanel;
    
    public CollectionWindow(VehiclesTableModel model, Client client, CommandHandler handler) {
        this.handler = handler;

        //setTitle("VEHICLE COLLECTION (User: " + client.getUsername() + ")");
        setTitle(Resources.getString(Resources.WINDOW_TITLE) + 
            " (" + Resources.getString(Resources.WINDOW_TITLE_USER) + ": " + client.getUsername() + ")");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {    
            public void windowClosing(WindowEvent evt) {
                //System.out.println("Закрываем окно");
                handler.doStop();
                objectView.stop();
                dispose();
                System.exit(0);
            } 
        }); 

        JComboBox<String> langBox = new JComboBox<String>(Resources.languages);
        langBox.setEditable(false);
        langBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Resources.setLanguage(langBox.getSelectedIndex());
                setTitle(Resources.getString(Resources.WINDOW_TITLE) + 
                    " (" + Resources.getString(Resources.WINDOW_TITLE_USER) + ": " + client.getUsername() + ")");
                tableView.updateLocale();
                buttonPanel.updateLocale();
            }
        });

        JPanel p = new JPanel();
        p.setBackground(Color.LIGHT_GRAY);

        buttonPanel = new ButtonPanel(model, handler);
        tableView = new TableViewPanel(model, handler, p, this);
        objectView = new ObjectViewPanel(model, handler);
  
        getContentPane().add(buttonPanel);
        getContentPane().add(tableView);
        getContentPane().add(objectView);
        p.add(langBox);
        getContentPane().add(p);
    }

    public void run() {
        handler.doStart();       
        //pack();
        setVisible(true);
    }
}