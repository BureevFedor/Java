package data;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Класс CoordinatesDeserializer
 * @author Буреев Фёдор
 * @version 1.0
 */
public class CoordinatesDeserializer implements JsonDeserializer<Coordinates> {

    /** Десериализует объект класса Coordinates
     * @param json Json-элемент, который десериализуют
     * @param typeOfT Тип объекта, в который прооисходит десериализация
     * @param context Контекст десериализации
     * @return Объект класса Coordinates
     */
    @Override
    public Coordinates deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        
        Float x = jsonObject.get("x").getAsFloat();
        Long y = jsonObject.get("y").getAsLong();

		return new Coordinates(x, y);
	}
}