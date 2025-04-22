package lab3.rooms;

import lab3.descriptions.GalleryType;

public interface GalleryInterface extends FloorInterface, WallInterface {
    public GalleryType getType();
}
