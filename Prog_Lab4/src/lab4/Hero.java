package lab4;

import lab4.rooms.PrintInterface;

public class Hero implements PrintInterface {

    private String name;
    private boolean hasRope;
    private boolean hasLight;
    private int id;

    public Hero(String name, boolean hasRope, boolean hasLight, int id) {
        this.name = name;
        this.hasRope = hasRope;
        this.hasLight = hasLight;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean hasRope() {
        return hasRope;
    }

    public boolean hasLight() {
        return hasLight;
    }

    public void print() {
        System.out.println("Имя: " + name);
        System.out.println("Вещи:" + (hasRope ? " Верёвка" : "") + (hasLight ? " Фонарик" : ""));
        System.out.println("Помещение: " + id);
    }

    @Override
    public String toString() {
        return name + " [" + (hasRope ? " Верёвка" : "") + (hasLight ? " Фонарик" : "") + "]" + "Положение: " + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Hero hero = (Hero) obj;
        return name.equals(hero.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

