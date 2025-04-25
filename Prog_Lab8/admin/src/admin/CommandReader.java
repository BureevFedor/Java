package admin;

import java.util.Scanner;

import commands.*;

import communication.Response;
import communication.Request;
import communication.RequestType;

public class CommandReader {

    private Scanner scanner;
    private Client client;

    public CommandReader(Scanner scanner, Client client) {
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
            System.out.println("Введите команду управления сервером:");
            
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

   
    private void printHelp() {
        System.out.println("help : вывести справку по доступным командам");   
        System.out.println("exit : завершить программу");
        System.out.println("exitServer : завершить работу сервера");
        System.out.println("reguisterUser username password : зарегистрировать пользователя");
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

            case "exitServer":
                communicate(new Request(RequestType.EXIT, new ExitCommand(), "", ""));
                break;
            
            case "registerUser":
                if(args.length == 3) {
                    communicate(new Request(RequestType.REGISTER_USER, new RegisterUserCommand(args[1], args[2]), "", ""));
                } else {
                    System.out.println("Неверное число аргументов");
                } 
                break;

            default:
                System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                break;
        }
        return needExit;
    }

}