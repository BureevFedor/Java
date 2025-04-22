package lab3.descriptions;

import java.util.Random;

public enum WindowType {
    STAINED_GLASS,
    LITTLE,
    BROKEN,
    NORMAL,
    FUTURISTIC;

    public String description() {
        switch(this) {
            case STAINED_GLASS: 
                return "Прекрасный витраж с изображениями морской жизни, когда-то украшавший большое окно, изрядно выцвел и покрылся многовековой пылью.";
            case LITTLE: 
                return "Крошечное оконце на потолке практически не пропускало света.";
            case BROKEN: 
                return "Остатки оконной рамы, выбитые, казалось бы, ударом ужасающей силы, сиротливо торчали в оконном проёме.";
            case NORMAL: 
                return "Оконное стекло здесь неплохо сохранилось.";
            case FUTURISTIC: 
                return "Полупрозрачное перламутровое окно овальной формы испускало загадочный потусторонний свет.";
            default:
                return "";
        }
    }

    public static WindowType getRandom() {
        Random ran = new Random();
        return WindowType.values()[ran.nextInt(WindowType.values().length)];
    }
}