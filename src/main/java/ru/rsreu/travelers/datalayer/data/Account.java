package ru.rsreu.travelers.datalayer.data;

/**
 * Represents an account with various attributes, such as login credentials, authorization status, and account properties.
 */
public class Account {
    public final static Account NULL_ACCOUNT = new Account(-1, "", "", 5, true, false);
    private int id;
    private String login;
    private String password;
    private int groupId;
    private boolean isBlocked;
    private boolean isAuthorized;
    private String name;

    public Account(int id, String login, String password, int groupId, boolean isBlocked, boolean isAuthorized) {
        super();
        this.id = id;
        this.login = login;
        this.password = password;
        this.groupId = groupId;
        this.isBlocked = isBlocked;
        this.isAuthorized = isAuthorized;
    }

    public Account(int id, String login, String password, int groupId, boolean isBlocked, boolean isAuthorized, String name) {
        super();
        this.id = id;
        this.login = login;
        this.password = password;
        this.groupId = groupId;
        this.isBlocked = isBlocked;
        this.isAuthorized = isAuthorized;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public boolean getIsAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
