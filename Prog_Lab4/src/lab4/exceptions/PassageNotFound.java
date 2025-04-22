package lab4.exceptions;

public class PassageNotFound extends RuntimeException {

    private int id;
    public PassageNotFound(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ("Проход " + id + " не существует.");
    }
}
