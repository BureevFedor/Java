package data;

import com.google.gson.*;

import java.io.Serializable;

public class VehicleSerializable implements Serializable {
    private String name;
    private Float x;
    private Long y;
    private Integer enginePower;
    private Long fuelConsumption;
    private VehicleType type;
    private FuelType fuelType;
    
    public static VehicleSerializable create(String str) {
        try {
            Gson gson = Vehicle.getDeserializer();
            Vehicle obj = gson.fromJson(str, Vehicle.class);       
            return new VehicleSerializable(obj.getName(), obj.getCoordinates().getX(), obj.getCoordinates().getY(), obj.getEnginePower(), obj.getFuelConsumption(), obj.getType(), obj.getFuelType());
        } catch (Exception e) {
            System.out.println("Ошибка чтения объекта : " + e);
            return null;
        }
    }

    public VehicleSerializable(String name, float x, long y, int enginePower, long fuelConsumption, VehicleType type, FuelType fuelType) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.type = type;
        this.fuelType = fuelType;
    }

    public Vehicle toVehicle () {
        return new Vehicle(0, name, new Coordinates(x, y), null, enginePower, fuelConsumption, type, fuelType);
    }
}
