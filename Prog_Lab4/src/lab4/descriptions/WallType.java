package lab4.descriptions;

import java.util.Random;

public enum WallType {
    BASRELIEF,
    WALLPAPER,
    BARE_WALLS,
    PAINTED,
    WOODEN;

    public String description() {
        switch(this) {
            case BASRELIEF: 
                return "Четкие барельефы с поражавшими воображение резными портретами, шли широкой полосой по стенам зала, отделённые друг от друга традиционным точечным орнаментом.";
            case WALLPAPER: 
                return "Выцветшие от времени обои скучно свисали пыльными лоскутами с ободранных стен.";
            case BARE_WALLS: 
                return "На голых стенах не осталось почти ничего кроме практически обвалившейся штукатурки.";
            case PAINTED: 
                return "Узоры практически выцветшей краски, когда-то давно бывшие многоцветным восточным орнаментом, покрывали стены от пола до потолка.";
            case WOODEN: 
                return "Частично прогнившие деревянные панели на стенах местами разошлись, открывая неприглядного вида каменную кладку.";
            default:
                return "";
        }
    }

    public static WallType getRandom() {
        Random ran = new Random();
        return WallType.values()[ran.nextInt(WallType.values().length)];
    }
}