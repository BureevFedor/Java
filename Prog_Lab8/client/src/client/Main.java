package client;

import javax.swing.JDialog;
import javax.swing.JFrame;

import client.graphics.CollectionWindow;
import client.graphics.LoginWindow;
import client.graphics.VehiclesTableModel;

import client.graphics.Resources;

public class Main {
    private static int port = 2000;
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        Resources.init();

        LoginWindow loginWindow = new LoginWindow();
        loginWindow.run();

        String username = loginWindow.getUsername();
        String password = loginWindow.getPassword();

        VehiclesTableModel model = new VehiclesTableModel();

        try {
            System.out.println("Запускаю клиент.");

            Client client = new Client(port, "localhost", username, password);
            CommandHandler handler = new CommandHandler(client, model);

            if(!handler.doAuthorize()) {
                System.out.println("Ошибка авторизации");
                return;
            }

            CollectionWindow collectionWindow = new CollectionWindow(model, client, handler);
            collectionWindow.run();
        } catch (Exception e) {
            System.out.println("Аварийное завершение работы клиента : " + e);
        }
    }
   
}