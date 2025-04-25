package data;

import java.time.LocalDateTime;
import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Класс VehicleDeserializer
 * @author Буреев Фёдор
 * @version 1.0
 */
public class VehicleDeserializer implements JsonDeserializer<Vehicle> {
    /** Десериализует объект класса Vehicle
     * @param json Json-элемент, который десериализуют
     * @param typeOfT Тип объекта, в который прооисходит десериализация
     * @param context Контекст десериализации
     * @return Объект класса Vehicle
     */
    @Override
    public Vehicle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement element;

        int id = 0;
        element = jsonObject.get("id");
        if(element != null) {
            id = element.getAsInt();
        }

        String name = jsonObject.get("name").getAsString();
        Coordinates coordinates = context.deserialize(jsonObject.get("coordinates"), Coordinates.class);

        LocalDateTime creationDate = null;
        element = jsonObject.get("creationDate");
        if(element != null) {
            creationDate = context.deserialize(element, LocalDateTime.class);
        }
        
        int enginePower = jsonObject.get("enginePower").getAsInt();
        long fuelConsumption = jsonObject.get("fuelConsumption").getAsLong();

        element = jsonObject.get("type");
        VehicleType type = null;
        if(element != null) {
            type = VehicleType.valueOf(element.getAsString());
        }
        element = jsonObject.get("fuelType");
        FuelType fuelType = null;
        if(element != null) {
            fuelType = FuelType.valueOf(element.getAsString());
        }

		return new Vehicle(id, name, coordinates, creationDate, enginePower, fuelConsumption, type, fuelType, 0);
	}
}