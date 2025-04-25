package client.graphics;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;

import java.awt.Container;  
import java.awt.Component;


public class LoginWindow extends JFrame{

    private String username;
    private String password;
    private JDialog dialog;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginWindow() {
        username = "";
        password = "";

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dialog = new JDialog(this, Resources.getString(Resources.AUTH_TITLE), true);
        dialog.setResizable(false);
        dialog.addWindowListener(new WindowAdapter() {    
            public void windowClosing(WindowEvent evt) {
                //System.out.println("Закрываем окно");
                dispose();
                System.exit(0);
            } 
        }); 
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300,300);
        dialog.setLocationRelativeTo(null);
        
        Container contentPane = dialog.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        JLabel label1 = new JLabel(Resources.getString(Resources.LABEL_LOGIN) + ":");
        JTextField tf1 = new JTextField(15);
        tf1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username = tf1.getText();
                Component c = (Component)e.getSource();
                c.transferFocus();
            }
        });

        contentPane.add(label1);
        contentPane.add(tf1);
        
        layout.putConstraint(SpringLayout.WEST, label1, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, label1, 5, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, tf1, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, tf1, 5, SpringLayout.NORTH, contentPane);

        JLabel label2 = new JLabel(Resources.getString(Resources.LABEL_PASSWORD) + ":");
        JTextField tf2 = new JTextField(15);
        tf2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                password = tf2.getText();
                Component c = (Component)e.getSource();
                c.transferFocus();
            }
        });

        contentPane.add(label2);
        contentPane.add(tf2);
        
        layout.putConstraint(SpringLayout.WEST, label2, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, label2, 5, SpringLayout.SOUTH, label1);

        layout.putConstraint(SpringLayout.WEST, tf2, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, tf2, 5, SpringLayout.SOUTH, tf1);

        JButton button = new JButton(Resources.getString(Resources.BUTTON_ENTER));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                username = tf1.getText();
                password = tf2.getText();

                if(username.isEmpty() || password.isEmpty()) {
                    System.out.println(Resources.getString(Resources.TEXT_ENTER_LOGIN_AND_PASSWORD));
                } else {
                    dialog.dispose();
                    dispose();
                }
            }
        });
        button.addKeyListener( new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){

                    username = tf1.getText();
                    password = tf2.getText();
                    
                    if(username.isEmpty() || password.isEmpty()) {
                        System.out.println(Resources.getString(Resources.TEXT_ENTER_LOGIN_AND_PASSWORD));
                    } else {
                        dialog.dispose();
                        dispose();
                    }
                }
            }

            public void keyTyped(KeyEvent k) {}
            public void keyReleased(KeyEvent k) {}
        });

        contentPane.add(button);

        layout.putConstraint(SpringLayout.WEST, button, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.SOUTH, tf2);

        // layout constraints
        layout.putConstraint(SpringLayout.EAST, contentPane, 50, SpringLayout.EAST, tf2);
        layout.putConstraint(SpringLayout.SOUTH, contentPane, 50, SpringLayout.SOUTH, tf2);
    }

    public void run() {
        dialog.pack();
        dialog.setVisible(true);
    }
}