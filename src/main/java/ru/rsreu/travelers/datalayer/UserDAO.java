package ru.rsreu.travelers.datalayer;

import ru.rsreu.travelers.datalayer.data.User;

import java.util.List;

import java.util.List;

/**
 * Interface for Data Access Object that manages operations related to users.
 */
public interface UserDAO {

    /**
     * Retrieves a list of all users.
     *
     * @return a List of User objects representing all users
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<User> getUsers() throws DataBaseException;

    /**
     * Retrieves the driver associated with a specific trip.
     *
     * @param tripId the ID of the trip for which to retrieve the driver
     * @return the User object representing the driver for the specified trip
     * @throws DataBaseException if there is an error accessing the database
     */
    public User getDriver(int tripId) throws DataBaseException;

    /**
     * Adds a new user with the specified ID and name.
     *
     * @param id   the unique identifier for the new user
     * @param name the name of the new user
     * @throws DataBaseException if there is an error accessing the database
     */
    public void addUser(int id, String name) throws DataBaseException;

    /**
     * Deletes a user with the specified ID.
     *
     * @param id the unique identifier of the user to be deleted
     * @throws DataBaseException if there is an error accessing the database
     */
    public void deleteUser(int id) throws DataBaseException;

    /**
     * Updates the name of a user with the specified ID.
     *
     * @param id   the unique identifier of the user whose name is to be updated
     * @param name the new name for the user
     * @throws DataBaseException if there is an error accessing the database
     */
    public void updateNameUser(int id, String name) throws DataBaseException;

    /**
     * Blocks a user with the specified ID.
     *
     * @param id the unique identifier of the user to be blocked
     * @throws DataBaseException if there is an error accessing the database
     */
    public void blockUser(int id) throws DataBaseException;

    /**
     * Unblocks a user with the specified ID.
     *
     * @param id the unique identifier of the user to be unblocked
     * @throws DataBaseException if there is an error accessing the database
     */
    public void unblockUser(int id) throws DataBaseException;

    /**
     * Rates a user with a specified mark.
     *
     * @param mark   the rating value to assign to the user
     * @param userId the unique identifier of the user to be rated
     * @throws DataBaseException if there is an error accessing the database
     */
    public void rateUser(int mark, int userId) throws DataBaseException;

    /**
     * Rates a driver associated with a specific trip.
     *
     * @param mark   the rating value to assign to the driver
     * @param tripId the ID of the trip associated with the driver to be rated
     * @throws DataBaseException if there is an error accessing the database
     */
    public void rateDriver(int mark, int tripId) throws DataBaseException;
}

