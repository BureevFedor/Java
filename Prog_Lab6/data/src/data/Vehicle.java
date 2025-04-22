package data;

import java.time.LocalDateTime;
import com.google.gson.*;

/**
 * Класс Vehicle
 * @author Буреев Фёдор
 * @version 1.0
 */
public class Vehicle implements Comparable<Vehicle> {

     /** Поле идентификатор объекта*/
    private int id;
    
    /** Поле имя транспортного средства */
    private String name;

    /** Поле координаты объекта */
    private Coordinates coordinates;
    
    /** Поле время создания объекта */
    private LocalDateTime creationDate;
    
    /** Поле мощность двигателя объекта*/
    private int enginePower;

    /** Поле потребление топлива объекта */
    private long fuelConsumption;

    /** Поле тип транспортного средства */
    private VehicleType type;
    
    /** Поле тип топлива */
    private FuelType fuelType;

    /** Поле для вычисления свободного id */
    private static int lastId = 0;

    /** 
     * Возвращает объект для десериализации класса Vehicle
     * @return объект класса Gson
     */
    public static Gson getDeserializer() {
        return new GsonBuilder().
            registerTypeAdapter(Vehicle.class, new VehicleDeserializer()).
            registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer()).
            registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).
            create();
    }

    /** 
     * Возвращает объект для сериализации класса Vehicle 
     * @return объект класса Gson
     */
    public static Gson getSerializer() {
        return new GsonBuilder().
            registerTypeAdapter(Vehicle.class, new VehicleSerializer()).
            registerTypeAdapter(Coordinates.class, new CoordinatesSerializer()).
            registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).
            create();
    }

    /** 
     * Создаёт объект типа Vehicle по строке
     * @param str Json-строка с параметрами объекта 
     * @return объект класса Vehicle
     */
    public static Vehicle create(String str) {
        try {
            Gson gson = Vehicle.getDeserializer();
            Vehicle obj = gson.fromJson(str, Vehicle.class);       
            return obj;
        } catch (Exception e) {
            System.out.println("Ошибка чтения объекта : " + e);
            return null;
        }
    }

    /** 
     * Создаёт объект типа Vehicle по указанным параметрам
     * @param id идентификатор объекта
     * @param name имя объекта
     * @param coordinates координаты транспортного средства
     * @param creationDate время создания объекта
     * @param enginePower мощь двигателя транспортного средства
     * @param fuelConsumption потребление топлива транспортного средства
     * @param type тип транспортного средства
     * @param fuelType тип топлива, потребляемого транспортным средством
     */
    public Vehicle(int id, String name, Coordinates coordinates, LocalDateTime creationDate, int enginePower, long fuelConsumption, VehicleType type, FuelType fuelType) {
        if(id == 0) {
            lastId++;
            this.id = lastId;
        } else {
            if(lastId < id) {
                lastId = id;
            }
            this.id = id;
        }
        
        this.name = name;
        this.coordinates = coordinates;
        if(creationDate == null) { 
            this.creationDate = LocalDateTime.now();    
        } else {
            this.creationDate = creationDate;
        }
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.type = type;
        this.fuelType = fuelType;
    }

    public String print() {
        String result =  
            "id: " + id + "\n" + 
            "name: " + name + "\n" + 
            "coordinates: " + coordinates.getX() + ", " + coordinates.getY() + "\n" + 
            "creationDate: " + creationDate + "\n" + 
            "enginePower: " + enginePower + "\n" + 
            "fuelConsumption: " + fuelConsumption + "\n" + 
            "type: " + type + "\n" + 
            "fuelType: " + fuelType + "\n";

        return result;
    }

    
    /** 
     * Возвращает идентификатор объекта 
     * @return идентификатор объекта
     */
    public int getId () {
        return id;
    }

    /** 
     * Возвращает имя объекта 
     * @return имя объекта
     */
    public String getName () {
        return name;
    }

    /** 
     * Возвращает тип топлива объекта 
     * @return тип топлива
     */    
    public FuelType getFuelType () {
        return fuelType;
    }

    /** 
     * Возвращает тип транспортного средства 
     * @return тип транспортного средства
     */
    public VehicleType getType () {
        return type;
    }

    /** 
     * Возвращает мощность двигателя 
     * @return мощность двигателя
     */    
    public int getEnginePower() {
        return enginePower;
    }

    /** 
     * Возвращает потребление топлива 
     * @return потребление топлива
     */    
    public long getFuelConsumption() {
        return fuelConsumption;
    }

    /** 
     * Возвращает время создания объекта 
     * @return время создания объекта
     */
    public LocalDateTime getCreationDate() { 
        return creationDate;
    }

    /** 
     * Возвращает координаты транспортного средства 
     * @return координаты транспортного средства
     */    
    public Coordinates getCoordinates() { 
        return coordinates;
    }

    /** 
     * Устанавливает идентификатор объекта 
     * @param id идентификатор объекта
     */
    public void setId(int id) {
        this.id = id;
    }

    /** 
     * Устанавливает имя объекта 
     * @param name имя объекта
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Устанавливает координаты транспортного средства 
     * @param x координата x транспортного средства
     * @param y координата y транспортного средства
     */    
    public void setCoordinates(float x, long y) {
        this.coordinates = new Coordinates(x, y);
    }

    /** 
     * Устанавливает мощность двигателя
     * @param enginePower мощность двигателя
     */    
    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    /** 
     * Устанавливает потребление топлива 
     * @param fuelConsumption потребление топлива
     */
    public void setFuelConsumption(long fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    /** 
     * Устанавливает тип транспортного средства 
     * @param type тип транспортного средства
     */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /** 
     * Устанавливает тип топлива 
     * @param fuelType тип топлива
     */
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /** 
     * Копирует все параметры указанного объекта в этот экземпляр объекта
     * @param vehicle указанный объект
     */
    public void update (Vehicle vehicle) {
        //this.id = vehicle.id; // Оставляем id без изменений.
        this.name = vehicle.name;
        this.coordinates = vehicle.coordinates;
        this.creationDate = vehicle.creationDate;
        this.enginePower = vehicle.enginePower;
        this.fuelConsumption = vehicle.fuelConsumption;
        this.type = vehicle.type;
        this.fuelType = vehicle.fuelType;
    } 

    @Override
    public int compareTo(Vehicle vehicle) {
        int id = vehicle.getId();
        if(this.id == id) return 0;
        if (this.id < id) return -1;
        return 1;
    }
}
