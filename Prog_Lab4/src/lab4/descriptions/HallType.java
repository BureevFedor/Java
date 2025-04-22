package lab4.descriptions;

import java.util.Random;

public enum HallType {
    LARGE,
    MEDIUM,
    SMALL;

    public String description() {
        switch(this) {
            case LARGE:
                return "Эта помещение было, скорее всего, главным вестибюлем или залом. От одного его размера захватывало дух.";
            case MEDIUM: 
                return "Это была просторная, но не очень хорошо сохранившаяся комната.";
            case SMALL: 
                return "Крохотная тесная комнатушка выглядела довольно неуютно.";
            default:
                return "";
        }
    }

    public static HallType getRandom() {
        Random ran = new Random();
        return HallType.values()[ran.nextInt(HallType.values().length)];
    }
}