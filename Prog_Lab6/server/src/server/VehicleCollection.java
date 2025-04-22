package server;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Arrays;
import java.util.Comparator;

import com.google.gson.*;

import data.FuelType;
import data.SortByEnginePower;
import data.Vehicle;

import java.io.FileOutputStream;
import java.io.InputStreamReader;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Paths;

import com.google.gson.reflect.TypeToken;


public class VehicleCollection {
    private LinkedList<Vehicle> vehicles;
    private boolean isSaved = true;
    private String fileName = "";
    private String tmpFileName = "temp_vehicles.txt";

    public String getFileName() {
        return fileName;
    }
     
    public String getTmpFileName() {
        return tmpFileName;
    }

    public boolean isSaved () {
        return this.isSaved;
    }

    public VehicleCollection (String fileName) {
        vehicles = new LinkedList<>();
        this.fileName = fileName;
    }

    public String info () {
        String result = "Тип коллекции: LinkedList \n" + "Объекты коллекции: Vehicle \n" + "Количество элементов: " + vehicles.size() + "\n";
        try {
            BasicFileAttributes attr = Files.readAttributes(Paths.get(fileName), BasicFileAttributes.class);
            result += ("Дата инициализации: " + attr.creationTime());
        } catch (Exception e) {
            System.out.println("Не удалось получить дату инициализации коллекции");
        }
        return result;
    }
    
    public String show () {
        return vehicles.stream()
            .map(vehicle -> vehicle.print())
            .collect(Collectors.joining("\n"));
    }
        
    private boolean add_element (Vehicle element) {    
        if(vehicles.stream().anyMatch(vehicle -> vehicle.getId() == element.getId())) {
            System.out.println("Id нового объекта совпал с Id уже существующего объекта. Пожалуйста, поменяйте или уберите Id.");
            return false;
        }
        vehicles.add(element);
        return true;
    }

    public boolean add (Vehicle element) {    
        if(add_element(element)) {
            this.isSaved = false;
            save_tmp();
            return true;
       }
       return false;
    }

    public void add (String string) {
        Vehicle obj = Vehicle.create(string);
        if(obj != null) {
            add(obj);
        }
    }

    public boolean update (int id, Vehicle element) {
        boolean found = vehicles.stream().anyMatch(vehicle -> { 
            if(vehicle.getId() == id) {
                vehicle.update(element); 
                return true;
            } 
            return false;
        } );

        if(found) {
            this.isSaved = false;
            save_tmp();
        }
        
        return found;
    }

    public boolean remove (int id) {
        if(vehicles.removeIf( vehicle -> (vehicle.getId() == id) )) {
            this.isSaved = false;
            save_tmp();
            return true;
        }

        return false;
    }
   
    public void clear () {
        vehicles.clear();
        this.isSaved = false;
        save_tmp();
    }

    public boolean remove_at (int index) {
        try {
            vehicles.remove(index);
            this.isSaved = false;
            save_tmp();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
    public boolean remove_lower (Vehicle element) {
        if (vehicles.removeIf( vehicle -> (vehicle.getEnginePower() < element.getEnginePower()) ) ) {
            this.isSaved = false;
            save_tmp();
            return true;
        }
        return false;
    }

    public void sort () {
        Collections.sort(vehicles, new SortByEnginePower());
        this.isSaved = false;
        save_tmp();
    }

    public String filter_starts_with_name (String name) {
        return vehicles.stream()
            .filter(vehicle -> vehicle.getName().startsWith(name))
            .map(vehicle -> vehicle.print())
            .collect(Collectors.joining("\n"));
    }
     
    public String print_unique_fuel_type () {
        return Arrays.stream(FuelType.values())
            .filter( val -> vehicles.stream().filter(vehicle -> vehicle.getFuelType() == val).count() == 1 )
            .map( val -> val.toString())
            .collect(Collectors.joining("\n"));
    }

    public String print_field_descending_type () {
        return vehicles.stream()
            .map(vehicle -> vehicle.getType())
            .filter(val -> val != null)
            /*.sorted(new Comparator<VehicleType>() {
                public int compare(VehicleType a, VehicleType b) {
                    return b.ordinal() - a.ordinal();
                }
            })*/
            .sorted(Comparator.reverseOrder())
            .map( val -> val.toString())
            .collect(Collectors.joining("\n"));
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
    public boolean save () {
        if(save_elements(this.fileName)) {
            this.isSaved = true;
            return true;
        }
        return false;
    }

    /** 
     * Сохраняет коллекцию во временный файл
     */   
    public void save_tmp () {
        save_elements(this.tmpFileName);
    }
}
