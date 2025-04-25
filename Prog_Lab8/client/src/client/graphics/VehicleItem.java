package client.graphics;

import data.VehicleType;
import data.FuelType;
import data.VehicleSerializable;

public class VehicleItem {
    private Integer id;
    private String name;
    private Float x;
    private Long y;
    private String creationDate;
    private Integer enginePower;
    private Long fuelConsumption;
    private String type; //!
    private String fuelType; //!
    private Integer userId;

    public VehicleItem(Integer id, String name, Float x, Long y, String creationDate, Integer enginePower, Long fuelConsumption, String type, String fuelType, Integer userId) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.type = type;
        this.fuelType = fuelType;
        this.userId = userId;
    }

    public VehicleItem() {
        id = 0;
        name = "";
        x = (float)0;
        y = (long)0;
        creationDate = "";
        enginePower = 0;
        fuelConsumption = (long)0;
        type = "";
        fuelType = "";
        userId = 0;
    }

    public VehicleSerializable createSerializable() {
        return new VehicleSerializable(
            name, 
            x, 
            y, 
            enginePower, 
            fuelConsumption, 
            (type.isEmpty() ? null : VehicleType.valueOf(type)), 
            (fuelType.isEmpty() ? null : FuelType.valueOf(fuelType)));
    }

    public String fill(String str) {
        String[] s = str.split(",");

        if(s.length != 10) {
            System.out.println("Invalid item : " + str);
            return null;
        }
        
        return fill(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9]);
    }

    public String fill (String id, String name, String x, String y, String creationDate, String enginePower, String fuelConsumption, String type, String fuelType, String userId) {
        String err = "";

        if(id != null) {
            try {
                this.id = Integer.parseInt(id);
            } catch (Exception e) {
                err += "Ошибка: Идентификатор объекта должен быть целым числом" + "\n";
            }
        }

        if(name != null) {
            if(name.isEmpty()) {
                err += "Ошибка: Имя не может быть пустым" + "\n";
            } else {
                this.name = name;
            }
        }

        try {
            this.x = Float.parseFloat(x);
        } catch (Exception e) {
            err += "Ошибка: Координата X должна быть плавающим числом" + "\n";
        }

        try {
            this.y = Long.parseLong(y);
        } catch (Exception e) {
            err += "Ошибка: Координата Y должна быть плавающим числом" + "\n";
        }

        if(creationDate != null) {
            if(creationDate.isEmpty()) {
                err += "Ошибка: Дата создания не может быть пустой" + "\n";
            } else {
                this.creationDate = creationDate;
            }
        }

        try {
            this.enginePower = Integer.parseInt(enginePower);
        } catch (Exception e) {
            err += "Ошибка: Мощь двигателя должна быть целым числом больше нуля" + "\n";
        }

        try {
            this.fuelConsumption = Long.parseLong(fuelConsumption);
        } catch (Exception e) {
            err += "Ошибка: Уровень потребления топлива должен быть числом больше нуля" + "\n";
        }

        try {
            this.type = (type == null || type.isEmpty()) ? "" : VehicleType.valueOf(type).toString();
        } catch (Exception e) {
            err += "Ошибка: Неверный тип транспортного средства" + "\n";
        }

        try {
            this.fuelType = (fuelType == null || fuelType.isEmpty()) ? "" : FuelType.valueOf(fuelType).toString();
        } catch (Exception e) {
            err += "Ошибка: Неверный тип топлива" + "\n";
        }

        if(userId != null) {
            try {
                this.userId = Integer.parseInt(userId);
            } catch (Exception e) {
                err += "Ошибка: Идентификатор пользователя должен быть целым числом" + "\n";
            }
        }

        if(err.isEmpty()) {
            return null;
            //return new VehicleItem(v_id, v_name, v_x, v_y, v_creationDate, v_enginePower, v_fuelConsumption, v_type, v_fuelType, v_userId);
        }
        
        return err;
    }

    public Integer getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public String getFuelType () {
        return fuelType;
    }

    public String getType () {
        return type;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public Long getFuelConsumption() {
        return fuelConsumption;
    }

    public String getCreationDate() { 
        return creationDate;
    }

    public Float getX() { 
        return x;
    }

    public Long getY() { 
        return y;
    }

    public Integer getUserId() { 
        return userId;
    }
    
    public void setUserId(Integer id) { 
        this.userId = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public void setEnginePower(Integer enginePower) {
        this.enginePower = enginePower;
    }

    public void setFuelConsumption(Long fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public static String[] getTypeStrings() {
        return new String[] {"", "PLANE", "SUBMARINE", "BOAT", "BICYCLE", "CHOPPER"};
    }

    public static String[] getFuelTypeStrings() {
        return new String[] {"", "ALCOHOL", "MANPOWER", "ANTIMATTER"};
    }
}
