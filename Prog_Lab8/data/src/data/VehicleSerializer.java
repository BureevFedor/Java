package data;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Класс VehicleSerializer
 * @author Буреев Фёдор
 * @version 1.0
 */
public class VehicleSerializer implements JsonSerializer<Vehicle> {

    /** Сериализует объект класса Vehicle
     * @param obj объект класса Vehicle, который сериализуют
     * @param type Тип объекта, для которого происходит сериализация
     * @param context Контекст сериализации
     * @return JsonElement сериализованного объекта
     */    
    @Override
    public JsonElement serialize(Vehicle obj, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

		result.addProperty("id", obj.getId());
		result.addProperty("name", obj.getName());
        result.add("coordinates", context.serialize(obj.getCoordinates()));
        result.add("creationDate", context.serialize(obj.getCreationDate()));
		result.addProperty("enginePower", obj.getEnginePower());
		result.addProperty("fuelConsumption", obj.getFuelConsumption());

        VehicleType vehicleType = obj.getType();
        if(vehicleType != null) {
            result.addProperty("type", vehicleType.toString());
        }
            
        FuelType fuelType = obj.getFuelType();
        if(fuelType != null) {
            result.addProperty("fuelType", fuelType.toString());
        }

        return result;
	}
}