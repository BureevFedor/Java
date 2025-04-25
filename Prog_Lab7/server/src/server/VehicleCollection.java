package server;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import java.util.concurrent.ConcurrentLinkedQueue;

import data.FuelType;
import data.SortByEnginePower;
import data.Vehicle;

public class VehicleCollection {
    private ConcurrentLinkedQueue<Vehicle> vehicles;
    private DatabaseManager databaseManager;

    public VehicleCollection (DatabaseManager databaseManager) {
        vehicles = new ConcurrentLinkedQueue<>();
        this.databaseManager = databaseManager;
    }

    public String info () {
        return "Тип коллекции: ConcurrentLinkedQueue \n" + "Объекты коллекции: Vehicle \n" + "Количество элементов: " + vehicles.size() + "\n";
    }
    
    public String show () {
        return vehicles.stream()
            .map(vehicle -> vehicle.print())
            .collect(Collectors.joining("\n"));
    }

    public Result add (Vehicle element, int userId) {
        try {
            int value = databaseManager.vehiclesGetNextSequenceNumber();
            element.setId(value);
            element.setUserId(userId);

            databaseManager.insertVehicle(element);
            vehicles.add(element);
            return new Result(true, "Объект успешно добавлен в коллекцию.");
        } catch (Exception e) {
            return new Result(false, "Не получилось добавить объект в коллекцию.");
        }
    }

    public Result update (int id, Vehicle element, int userId) {
        Optional<Vehicle> result = vehicles.stream()
            .filter(vehicle -> (vehicle.getId() == id))
            .findFirst();

        if(result.isPresent()) {
            if(result.get().getUserId() != userId) {
                return new Result(false, "Объект принадлежит другому пользователю");
            }
        } else {
            return new Result(false, "Объект с этим id не был найден");
        }

        try {
            databaseManager.updateVehicle(id, element);
            result.get().update(element);
            return new Result(true, "Объект успешно обновлён");
        } catch (Exception E) {
            return new Result(false, "Ошибка обновления объекта");
        }
    }

    public void clear (int userId) {
        try {
            databaseManager.clearVehicles(userId);
            vehicles.removeIf( vehicle -> (vehicle.getUserId() == userId));
        } catch (Exception E) {
            System.out.println("Не получилось очистить таблицу Vehicles в базе данных");
        }
    }

    public Result remove (int id, int userId) {
        Optional<Vehicle> result = vehicles.stream()
            .filter(vehicle -> (vehicle.getId() == id))
            .findFirst();

        if(result.isPresent()) {
            if(result.get().getUserId() != userId) {
                return new Result(false, "Объект принадлежит другому пользователю");
            }
        } else {
            return new Result(true, "Объект с этим id не был найден");
        }

        try {
            databaseManager.deleteVehicle(id);
            vehicles.removeIf( vehicle -> (vehicle.getId() == id) );
            return new Result(true, "Объект успешно удалён");
        } catch (Exception E) {
            return new Result(false, "Ошибка удаления объекта");
        }
    }

    public Result remove_at (int index, int userId) {
        int count = 0;
        Vehicle vehicle = null;
        for(Vehicle v : vehicles) {
            if(count != index) {
                count++;
            } else {
                vehicle = v;
                break;
            }
        }
        if(vehicle == null) return new Result(false, "Объект по этому индексу не был найден : " + index);

        try {
            databaseManager.deleteVehicle(vehicle.getId());
            vehicles.remove(vehicle);
            return new Result(true, "Объект успешно удалён из коллекции.");
        } catch (Exception E) {
            return new Result(false, "Ошибка удаления объекта");
        }
    }

    public Result remove_lower (Vehicle element, int userId) {
        try {
            databaseManager.deleteVehiclesIfLower(element, userId);
            vehicles.removeIf( vehicle -> ((vehicle.getEnginePower() < element.getEnginePower()) && (vehicle.getUserId() == userId)));
            return new Result(true, "Объект(ы) успешно удалены из коллекции");
        } catch (Exception E) {
            return new Result(false, "Ошибка удаления объектов");
        }
    }

    public void sort () {
        LinkedList<Vehicle> vehicleList = new LinkedList<Vehicle>(vehicles);
        Collections.sort(vehicleList, new SortByEnginePower());
        vehicles = new ConcurrentLinkedQueue<Vehicle>(vehicleList);
    }

    public String filter_starts_with_name (String name) {
        return vehicles.stream()
            .filter(vehicle -> vehicle.getName().startsWith(name))
            .map(vehicle -> vehicle.print())
            .collect(Collectors.joining("\n"));
    }
     
    public String print_unique_fuel_type () {
        return Arrays.stream(FuelType.values())
            .filter( val -> vehicles.stream().filter(vehicle -> vehicle.getFuelType() == val).count() == 1 )
            .map( val -> val.toString())
            .collect(Collectors.joining("\n"));
    }

    public String print_field_descending_type () {
        return vehicles.stream()
            .map(vehicle -> vehicle.getType())
            .filter(val -> val != null)
            .sorted(Comparator.reverseOrder())
            .map( val -> val.toString())
            .collect(Collectors.joining("\n"));
    }

    public boolean loadCollection() {
        try {
            vehicles = databaseManager.getVehicles();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
