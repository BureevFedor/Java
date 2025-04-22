package server;

import communication.Request;
import communication.RequestType;
import communication.Response;
import communication.ResponseCode;

import commands.*;

public class CommandHandler {

    private VehicleCollection vehicles;

    public CommandHandler(VehicleCollection vehicles) {
        this.vehicles = vehicles;
    }

    public Response doCommand(Request request) {
        RequestType type = request.getType();
        System.out.println("Получил команду : " + type.toString());
        switch(type) {
            case INFO: {
                //InfoCommand cmd = (InfoCommand) request.getCommand();
                return new Response(ResponseCode.SUCCESS, vehicles.info());
            }
            case SHOW: {
                //ShowCommand cmd = (ShowCommand) request.getCommand();
                return new Response(ResponseCode.SUCCESS, vehicles.show());
            }
            case ADD: {
                AddCommand cmd = (AddCommand) request.getCommand();
                if(vehicles.add(cmd.getObj().toVehicle())) {
                    return new Response(ResponseCode.SUCCESS, "Элемент успешно добавлен в коллекцию.");
                } else {
                    return new Response(ResponseCode.ERROR, "Не получилось добавить элемент в коллекцию.");
                }
            }
            case UPDATE: {
                UpdateCommand cmd = (UpdateCommand) request.getCommand();
                if(vehicles.update(cmd.getId(), cmd.getObj().toVehicle())) {
                    return new Response(ResponseCode.SUCCESS, "Элемент успешно обновлён в коллекции.");
                } else {
                    return new Response(ResponseCode.ERROR, "Объект с этим Id не был найден.");
                }
            }
            case REMOVE_BY_ID: {
                RemoveByIdCommand cmd = (RemoveByIdCommand) request.getCommand();
                if(vehicles.remove(cmd.getId())) {
                    return new Response(ResponseCode.SUCCESS, "Элемент успешно удалён из коллекции.");
                } else {
                    return new Response(ResponseCode.SUCCESS, "Элемент с этим Id не был найден.");
                }
            }
            case CLEAR: {
                //ClearCommand cmd = (ClearCommand) request.getCommand();
                vehicles.clear();
                return new Response(ResponseCode.SUCCESS, "");
            }
            case REMOVE_AT: {
                RemoveAtIndexCommand cmd = (RemoveAtIndexCommand) request.getCommand();
                if(vehicles.remove_at(cmd.getIndex())) {
                    return new Response(ResponseCode.SUCCESS, "Элемент успешно удалён из коллекции.");
                } else {
                    return new Response(ResponseCode.SUCCESS, "Элемент по этому индексу не был найден.");
                }
            }
            case REMOVE_LOWER: {
                RemoveLowerCommand cmd = (RemoveLowerCommand) request.getCommand();
                if (vehicles.remove_lower(cmd.getObj().toVehicle()) ) {
                    return new Response(ResponseCode.SUCCESS, "Элемент(ы) успешно удалены из коллекции. ");
                } else {
                    return new Response(ResponseCode.SUCCESS, "Элементов для удаления не найдено.");
                }
            }
            case SORT: {
                //SortCommand cmd = (SortCommand) request.getCommand();
                vehicles.sort();
                return new Response(ResponseCode.SUCCESS, "Коллекция отсортирована по параметру EnginePower.");
            }
            case FILTER_STARTS_WITH_NAME: {
                FilterStartsWithNameCommand cmd = (FilterStartsWithNameCommand) request.getCommand();
                return new Response(ResponseCode.SUCCESS, vehicles.filter_starts_with_name(cmd.getName()));
            }
            case PRINT_UNIQUE_FUEL_TYPE: {
                //PrintUniqueFuelTypeCommand cmd = (PrintUniqueFuelTypeCommand) request.getCommand();
                return new Response(ResponseCode.SUCCESS, vehicles.print_unique_fuel_type());
            }
            case PRINT_FIELD_DESCENDING_TYPE: {
                //PrintFieldDescendingTypeCommand cmd = (PrintFieldDescendingTypeCommand) request.getCommand();
                return new Response(ResponseCode.SUCCESS, vehicles.print_field_descending_type());
            }
            case SAVE: {
                //SaveCommand cmd = (SaveCommand) request.getCommand();
                if (vehicles.save()) {
                    return new Response(ResponseCode.SUCCESS, "Коллекция успешно сохранена в файле.");
                } else {
                    return new Response(ResponseCode.ERROR, "Ошибка сохранения коллекции.");
                }
            }
            case EXIT: {
                //ExitCommand cmd = (ExitCommand) request.getCommand();
                break;
            }
        }
        return new Response(ResponseCode.ERROR, "Неизвестный запрос.");
    }
}
