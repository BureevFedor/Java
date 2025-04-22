package lab4.rooms;

import lab4.descriptions.BridgeType;

public class Bridge extends AbstractRoom implements BridgeInterface{
    private BridgeType type;

    public Bridge(int id, String description, BridgeType type) {
        super(id, description);
        this.type = type;
    }

    public BridgeType getType() {
        return type;
    }

    @Override
    public void print() {
        System.out.println(getDescription());
        System.out.println(type.description());
    }

    @Override
    public String toString() {
        return getDescription() + " " + type.description();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Bridge room = (Bridge) obj;
        return getId() == room.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
