package data;

/**
 * Класс Coordinates
 * @author Буреев Фёдор
 * @version 1.0
 */
public class Coordinates {

    /** Поле Координата x */
    private Float x; //Поле не может быть null
    
    /** Поле Координата y */
    private long y;

    
    /** Конструктор объекта класса Coordinates
     * @param x Координата x
     * @param y Координата y
     */
    public Coordinates(Float x, long y)
    {
        this.x = x;
        this.y = y;
    }

    /** Возвращает значение координаты x
     * @return Координата x
     */
    public Float getX() {
        return this.x;
    }

    /** Возвращает значение координаты y
     * @return Координата y
     */
    public long getY() {
        return this.y;
    }
}