package lab3.descriptions;

import java.util.Random;

public enum BridgeType {
    LIGHT,
    SCAFFOLDING,
    METAL,
    PLANT,
    STONE;

    public String description() {
        switch(this) {
            case LIGHT:
                return "Лёгкий воздушный мостик, казалось, парил в воздухе.";
            case SCAFFOLDING: 
                return "Прочность старых строительных лесов не вызывала никакого доверия.";
            case METAL: 
                return "Тяжёлая металлическая конструкция железнодорожного моста тихо гудела.";
            case PLANT: 
                return "Здесь лианы столь густо переплетали ветви и стволы деревьев, что по ним можно было перебраться на другую сторону провала, как по мосту.";
            case STONE: 
                return "Под небольшим каменном мостиком с четырьмя статуями тихо журчал ручей.";
            default:
                return "";
        }
    }

    public static BridgeType getRandom() {
        Random ran = new Random();
        return BridgeType.values()[ran.nextInt(BridgeType.values().length)];
    }
}