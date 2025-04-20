package ru.rsreu.travelers.datalayer.data;

/**
 * Represents a user with various attributes such as ID, name, group ID, marks, and blocked status.
 */
public class User {
    public final static User NULL_USER = new User(-1, "", -1, 0, 0, false);
    private int id;
    private String name;
    private int groupId;
    private int sumMarks;
    private int countMarks;
    private boolean isBlocked;

    public User(int id, String name, int groupId, int sumMarks, int countMarks, boolean isBlocked) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.sumMarks = sumMarks;
        this.countMarks = countMarks;
        this.isBlocked = isBlocked;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSumMarks() {
        return sumMarks;
    }

    public void setSumMarks(int sumMarks) {
        this.sumMarks = sumMarks;
    }

    public int getCountMarks() {
        return countMarks;
    }

    public void setCountMarks(int countMarks) {
        this.countMarks = countMarks;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean blocked) {
        isBlocked = blocked;
    }

}
