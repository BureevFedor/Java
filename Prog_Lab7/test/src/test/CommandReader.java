package test;

import data.VehicleSerializable;

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

public class CommandReader {
    private ArrayList<String> scriptnames;

    private Client client;
    private String username;
    private String password;

    public CommandReader(Client client, String username, String password) {
        scriptnames = new ArrayList<String>();
        this.client = client;
        this.username = username;
        this.password = password;
    }
   
   private void communicate(Request request) {
        if(request != null) {    
            try {
                Response response = client.doCommand(request);
                if(response != null) {
                    System.out.println(Thread.currentThread().getName() + " : " + "Получили ответ (" + response.getCode() + ") : \n" + response.getText());
                }
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " : " + e);
            } 
        }
   }

    public void run(String filename)
    {
        execute(filename);
    }

    /** Читает и исполняет команды управления коллекцией из указанного файла
     * @param fileName имя файла
     * @return true, если была выполнена команда exit, false, если нет
     */
    private boolean execute (String fileName) {

        boolean needExit = false;

        if(scriptnames.contains(fileName)) {
            System.out.println(Thread.currentThread().getName() + " : " + "Рекурсивный вызов скриптов запрещён");
            return needExit;
        }

        File f = new File(fileName);
        if (f.exists() && !f.isDirectory()) {
            try {
                scriptnames.add(fileName);
                System.out.println(Thread.currentThread().getName() + " : " + "scriptnames : " + scriptnames.toString());

                InputStream fin = new FileInputStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(!line.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " : " + "########## ВЫПОЛНЯЮ КОМАНДУ : " + line);
                        needExit = runCommand(line);
                        if(needExit) {
                            break;
                        }
                    }

                    //Thread.sleep(1000);
                }

                reader.close();
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " : " + e);
            }

            scriptnames.remove(fileName);
            System.out.println(Thread.currentThread().getName() + " : " + "scriptnames : " + scriptnames.toString());

        } else {
            System.out.println(Thread.currentThread().getName() + " : " + "Неправильное имя файла: " + fileName);
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
                communicate(new Request(RequestType.INFO, new InfoCommand(), username, password));
                break;

            case "show":
                communicate(new Request(RequestType.SHOW, new ShowCommand(), username, password));
                break;

            case "add":
                if(args.length == 2) {
                    try {
                        VehicleSerializable obj = VehicleSerializable.create(args[1]);
                        if(obj != null) {
                            communicate(new Request(RequestType.ADD, new AddCommand(obj), username, password));
                        }
                    } catch (Exception e) {
                        System.out.println(Thread.currentThread().getName() + " : " + "Неправильный формат задания объекта : " + args[1]);
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " : " + "Неверное число аргументов");
                }
                break;
            case "update":
                if(args.length == 3) {
                    try {
                        int id = Integer.parseInt(args[1]);
                        VehicleSerializable obj = VehicleSerializable.create(args[2]);
                        if(obj != null) {
                            communicate(new Request(RequestType.UPDATE, new UpdateCommand(id, obj), username, password));    
                        }
                    } catch (Exception e) {
                        System.out.println(Thread.currentThread().getName() + " : " + "Неправильно введены аргументы : " + e);
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " : " + "Неверное число аргументов.");
                }
                break;
            case "clear":
                communicate(new Request(RequestType.CLEAR, new ClearCommand(), username, password));    
                break;

            case "execute_script":
                if(args.length == 2) {
                    needExit = execute(args[1]);
                } else {
                    System.out.println(Thread.currentThread().getName() + " : " + "Неверное число аргументов.");
                }
                break;
            
            case "remove_by_id":
                if(args.length == 2) {
                    try {
                        int id = Integer.parseInt(args[1]);
                        communicate(new Request(RequestType.REMOVE_BY_ID, new RemoveByIdCommand(id), username, password));   
                    } catch (Exception e) {
                        System.out.println(Thread.currentThread().getName() + " : " + "Неправильно введены аргументы : " + e);
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " : " + "Неверное число аргументов.");
                }
                break;

            case "remove_at":
                if(args.length == 2) {
                    try {
                        int idx = Integer.parseInt(args[1]);
                        communicate(new Request(RequestType.REMOVE_AT, new RemoveAtIndexCommand(idx), username, password));   
                    } catch (Exception e) {
                        System.out.println(Thread.currentThread().getName() + " : " + "Неправильно введены аргументы : " + e);
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " : " + "Неверное число аргументов.");
                }
                break;

            case "remove_lower":
                if(args.length == 2) {
                    VehicleSerializable obj = VehicleSerializable.create(args[1]);
                    if(obj != null) {
                        communicate(new Request(RequestType.REMOVE_LOWER, new RemoveLowerCommand(obj), username, password));  
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " : " + "Неверное число аргументов.");
                }
                break;

            case "sort":
                communicate(new Request(RequestType.SORT, new SortCommand(), username, password));  
                break;
            
            case "filter_starts_with_name":
                if(args.length == 2) {
                    String str = args[1];
                    communicate(new Request(RequestType.FILTER_STARTS_WITH_NAME, new FilterStartsWithNameCommand(str), username, password));  
                } else {
                    System.out.println(Thread.currentThread().getName() + " : " + "Неверное число аргументов.");
                }
                break;

            case "print_unique_fuel_type":
                communicate(new Request(RequestType.PRINT_UNIQUE_FUEL_TYPE, new PrintUniqueFuelTypeCommand(), username, password));  
                break;

            case "print_field_descending_type":
                communicate(new Request(RequestType.PRINT_FIELD_DESCENDING_TYPE, new PrintFieldDescendingTypeCommand(), username, password));  
                break;

            default:
                System.out.println(Thread.currentThread().getName() + " : " + "Неопознанная команда. Наберите 'help' для справки.");
                break;
        }
        return needExit;
    }
}