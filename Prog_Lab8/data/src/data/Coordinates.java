package data;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private Float x;
    private long y;

    public Coordinates(Float x, long y)
    {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return this.x;
    }

    public long getY() {
        return this.y;
    }
}