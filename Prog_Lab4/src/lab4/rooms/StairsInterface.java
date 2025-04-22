package lab4.rooms;

import lab4.descriptions.StairsType;

public interface StairsInterface extends WallInterface, WindowInterface {
    public StairsType getType();
}
