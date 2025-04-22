package data;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Класс CoordinatesSerializer
 * @author Буреев Фёдор
 * @version 1.0
 */
public class CoordinatesSerializer implements JsonSerializer<Coordinates> {

    /** Сериализует объект класса Coordinates
     * @param obj объект класса Coordinates, который сериализуют
     * @param type Тип объекта, для которого происходит сериализация
     * @param context Контекст сериализации
     * @return JsonElement сериализованного объекта
     */
    @Override
    public JsonElement serialize(Coordinates obj, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

		result.addProperty("x", obj.getX());
		result.addProperty("y", obj.getY());

        return result;
    }
}