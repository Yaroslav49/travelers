package ru.rsreu.travelers.datalayer;

import ru.rsreu.travelers.datalayer.data.UserGroup;

import java.util.List;

/**
 * Interface for Data Access Object that manages operations related to user groups.
 */
public interface UserGroupDAO {

    /**
     * Retrieves a list of all user groups.
     *
     * @return a List of UserGroup objects representing all user groups
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<UserGroup> getUserGroups() throws DataBaseException;
}
