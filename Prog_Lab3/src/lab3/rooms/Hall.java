package lab3.rooms;

import lab3.descriptions.HallType;
import lab3.descriptions.FloorType;
import lab3.descriptions.WallType;
import lab3.descriptions.WindowType;

public class Hall extends AbstractRoom implements HallInterface{
    private HallType type;
    private FloorType floor;
    private WallType walls;
    private WindowType windows;

    public Hall(int id, String description, HallType type, FloorType floor, WallType walls, WindowType windows) {
        super(id, description);
        this.type = type;
        this.floor = floor;
        this.walls = walls;
        this.windows = windows;
    }

    public HallType getType() {
        return type;
    }

    public FloorType getFloorType() {
        return floor;
    }

    public WallType getWallType() {
        return walls;
    }

    public WindowType getWindowType() {
        return windows;
    }

    @Override
    public void print() {
        System.out.println(getDescription());
        System.out.println(type.description());
        System.out.println(floor.description());
        System.out.println(walls.description());
        System.out.println(windows.description());
    }

    @Override
    public String toString() {
        return getDescription() + " " + type.description() + " " + floor.description() + " " + walls.description() + " " + windows.description();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Hall room = (Hall) obj;
        return getId() == room.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
