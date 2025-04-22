package lab3.rooms;

import lab3.descriptions.HallType;

public interface HallInterface extends FloorInterface, WallInterface, WindowInterface {
    public HallType getType();
}
