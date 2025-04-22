package client;

import java.util.Scanner;

public class Main {
    private static int port = 2000;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Запускаю клиент.");
            String username;
            String password;

            while(true) {
                System.out.println("Введите имя пользователя : ");
                try {
                    username = scanner.nextLine().trim();
                    if(!username.isEmpty()) break;
                } catch (Exception e) {
                    System.out.println("Неверное имя пользователя.");
                } 
            }

            while(true) {
                System.out.println("Введите пароль : ");
                try {
                    password = scanner.nextLine().trim();
                    if(!password.isEmpty()) break;
                } catch (Exception e) {
                    System.out.println("Неверный пароль.");
                } 
            }

            Client client = new Client(port, "localhost");
            CommandReader reader = new CommandReader(scanner, client, username, password);
            reader.run();
        } catch (Exception e) {
            System.out.println("Аварийное завершение работы клиента : " + e);
        }
        
        scanner.close();
    }
}