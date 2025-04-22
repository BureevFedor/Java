package lab3.rooms;

import lab3.descriptions.WallType;
import lab3.descriptions.WindowType;
import lab3.descriptions.StairsType;

public class Stairs extends AbstractRoom implements StairsInterface {
    private StairsType type;
    private WindowType windows;
    private WallType walls;

    public Stairs(int id, String description, StairsType type, WindowType windows, WallType walls) {
        super(id, description);
        this.type = type;
        this.windows = windows;
        this.walls = walls;
    }

    public StairsType getType() {
        return type;
    }

    public WindowType getWindowType() {
        return windows;
    }

    public WallType getWallType() {
        return walls;
    }

    @Override
    public void print() {
        System.out.println(getDescription());
        System.out.println(type.description());
        System.out.println(walls.description());
        System.out.println(windows.description());
    }

    @Override
    public String toString() {
        return getDescription() + " " + type.description() + " " + walls.description() + " " + windows.description();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Stairs room = (Stairs) obj;
        return getId() == room.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
