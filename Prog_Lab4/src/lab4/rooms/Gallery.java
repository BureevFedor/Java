package lab4.rooms;

import lab4.descriptions.GalleryType;
import lab4.descriptions.WallType;
import lab4.descriptions.FloorType;

public class Gallery extends AbstractRoom implements GalleryInterface {
    private GalleryType type;
    private FloorType floor;
    private WallType walls;

    public Gallery(int id, String description, GalleryType type, FloorType floor, WallType walls) {
        super(id, description);
        this.type = type;
        this.floor = floor;
        this.walls = walls;
    }

    public GalleryType getType() {
        return type;
    }

    public FloorType getFloorType() {
        return floor;
    }

    public WallType getWallType() {
        return walls;
    }

    @Override
    public void print() {
        System.out.println(getDescription());
        System.out.println(type.description());
        System.out.println(floor.description());
        System.out.println(walls.description());
    }

    @Override
    public String toString() {
        return getDescription() + " " + type.description() + " " + floor.description() + " " + walls.description();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Gallery room = (Gallery) obj;
        return getId() == room.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
