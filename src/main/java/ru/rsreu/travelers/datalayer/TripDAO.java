package ru.rsreu.travelers.datalayer;

import ru.rsreu.travelers.datalayer.data.RawTrip;

import java.util.List;

/**
 * Interface for Data Access Object that manages trip operations.
 */
public interface TripDAO {

    /**
     * Retrieves a list of all open trips.
     *
     * @return a List of Trip objects that are currently open
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Trip> getAllOpenTrips() throws DataBaseException;

    /**
     * Retrieves a list of active trips for a specific passenger by their account ID.
     *
     * @param accountId the unique identifier of the passenger's account
     * @return a List of Trip objects that are active for the specified passenger
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Trip> getPassengerActiveTrips(int accountId) throws DataBaseException;

    /**
     * Retrieves a list of inactive trips for a specific passenger by their account ID.
     *
     * @param accountId the unique identifier of the passenger's account
     * @return a List of Trip objects that are inactive for the specified passenger
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Trip> getPassengerInactiveTrips(int accountId) throws DataBaseException;

    /**
     * Retrieves a list of all active trips.
     *
     * @return a List of Trip objects that are currently active
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Trip> getActiveTrips() throws DataBaseException;

    /**
     * Retrieves a list of all closed trips.
     *
     * @return a List of Trip objects that are closed
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Trip> getClosedTrips() throws DataBaseException;

    /**
     * Retrieves a list of all canceled trips.
     *
     * @return a List of Trip objects that are canceled
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Trip> getCanceledTrips() throws DataBaseException;

    /**
     * Retrieves a list of all deleted trips.
     *
     * @return a List of Trip objects that are deleted
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Trip> getDeletedTrips() throws DataBaseException;

    /**
     * Retrieves a list of active trips for a specific driver by their account ID.
     *
     * @param accountId the unique identifier of the driver's account
     * @return a List of Trip objects that are active for the specified driver
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Trip> getDriverActiveTrips(int accountId) throws DataBaseException;

    /**
     * Retrieves a list of inactive trips for a specific driver by their account ID.
     *
     * @param accountId the unique identifier of the driver's account
     * @return a List of Trip objects that are inactive for the specified driver
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Trip> getDriverInactiveTrips(int accountId) throws DataBaseException;

    /**
     * Retrieves a specific trip for a passenger by trip ID.
     *
     * @param tripId the unique identifier of the trip
     * @return the Trip object associated with the specified trip ID
     * @throws DataBaseException if there is an error accessing the database
     */
    public Trip getPassengerTrip(int tripId) throws DataBaseException;

    /**
     * Checks if a specific driver has any trips.
     *
     * @param driverId the unique identifier of the driver
     * @return true if the driver has trips, false otherwise
     * @throws DataBaseException if there is an error accessing the database
     */
    public boolean driverHasTrips(int driverId) throws DataBaseException;

    /**
     * Checks whether the specified trip can be closed now (the driver cannot close a trip whose date has not yet passed)
     *
     * @param tripId the unique identifier of the trip
     * @return true if the trip can be closed, false otherwise
     * @throws DataBaseException if there is an error accessing the database
     */
    public boolean checkDateForClose(int tripId) throws DataBaseException;

    /**
     * Refuses a trip for a specific account and trip ID.
     *
     * @param accountId the unique identifier of the account refusing the trip
     * @param tripId the unique identifier of the trip being refused
     * @throws DataBaseException if there is an error accessing the database
     */
    public void refusalTrip(int accountId, int tripId) throws DataBaseException;

    /**
     * Updates the number of passengers after a refusal for a specific trip.
     *
     * @param accountId the unique identifier of the account that refused the trip
     * @param tripId the unique identifier of the trip being updated
     * @throws DataBaseException if there is an error accessing the database
     */
    public void updateNumberPassengersAfterRefusal(int accountId, int tripId) throws DataBaseException;

    /**
     * Updates the number of passengers after approval for a specific trip.
     *
     * @param tripId the unique identifier of the trip being updated
     * @throws DataBaseException if there is an error accessing the database
     */
    public void updateNumberPassengersAfterApprove(int tripId) throws DataBaseException;

    /**
     * Closes a specific trip by its unique identifier.
     *
     * @param tripId the unique identifier of the trip to be closed
     * @throws DataBaseException if there is an error accessing the database
     */
    public void closeTrip(int tripId) throws DataBaseException;

    /**
     * Cancels a specific trip by its unique identifier.
     *
     * @param tripId the unique identifier of the trip to be canceled
     * @throws DataBaseException if there is an error accessing the database
     */
    public void cancelTrip(int tripId) throws DataBaseException;

    /**
     * Deletes a specific trip by its unique identifier.
     *
     * @param tripId the unique identifier of the trip to be deleted
     * @throws DataBaseException if there is an error accessing the database
     */
    public void deleteTrip(int tripId) throws DataBaseException;

    /**
     * Edits details of an existing trip.
     *
     * @param tripId        the unique identifier of the trip to be edited
     * @param collectionPoint the new collection point for the trip
     * @param description   the new description for the trip
     * @throws DataBaseException if there is an error accessing the database
     */
    public void editTrip(int tripId, String collectionPoint, String description) throws DataBaseException;

    /**
     * Adds a new trip using provided raw trip data.
     *
     * @param trip the RawTrip object containing details of the new trip to be added
     * @throws DataBaseException if there is an error accessing the database
     */
    public void addTrip(RawTrip trip) throws DataBaseException;
}

