package data;

import java.time.LocalDateTime;
import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Класс LocalDateTimeDeserializer
 * @author Буреев Фёдор
 * @version 1.0
 */
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
      /** Десериализует объект класса LocalDateTime
     * @param json Json-элемент, который десериализуют
     * @param typeOfT Тип объекта, в который прооисходит десериализация
     * @param context Контекст десериализации
     * @return Объект класса LocalDateTime
     */
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		  return LocalDateTime.parse(json.getAsString());
	}
}