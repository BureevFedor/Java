package admin;

import java.util.Scanner;

public class Main {
    private static int port = 2000;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Запускаю админ.");
            Client client = new Client(port, "localhost");
            CommandReader reader = new CommandReader(scanner, client);
            reader.run();
        } catch (Exception e) {
            System.out.println("Аварийное завершение работы админа : " + e);
        }
        
        scanner.close();
    }
}