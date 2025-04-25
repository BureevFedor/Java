package data;

import java.time.LocalDateTime;
import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Класс CoordinatesSerializer
 * @author Буреев Фёдор
 * @version 1.0
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

    /** Сериализует объект класса LocalDateTime
     * @param obj объект класса LocalDateTime, который сериализуют
     * @param type Тип объекта, для которого происходит сериализация
     * @param context Контекст сериализации
     * @return JsonElement сериализованного объекта
     */    
    @Override
    public JsonElement serialize(LocalDateTime obj, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(obj.toString());
    }
}