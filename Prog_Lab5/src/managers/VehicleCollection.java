package managers;

import java.util.LinkedList;
import java.util.ArrayList;

import com.google.gson.*;

import data.FuelType;
import data.SortByEnginePower;
import data.Vehicle;
import data.VehicleType;

import java.io.FileOutputStream;
import java.io.InputStreamReader;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStream;

import java.util.Collections;

import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Paths;

import com.google.gson.reflect.TypeToken;

/**
 * Класс VehicleCollection
 * @author Буреев Фёдор
 * @version 1.0
 */
public class VehicleCollection {

    /** Поле коллекции объектов класса Vehicle */
    private LinkedList<Vehicle> vehicles;
    
    /** Поле флага сохранения коллекции в файле */
    private boolean isSaved = true;

    /** Поле имени файла для сохранения коллекции */
    private String fileName = "";
    
    /** Поле имени временного файла для сохранения коллекции */
    private String tmpFileName = "temp_vehicles.txt";

    /** 
     * Возвращает имя файла для сохранения коллеккции
     * @return имя файла
     */    
    public String getFileName() {
        return fileName;
    }

    /** 
     * Возвращает имя временного файла для сохранения коллеккции
     * @return имя временного файла
     */        
    public String getTmpFileName() {
        return tmpFileName;
    }

    /** 
     * Возвращает состояние флага сохранения коллекции в файле
     * @return true, если коллекцкия нуждается в сохранении, false, если не нуждается
     */    
    public boolean isSaved () {
        return this.isSaved;
    }

    /** 
     * Конструктор класса VehicleCollection
     * @param fileName имя файла для сохранения коллекции
     */        
    public VehicleCollection (String fileName) {
        vehicles = new LinkedList<>();
        this.fileName = fileName;
    }

    /** 
     * Выводит информацию о коллекции
     */    
    public void info () {
        System.out.println("Тип коллекции: LinkedList");
        System.out.println("Объекты коллекции: Vehicle");
        System.out.println("Количество элементов: " + vehicles.size());

        try {
            BasicFileAttributes attr = Files.readAttributes(Paths.get(fileName), BasicFileAttributes.class);
            System.out.println("Дата инициализации: " + attr.creationTime());
        } catch (Exception e) {
            System.out.println("Не удалось получить дату инициализации коллекции");
        }
    }

    /** 
     * Печатает все элементы коллекции
     */       
    public void show () {
        vehicles.forEach(vehicle -> {
            vehicle.print();
        });
    }

    /** 
     * Добавляет элемент в коллекцию
     * @param element Объект класса Vehicle
     * @return true, если добавление успешно, false, если нет
     */   
    private boolean add_element (Vehicle element) {    
        for(int i = 0; i < vehicles.size(); i++) {
            if(element.getId() == vehicles.get(i).getId()) {
                System.out.println("Id нового объекта совпал с Id уже существующего объекта. Пожалуйста, поменяйте или уберите Id.");
                return false;
            }
        }
        vehicles.add(element);
        return true;
    }

    /** 
     * Добавляет элемент в коллекцию
     * @param element Объект класса Vehicle
     */   
    public void add (Vehicle element) {    
       if(add_element(element)) {
            this.isSaved = false;
            save_tmp();
       }
    }

    /** 
     * Добавляет элемент в коллекцию
     * @param string Json-строка с параметрами нового объекта класса Vehicle
     */   
    public void add (String string) {
        Vehicle obj = Vehicle.create(string);
        if(obj != null) {
            add(obj);
        }
    }

    /** 
     * Обновляет элемент с указанным идентификатором
     * @param id идентификатор изменяемого объекта
     * @param element Объект класса Vehicle, заменяющий объект коллекции с заданным идентификатором
     * @return true, если обновление успешно, false, если нет
     */   
    public boolean update (int id, Vehicle element) {
        boolean found = false;

        for(int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId() == id) {
                vehicles.get(i).update(element);
                found = true;
                break;
            }
        }
        if(found) {
            this.isSaved = false;
            save_tmp();
        }

        return found;
    }

    /** 
     * Удаляет элемент с указанным идентификатором
     * @param id идентификатор удаляемого объекта
     * @return true, если удаление прошло успешно, false, если нет
     */   
    public boolean remove (int id) {
        boolean found = false;

        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId() == id) {
                found = true;
                vehicles.remove(i);
                break;
            }
        }

        if(found) {
            this.isSaved = false;
            save_tmp();
        }

        return found;
    }

    /** 
     * Удаляет все элементы коллекции
     */       
    public void clear () {
        vehicles.clear();
        this.isSaved = false;
        save_tmp();
    }

    /** 
     * Удаляет элемент с указанным индексом
     * @param index индекс удаляемого объекта
     * @return true, если удаление прошло успешно, false, если нет
     */       
    public boolean remove_at (int index) {
        if(index < vehicles.size()) {
            vehicles.remove(index);
            this.isSaved = false;
            save_tmp();
            return true;
        }
        return false;
    }

    /** 
     * Удаляет элементы коллекции с параметром enginePower, меньшим, чем у указанного объекта
     * @param element указанный объект класса Vehicle
     */   
    public void remove_lower (Vehicle element) {
        vehicles.removeIf( vehicle -> (vehicle.getEnginePower() < element.getEnginePower()) );
        this.isSaved = false;
        save_tmp();
    }

    /** 
     * Сортирует объекты коллекции по значению поля enginePower
     */   
    public void sort () {
        Collections.sort(vehicles, new SortByEnginePower());
        this.isSaved = false;
        save_tmp();
    }

    /** 
     * Печатает все элементы коллекции, имена которых начинаются с заданной подстроки (префикса)
     * @param name префикс
     */   
    public void filter_starts_with_name (String name) {
        vehicles.forEach(vehicle -> {
            if (vehicle.getName().startsWith(name)) {
                vehicle.print();
            }
        });
    }

    /** 
     * Печатает типы топлива, попадающиеся в коллекции ровно один раз
     */       
    public void print_unique_fuel_type () {
        int[] array = new int[FuelType.values().length];
        
        for(int i = 0; i < FuelType.values().length; i++) {
            array[i] = 0;
        }

        for(int i = 0; i < vehicles.size(); i++) {
            FuelType fuelType = vehicles.get(i).getFuelType();
            if(fuelType != null) {
                array[fuelType.ordinal()]++;
            }
        }

        for(int i = 0; i < FuelType.values().length; i++) {
            if(array[i] == 1) {
                System.out.println(FuelType.values()[i]);
            }
        }
    }

    /** 
     * Печатает значения поля type всех элементов коллекции в порядке убывания
     */    
    public void print_field_descending_type () {
        int n = VehicleType.values().length + 1;
        ArrayList<ArrayList<Integer>> array = new ArrayList<ArrayList<Integer>>(n);
        for(int i = 0; i < n; i++) {
            array.add(new ArrayList<Integer>());
        }

        for(int i = 0; i < vehicles.size(); i++) {
            VehicleType type = vehicles.get(i).getType();
            if(type != null) {
                array.get(vehicles.get(i).getType().ordinal() + 1).add(i);
            } else {
                array.get(0).add(i);
            }
        }

        for(int i = array.size() - 1; i >= 0; i--) {
            for(int j = 0; j < array.get(i).size(); j++) {
                System.out.println(vehicles.get(array.get(i).get(j)).getType());
            }
        }
    }

    /** 
     * Загружает в коллекцкию элементы из указанного файла
     * @param fileName имя файла
     * @return true, если загрузка прошла успешно, false, если нет
     */           
    private boolean load_elements (String fileName) {
        try { 
            Gson gson = Vehicle.getDeserializer();
            InputStream fin = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

            String line = reader.readLine();
            if(line != null) {
                vehicles = gson.fromJson(line, new TypeToken<LinkedList<Vehicle>>() {}.getType());
            }

            reader.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    /** 
     * Загружает элементы в коллекцию
     * @return true, если загрузка прошла успешно, false, если нет
     */                
    public boolean load () {
        return load_elements(this.fileName);
    } 

    /** 
     * Загружает элементы из временного файла в коллекцию
     * @return true, если загрузка прошла успешно, false, если нет
     */       
    public boolean load_tmp () {
        this.isSaved = false;
        return load_elements(this.tmpFileName);
    }

    /** 
     * Сохраняет коллекцию в указанный файл
     * @param fileName имя файла
     * @return true, если сохранение прошло успешно, false, если нет
     */           
    private boolean save_elements (String fileName) {
        try { 
            Gson gson = Vehicle.getSerializer();
            FileOutputStream fout = new FileOutputStream(fileName);      
            fout.write(gson.toJson(vehicles).getBytes());
            fout.close();
            return true; 
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    /** 
     * Сохраняет коллекцию в файл
     */   
    public void save () {
        if(save_elements(this.fileName)) {
            this.isSaved = true;
        }
    }

    /** 
     * Сохраняет коллекцию во временный файл
     */   
    public void save_tmp () {
        save_elements(this.tmpFileName);
    }
}
