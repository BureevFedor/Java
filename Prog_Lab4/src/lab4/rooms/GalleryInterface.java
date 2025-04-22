package lab4.rooms;

import lab4.descriptions.GalleryType;

public interface GalleryInterface extends FloorInterface, WallInterface {
    public GalleryType getType();
}
