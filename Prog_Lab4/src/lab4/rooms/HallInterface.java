package lab4.rooms;

import lab4.descriptions.HallType;

public interface HallInterface extends FloorInterface, WallInterface, WindowInterface {
    public HallType getType();
}
