package ru.rsreu.travelers.datalayer.data;

/**
 * Represents a type of locality with an identifier and a name.
 */
public class TypeLocality {
    private int id;
    private String name;

    public TypeLocality(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
