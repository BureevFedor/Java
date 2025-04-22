package lab4.rooms;

import java.util.Random;

public enum RoomType {
    HALL,
    GALLERY,
    STAIRS,
    BRIDGE;

    public static RoomType getRandom() {
        Random ran = new Random();
        return RoomType.values()[ran.nextInt(RoomType.values().length)];
    }
}