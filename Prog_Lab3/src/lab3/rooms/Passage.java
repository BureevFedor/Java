package lab3.rooms;

public class Passage extends AbstractRoom {
    private int id1;
    private int id2;
    private boolean needRope;
    private boolean needLight;
    private String description1;
    private String description2;
    
    public Passage(int id, String description, int id1, int id2, boolean needRope, boolean needLight, String description1, String description2) {
        super(id, description);
        this.id1 = id1;
        this.id2 = id2;
        this.needRope = needRope;
        this.needLight = needLight;
        this.description1 = description1;
        this.description2 = description2;
    }

    public boolean checkId(int id) {
        return ((id == id1) || (id == id2));
    }

    public boolean needRope() {
        return needRope;
    }

    public boolean needLight() {
        return needLight;
    }

    public String getDescription(int id) { 
        if(id == id1) {
            return description1;    
        } else if(id == id2) {
            return description2;   
        }

        return "";
    }

    public int pass(int id, boolean hasRope, boolean hasLight) {
        if(((needRope && hasRope) || !needRope) && ((needLight && hasLight) || !needLight)) {
            if(id == id1) {
                return id2;
            } else if (id == id2) {
                return id1;
            }
        } else {
            System.out.println("Невозможно пройти : " + ((needRope && !hasRope) ? " Нужна верёвка! " : "") + ((needLight && !hasLight) ? " Нужен фонарик! " : ""));
        }

        return id;
    }
    
    public void print() {
        System.out.println(getDescription());
        System.out.println(id1 + ": " + getDescription(id1));
        System.out.println(id2 + ": " + getDescription(id2));
        System.out.println((needRope ? "Нужна верёвка." : "") + (needLight ? "Нужен фонарик." : ""));
    }

    @Override
    public String toString() {
        return getDescription() + " " + id1 + ": [" + getDescription(id1) + "] " + id2 + ": [" + getDescription(id2) + "] " + (needRope ? "Нужна верёвка." : "") + (needLight ? "Нужен фонарик." : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Passage passage = (Passage) obj;
        return getId() == passage.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
