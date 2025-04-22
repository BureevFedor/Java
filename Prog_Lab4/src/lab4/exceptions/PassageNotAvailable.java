package lab4.exceptions;

public class PassageNotAvailable extends Exception {

    private int passageId;
    private int roomId;
    public PassageNotAvailable(int passageId, int roomId) {
        this.passageId = passageId;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return ("Проход " + passageId + " не доступен в помещении " + roomId + ".");
    }
}
