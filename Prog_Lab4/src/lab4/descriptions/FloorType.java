package lab4.descriptions;

import java.util.Random;

public enum FloorType {
    STONE,
    WOOD, 
    GLASS, 
    GROUND, 
    METAL, 
    CERAMIC;
    
    public String description() {
        switch(this) {
            case STONE: 
                return "Каменный пол был усыпан кусками штукатурки, отвалившейся от потолка.";
            case WOOD: 
                return "Старые пыльные половицы отчаянно скрипели под ногами.";
            case GLASS: 
                return "Сквозь мутный стеклянный пол было очень трудно разглядеть, что находилось вниз.";
            case GROUND: 
                return "Сквозь земляной пол, местами покрытый мхом, пробивались бледные ростки.";
            case METAL: 
                return "Металлический настил гулко звенел под ногами.";
            case CERAMIC: 
                return "Плитки, покрывавшие пол, были в удручающем состоянии.";
            default:
                return "";
        }
    }

    public static FloorType getRandom() {
        Random ran = new Random();
        return FloorType.values()[ran.nextInt(FloorType.values().length)];
    }
}