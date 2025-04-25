package test;

import java.lang.Thread;

public class Main {
    private static int port = 2000;
    private static String user1 = "a";
    private static String password1 = "b";
    private static String user2 = "a";
    private static String password2 = "b";
    
    private static String script1 = "test1.txt";
    private static String script2 = "test2.txt";

    public static void main(String[] args) {
        try {
            System.out.println("Запускаю клиент.");

            Thread thread1 = new Thread(new ClientTask(user1, password1, script1, port));
            Thread thread2 = new Thread(new ClientTask(user2, password2, script2, port));

            thread1.start();
            thread2.start();

            thread1.join();
            thread1.join();
        } catch (Exception e) {
            System.out.println("Аварийное завершение работы : " + e);
        }
    }
}