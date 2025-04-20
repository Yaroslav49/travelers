package ru.rsreu.travelers.datalayer.data;

/**
 * Represents a locality with various attributes such as an identifier, type, and name.
 */
public class Locality {
    public final static Locality NULL_LOCALITY = new Locality(-1, -1, "");
    private int id;
    private int type;
    private String name;

    public Locality(int id, int type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
