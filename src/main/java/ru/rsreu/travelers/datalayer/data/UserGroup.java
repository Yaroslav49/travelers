package ru.rsreu.travelers.datalayer.data;

/**
 * Represents a user group with an identifier and a name.
 */
public class UserGroup {
    private int id;
    private String name;

    public UserGroup(int id, String name) {
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
