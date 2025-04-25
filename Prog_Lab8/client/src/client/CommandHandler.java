package client;

import data.VehicleSerializable;
import commands.*;

import communication.Response;
import communication.ResponseCode;
import communication.Request;
import communication.RequestType;

import javax.swing.JOptionPane;

import client.graphics.VehicleItem;
import client.graphics.VehiclesTableModel;

import java.util.ArrayList;

import client.graphics.Resources;

public class CommandHandler {
    private Client client;
    private VehiclesTableModel model;
    private CommandReader commandReader; 
    private Thread updater;

    public CommandHandler(Client client, VehiclesTableModel model) {
        this.model = model;
        this.client = client;
        this.commandReader = new CommandReader(null, client, client.getUsername(), client.getPassword());

        model.setCommandHandler(this);

        updater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    doShow();
                    try { 
                        Thread.sleep(5000); 
                    } catch (InterruptedException e) {
                        //System.out.println("!!! : " + e);
                        break;
                    }
                }
                System.out.println("Updater завершает работу");
            }
        });
    }

    public synchronized void doExecuteScript(String fileName) {
        commandReader.runCommand("execute_script " + fileName);
        doShow();
    }
  
    public synchronized void doHelp() {
        JOptionPane.showMessageDialog(null, 
            "help : вывести справку по доступным командам" + "\n" + 
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)" + "\n" + 
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении" + "\n" + 
            "add {element} : добавить новый элемент в коллекцию" + "\n" + 
            "update id {element} : обновить значение элемента коллекции, id которого равен заданному" + "\n" + 
            "remove_by_id id : удалить элемент из коллекции по его id" + "\n" + 
            "clear : очистить коллекцию" + "\n" + 
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме." + "\n" + 
            "exit : завершить программу" + "\n" + 
            "remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)" + "\n" +
            "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный" + "\n" + 
            "sort : отсортировать коллекцию в естественном порядке" + "\n" + 
            "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки" + "\n" +
            "print_unique_fuel_type : вывести уникальные значения поля fuelType всех элементов в коллекции" + "\n" + 
            "print_field_descending_type : вывести значения поля type всех элементов в порядке убывания");
    }

    public synchronized void doShow() {
        Response response = client.doCommand(new Request(RequestType.SHOW, new ShowCommand(), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(),
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
            } else {
                String str = response.getText();
                //System.out.println(str);
                String[] ss = str.split("\n");
                ArrayList<VehicleItem> items = new ArrayList<VehicleItem>();
                for(String s : ss) {
                    if(!s.isEmpty()) {
                        VehicleItem item = new VehicleItem();
                        if (item.fill(s) == null) {
                            items.add(item);
                        }
                    }
                }
                model.update(items);
            }
        }
    }

    public synchronized void doInfo() {
        Response response = client.doCommand(new Request(RequestType.INFO, new InfoCommand(), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(),
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, response.getText());
            }
        }
    }

    public synchronized void doPrintUniqueFuelType() {
        Response response = client.doCommand(new Request(RequestType.PRINT_UNIQUE_FUEL_TYPE, new PrintUniqueFuelTypeCommand(), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(), 
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
            } else {
                if(response.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "В коллекции нет элементов с уникальным типом топлива.");
                } else {
                    JOptionPane.showMessageDialog(null, response.getText());
                }
            }
        }
    }

    public synchronized void doAdd(VehicleSerializable item) {
        Response response = client.doCommand(new Request(RequestType.ADD, new AddCommand(item), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(), 
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            return;
        }

        doShow();
    }

    public synchronized void doUpdate(int id, VehicleSerializable item) {
        Response response = client.doCommand(new Request(RequestType.UPDATE, new UpdateCommand(id, item), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(), 
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            return;
        }

        doShow();
    }

    public synchronized void doRemoveItemById(int id) {
        Response response = client.doCommand(new Request(RequestType.REMOVE_BY_ID, new RemoveByIdCommand(id), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(), 
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            return;
        }

        doShow();
    }

    public synchronized void doRemoveItemByIndex(int idx) {
        Response response = client.doCommand(new Request(RequestType.REMOVE_AT, new RemoveAtIndexCommand(idx), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(), 
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            return;
        }

        doShow();
    }

    public synchronized void doRemoveLowerItems(VehicleSerializable item) {
        Response response = client.doCommand(new Request(RequestType.REMOVE_LOWER, new RemoveLowerCommand(item), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(), 
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            return;
        }

        doShow();
    }

    public synchronized void doClear() {
        Response response = client.doCommand(new Request(RequestType.CLEAR, new ClearCommand(), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(), 
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Данные успешно удалены.");
            }
        } else {
            return;
        }

        doShow();        
    }

    public synchronized boolean doAuthorize() {
        Response response = client.doCommand(new Request(RequestType.AUTHORIZE, new AuthorizeCommand(), client.getUsername(), client.getPassword()));
        if(response != null) {
            if (response.getCode() == ResponseCode.ERROR) {
                JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_ERROR) + " : " + response.getText(), 
                    Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (response.getCode() == ResponseCode.SUCCESS) {
                try {
                    model.setUserId(Integer.parseInt(response.getText()));
                    return true;
                } catch (Exception e) {}
            }
        }
        JOptionPane.showMessageDialog(null, Resources.getString(Resources.TEXT_AUTH_ERROR), 
            Resources.getString(Resources.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public void doStart() {
        updater.start();
    }

    public void doStop() {
        updater.interrupt();
        /*try {
            updater.join();
        } catch (Exception e) { }*/
    }
}