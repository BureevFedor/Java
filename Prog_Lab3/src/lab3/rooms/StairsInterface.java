package lab3.rooms;

import lab3.descriptions.StairsType;

public interface StairsInterface extends WallInterface, WindowInterface {
    public StairsType getType();
}
