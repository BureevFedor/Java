package lab4.exceptions;

public class RoomNotFound extends RuntimeException {

    private int id;
    public RoomNotFound(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ("Помещение " + id + " не существует.");
    }
}
