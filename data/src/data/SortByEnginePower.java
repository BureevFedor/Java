package data;

import java.util.*;

/**
 * Класс SortByEnginePower
 * @version 1.0
 */
public class SortByEnginePower implements Comparator<Vehicle> {
    /** 
     * Возвращает результат сравнения мощности двигателей двух объектов класса Vehicle
     * @param a первый объект
     * @param b второй объект
     * @return результат сравнения
     */    
    public int compare(Vehicle a, Vehicle b){
        return a.getEnginePower() - b.getEnginePower();
    }
}