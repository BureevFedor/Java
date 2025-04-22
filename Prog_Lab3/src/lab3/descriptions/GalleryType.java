package lab3.descriptions;

import java.util.Random;

public enum GalleryType {
    GLASS,
    WATER,
    PUDDLE,
    STATUES,
    CARPET,
    OPEN;

    public String description() {
        switch(this) {
            case GLASS:
                return "Стеклянная галерея неплохо сохранилась.";
            case WATER: 
                return "Со сводчатого потолка галереи капала ледяная вода.";
            case PUDDLE: 
                return "Глубокая ледяная лужа перегородила проход, и, чтобы не намочить ботинки, пришлось обойти её по маленькому карнизу возле стены";
            case STATUES: 
                return "Пыльные статуи в металлических доспехах на одинаковых расстояниях стояли по бокам длинного коридора.";
            case CARPET: 
                return "Остатки роскошных ковров на полу и стенах галереи напоминали о былом величии.";
            case OPEN: 
                return "Свежий ветерок гулял по открытой галерее.";
            default:
                return "";
        }
    }

    public static GalleryType getRandom() {
        Random ran = new Random();
        return GalleryType.values()[ran.nextInt(GalleryType.values().length)];
    }
}