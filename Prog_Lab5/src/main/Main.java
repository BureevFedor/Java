package main;

import java.io.*;
import java.util.Scanner;

import managers.CommandReader;
import managers.VehicleCollection;

/**
 * Класс Main
 * @author Буреев Фёдор
 * @version 1.0
 */
public class Main {

    /**
     * Запускает программу
     * @param args Параметры командной строки (не используются)
     * @version 1.0
     */  
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

        CommandReader reader = new CommandReader(vehicles, scanner);
        reader.run();

        File tmpFile = new File(tmpFileName);
        if(tmpFile.exists() && !tmpFile.isDirectory()) {
            if (!tmpFile.delete()) {
                System.out.println("Не удалось удалить временный файл " + tmpFileName);
            }
        }

        scanner.close();
    }
}