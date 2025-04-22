package server;

import java.io.*;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import java.nio.charset.StandardCharsets;

public class Main {
    private static int port = 2000;
    public static void main(String[] args) {
        String fileName = System.getenv("FILEPATH");
        if((fileName == null) || fileName.isEmpty()) {
            System.out.println("Переменная окружения FILEPATH не задана. Пожалуйста, запишите в FILEPATH путь к файлу для сохранения коллекции.");
            return;
        }
        
        System.out.println(fileName);
        
        VehicleCollection vehicles = new VehicleCollection(fileName);
        String tmpFileName = vehicles.getTmpFileName();
        Scanner scanner = new Scanner(System.in);

        Logger logger = Logger.getLogger("server");
        try {
            logger.setUseParentHandlers(false); // Отключает дефолтное логирование в консоль
            FileHandler fh = new FileHandler("log.txt", true); //true обеспечивает добавление к файлу, а не затирание файла
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
            fh.setEncoding(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            System.out.println("Не удалось настроить запись лога в файл.");
        }

        boolean isLoaded = false;

        // Проверка состояния временного файла
        File f = new File(tmpFileName);
        if (f.exists() && !f.isDirectory()) {
            System.out.println("У вас есть несохранённые изменения. Хотите ли вы их загрузить в коллекцию? (Y/N)");
            
            try {
                String command = scanner.nextLine();    
                if((command.equals("Y")) || (command.equals("y"))) {
                    if(isLoaded = vehicles.load_tmp()) {
                        System.out.println("Несохранённые изменения загружены. ");
                    }
                }
            } catch (Exception e) {
                System.out.println("Ответ не распознан.");
                scanner.close();
                return;
            }
        }

        // Грузимся из основного файла
        if (!isLoaded) {
            File f1 = new File(fileName);
            if (f1.exists() && !f1.isDirectory()) {           
                isLoaded = vehicles.load();
                if (!isLoaded) {
                    System.out.println("Коллекцию не удалось загрузить из файла");
                    scanner.close();
                    return;
                }
            }
        }

        CommandHandler handler = new CommandHandler(vehicles);

        try {
            System.out.println("Запускаю сервер.");
            Server server = new Server(port, handler, logger);
            server.run();
        } catch (Exception e) {
            System.out.println("Аварийное завершение работы сервера : " + e);
        }

        File tmpFile = new File(tmpFileName);
        if(tmpFile.exists() && !tmpFile.isDirectory()) {
            if (!tmpFile.delete()) {
                System.out.println("Не удалось удалить временный файл " + tmpFileName);
            }
        }

        scanner.close();
    }
}