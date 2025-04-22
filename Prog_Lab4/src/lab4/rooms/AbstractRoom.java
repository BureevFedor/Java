package lab4.rooms;

public abstract class AbstractRoom implements PrintInterface {
    private int id;
    private String description;

    public AbstractRoom(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        AbstractRoom room = (AbstractRoom) obj;
        return id == room.getId();
    }

    @Override
    public int hashCode() {
        return id;
    }
}
