package lab3.descriptions;

import java.util.Random;

public enum StairsType {
    STRAIGHT,
    SPIRAL,
    CURVED,
    LADDER,
    RUSTY;

    public String description() {
        switch(this) {
            case STRAIGHT:
                return "Широкий пролёт лестницы с пыльными металлическими периллами с изображениями змей уходил в бесконечность.";
            case SPIRAL: 
                return "Винтовая лестница без перил круто уходила вверх и вниз.";
            case CURVED: 
                return "Большую часть помещения занимала изогнутая лестница с широкими ступенями.";
            case LADDER: 
                return "Длиннная приставная лестница уходила куда-то вверх. Вниз шла такая же лестница в противоположном углу помещения.";
            case RUSTY: 
                return "Тяжёлая проржавевшая конструкция, напоминающая мост, выглядела очень странно в этом помещении.";
            default:
                return "";
        }
    }

    public static StairsType getRandom() {
        Random ran = new Random();
        return StairsType.values()[ran.nextInt(StairsType.values().length)];
    }
}