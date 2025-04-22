package client;

import java.util.Scanner;

import data.FuelType;
import data.VehicleSerializable;
import data.VehicleType;

import java.util.Arrays;
import java.util.ArrayList;

import java.io.File;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import commands.*;

import communication.Response;
import communication.Request;
import communication.RequestType;

/**
 * Класс CommandReader
 * @author Буреев Фёдор
 * @version 1.0
 */
public class CommandReader {

    /** Поле список запущенных скриптов */    
    private ArrayList<String> scriptnames;

    /** Поле сканнера */ 
    private Scanner scanner;

    private Client client;

    public CommandReader(Scanner scanner, Client client) {
        scriptnames = new ArrayList<String>();
        this.scanner = scanner;
        this.client = client;
    }
   
   private void communicate(Request request) {
        if(request != null) {    
            try {
                Response response = client.doCommand(request);
                if(response != null) {
                    System.out.println("Получили ответ (" + response.getCode() + ") : \n" + response.getText());
                }
            } catch (Exception e) {
                System.out.println(e);
            } 
        }
   }

    public void run()
    {
        String command = "";
    
        while(true) {
            System.out.println("Введите команду управления коллекцией:");
            
            try {
                command = scanner.nextLine();
                if(command != null) {
                    command = command.trim();
                }
                
                if (runCommand(command)) {
                    break;
                }  
            } catch (Exception e) {
                System.out.println("Неправильная команда");
            }    
        }
    }

    /** Читает и исполняет команды управления коллекцией из указанного файла
     * @param fileName имя файла
     * @return true, если была выполнена команда exit, false, если нет
     */
    private boolean execute (String fileName) {

        boolean needExit = false;

        if(scriptnames.contains(fileName)) {
            System.out.println("Рекурсивный вызов скриптов запрещён");
            return needExit;
        }

        File f = new File(fileName);
        if (f.exists() && !f.isDirectory()) {
            try {
                scriptnames.add(fileName);
                System.out.println("scriptnames : " + scriptnames.toString());

                InputStream fin = new FileInputStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(!line.isEmpty()) {
                        System.out.println("########## ВЫПОЛНЯЮ КОМАНДУ : " + line);
                        needExit = runCommand(line);
                        if(needExit) {
                            break;
                        }
                    }
                }

                reader.close();
            } catch (Exception e) {
                System.out.println(e);
            }

            scriptnames.remove(fileName);
            System.out.println("scriptnames : " + scriptnames.toString());

        } else {
            System.out.println("Неправильное имя файла: " + fileName);
        }

        return needExit;
    }

    /** Выводит список команд управления коллекцией */    
    private void printHelp() {
        System.out.println("help : вывести справку по доступным командам");
        System.out.println("info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        System.out.println("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.println("add {element} : добавить новый элемент в коллекцию");
        System.out.println("update id {element} : обновить значение элемента коллекции, id которого равен заданному");
        System.out.println("remove_by_id id : удалить элемент из коллекции по его id");
        System.out.println("clear : очистить коллекцию");
        System.out.println("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.println("exit : завершить программу");
        System.out.println("remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)");
        System.out.println("remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный");
        System.out.println("sort : отсортировать коллекцию в естественном порядке");
        System.out.println("filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки");
        System.out.println("print_unique_fuel_type : вывести уникальные значения поля fuelType всех элементов в коллекции");
        System.out.println("print_field_descending_type : вывести значения поля type всех элементов в порядке убывания");
    }
    
    public boolean runCommand (String command)
    {
        boolean needExit = false;

        if(command == null) {
            return needExit;
        }

        if(command.isEmpty()) {
            return needExit;
        }
        
        String[] args;
        args = command.split(" ");

        if(args.length == 0) {
            return needExit;
        }
        
        switch (args[0]) {   
            
            case "exit":
                needExit = true;
                break;

            case "help":
                printHelp();
                break;

            case "info":
                communicate(new Request(RequestType.INFO, new InfoCommand()));
                break;

            case "show":
                communicate(new Request(RequestType.SHOW, new ShowCommand()));
                break;

            case "add":
                if(args.length == 1) {
                    add_interactive();
                } else if(args.length == 2) {
                    try {
                        VehicleSerializable obj = VehicleSerializable.create(args[1]);
                        if(obj != null) {
                            communicate(new Request(RequestType.ADD, new AddCommand(obj)));
                        }
                    } catch (Exception e) {
                        System.out.println("Неправильный формат задания объекта : " + args[1]);
                    }
                } else {
                    System.out.println("Неверное число аргументов");
                }
                break;
            case "update":
                if(args.length == 3) {
                    try {
                        int id = Integer.parseInt(args[1]);
                        VehicleSerializable obj = VehicleSerializable.create(args[2]);
                        if(obj != null) {
                            communicate(new Request(RequestType.UPDATE, new UpdateCommand(id, obj)));    
                        }
                    } catch (Exception e) {
                        System.out.println("Неправильно введены аргументы : " + e);
                    }
                } else {
                    System.out.println("Неверное число аргументов.");
                }
                break;
            case "clear":
                communicate(new Request(RequestType.CLEAR, new ClearCommand()));    
                break;

            case "execute_script":
                if(args.length == 2) {
                    needExit = execute(args[1]);
                } else {
                    System.out.println("Неверное число аргументов.");
                }
                break;
            
            case "remove_by_id":
                if(args.length == 2) {
                    try {
                        int id = Integer.parseInt(args[1]);
                        communicate(new Request(RequestType.REMOVE_BY_ID, new RemoveByIdCommand(id)));   
                    } catch (Exception e) {
                        System.out.println("Неправильно введены аргументы : " + e);
                    }
                } else {
                    System.out.println("Неверное число аргументов.");
                }
                break;

            case "remove_at":
                if(args.length == 2) {
                    try {
                        int idx = Integer.parseInt(args[1]);
                        communicate(new Request(RequestType.REMOVE_AT, new RemoveAtIndexCommand(idx)));   
                    } catch (Exception e) {
                        System.out.println("Неправильно введены аргументы : " + e);
                    }
                } else {
                    System.out.println("Неверное число аргументов.");
                }
                break;

            case "remove_lower":
                if(args.length == 2) {
                    VehicleSerializable obj = VehicleSerializable.create(args[1]);
                    if(obj != null) {
                        communicate(new Request(RequestType.REMOVE_LOWER, new RemoveLowerCommand(obj)));  
                    }
                } else {
                    System.out.println("Неверное число аргументов.");
                }
                break;

            case "sort":
                communicate(new Request(RequestType.SORT, new SortCommand()));  
                break;
            
            case "filter_starts_with_name":
                if(args.length == 2) {
                    String str = args[1];
                    communicate(new Request(RequestType.FILTER_STARTS_WITH_NAME, new FilterStartsWithNameCommand(str)));  
                } else {
                    System.out.println("Неверное число аргументов.");
                }
                break;

            case "print_unique_fuel_type":
                communicate(new Request(RequestType.PRINT_UNIQUE_FUEL_TYPE, new PrintUniqueFuelTypeCommand()));  
                break;

            case "print_field_descending_type":
                communicate(new Request(RequestType.PRINT_FIELD_DESCENDING_TYPE, new PrintFieldDescendingTypeCommand()));  
                break;

            default:
                System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                break;
        }
        return needExit;
    }

    /** Исполняет команду добавления элемента в коллекцию в интерактивном режиме ввода параметром */
    private void add_interactive() {
        String name;
        Float x;
        Long y;
        int enginePower;
        long fuelConsumption;
        VehicleType type = null;
        FuelType fuelType = null;


        while(true) {
            System.out.println("Введите имя транспортного средства:");
            try {
                name = scanner.nextLine().trim();    
            } catch (Exception e) {
                name = "";
            }

            if(name.isEmpty()) {
                System.out.println("Ошибка: Имя не может быть пустым");
            } else {
                break;
            }
        }

        while(true) {
            System.out.println("Введите координату X объекта:");
            try {
                String str = scanner.nextLine().trim();
                x = Float.parseFloat(str);
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: Координата X должна быть плавающим числом");
            }  
        }

        while(true) {
            System.out.println("Введите координату Y объекта:");
            try {
                String str = scanner.nextLine().trim();
                y = Long.parseLong(str);
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: Координата Y должна быть длинным целым числом");
            }  
        }

        while(true) {
            System.out.println("Введите мощь двигателя:");
            try {
                String str = scanner.nextLine().trim();
                enginePower = Integer.parseInt(str);
                if(enginePower <= 0) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: Мощь двигателя должна быть целым числом больше нуля");
            }  
        }
        
        while(true) {
            System.out.println("Введите уровень потребления топлива:");
            try {
                String str = scanner.nextLine().trim();
                fuelConsumption = Long.parseLong(str);
                if(fuelConsumption <= 0) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: Уровень потребления топлива должен быть числом больше нуля");
            }  
        }

        while(true) {
            System.out.println("Введите тип топлива ( " + Arrays.toString(FuelType.values()) + " ): ");
            try {
                String str = scanner.nextLine().trim();
                if(!str.isEmpty()) {
                    fuelType = FuelType.valueOf(str);
                }
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: Неверный тип топлива");
            }
        }

        while(true) {
            System.out.println("Введите тип транспортного средства ( " + Arrays.toString(VehicleType.values()) + " ): ");
            try {
                String str = scanner.nextLine().trim();
                if(!str.isEmpty()) {
                    type = VehicleType.valueOf(str);
                }
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: Неверный тип транспортного средства");
            }
        }

        communicate(new Request(RequestType.ADD, new AddCommand(new VehicleSerializable(name, x, y, enginePower, fuelConsumption, type, fuelType))));
    }
}