package server;

import communication.Request;
import communication.RequestType;
import communication.Response;
import communication.ResponseCode;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

import commands.*;

public class CommandHandler {

    private VehicleCollection vehicles;
    private UserManager userManager;
    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    private ServerLogger logger;

    public CommandHandler(VehicleCollection vehicles, UserManager userManager, ServerLogger logger) {
        this.vehicles = vehicles;
        this.userManager = userManager;
        this.logger = logger;
    }

    private class CommandHandlerTask extends RecursiveTask<Response> {
        private Request request;

        public CommandHandlerTask(Request request) {
            this.request = request;
        }

        @Override   
        protected Response compute() {
            return doCommand(request);
        }
    }

    public Response handle(Request request) {
        return forkJoinPool.invoke(new CommandHandlerTask(request));
    }
 
    private synchronized Response doCommand(Request request) {
        RequestType type = request.getType();
        logger.info("CommandHandler получил команду : " + type.toString());
        int userId = UserManager.UNKNOWN_USER_ID;
        
        if(!request.getUsername().isEmpty() || !request.getPassword().isEmpty()) {
            userId = userManager.getUserId(request.getUsername(), request.getPassword());
            if(userId == UserManager.UNKNOWN_USER_ID) {
                return new Response(ResponseCode.ERROR, "Пользователь не зарегистрирован");
            }
        }

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
                Result res = vehicles.add(cmd.getObj().toVehicle(), userId);
                return new Response(res.result ? ResponseCode.SUCCESS : ResponseCode.ERROR, res.text);
            }
            case UPDATE: {
                UpdateCommand cmd = (UpdateCommand) request.getCommand();
                Result res = vehicles.update(cmd.getId(), cmd.getObj().toVehicle(), userId);
                return new Response(res.result ? ResponseCode.SUCCESS : ResponseCode.ERROR, res.text);
            }
            case REMOVE_BY_ID: {
                RemoveByIdCommand cmd = (RemoveByIdCommand) request.getCommand();
                Result res = vehicles.remove(cmd.getId(), userId);
                return new Response(res.result ? ResponseCode.SUCCESS : ResponseCode.ERROR, res.text);
            }
            case CLEAR: {
                //ClearCommand cmd = (ClearCommand) request.getCommand();
                vehicles.clear(userId);
                return new Response(ResponseCode.SUCCESS, "");
            }
            case REMOVE_AT: {
                RemoveAtIndexCommand cmd = (RemoveAtIndexCommand) request.getCommand();
                Result res = vehicles.remove_at(cmd.getIndex(), userId);
                return new Response(res.result ? ResponseCode.SUCCESS : ResponseCode.ERROR, res.text);
            }
            case REMOVE_LOWER: {
                RemoveLowerCommand cmd = (RemoveLowerCommand) request.getCommand();
                Result res = vehicles.remove_lower(cmd.getObj().toVehicle(), userId);
                return new Response(res.result ? ResponseCode.SUCCESS : ResponseCode.ERROR, res.text);
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
            case REGISTER_USER: {
                RegisterUserCommand cmd = (RegisterUserCommand) request.getCommand();
                if(userManager.add(cmd.getUsername(), cmd.getPassword())) {
                    return new Response(ResponseCode.SUCCESS, "Пользователь зарегистрирован");
                } else {
                    return new Response(ResponseCode.ERROR, "Ошибка регистрации пользователя");
                }
            }
            case EXIT: {
                //ExitCommand cmd = (ExitCommand) request.getCommand();
                return new Response(ResponseCode.SUCCESS, "Сервер завершает работу");
            }
        }
        return new Response(ResponseCode.ERROR, "Неизвестный запрос.");
    }
}
