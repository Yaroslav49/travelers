package ru.rsreu.travelers.datalayer;

import ru.rsreu.travelers.datalayer.data.ParticipantTrip;

import java.util.List;

/**
 * Interface for Data Access Object that manages participant trip operations.
 */
public interface ParticipantTripDAO {

    /**
     * Retrieves a list of participants for a given trip by its unique identifier.
     *
     * @param tripId the unique identifier of the trip
     * @return a List of ParticipantTrip objects associated with the specified trip
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<ParticipantTrip> getParticipants(int tripId) throws DataBaseException;

    /**
     * Retrieves a list of participants for a given trip specifically for a moderator (with information about user blocking).
     *
     * @param tripId the unique identifier of the trip
     * @return a List of ParticipantTrip objects associated with the specified trip for moderators
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<ParticipantTrip> getParticipantsForModerator(int tripId) throws DataBaseException;

    /**
     * Checks if a specific participant is in a given trip and given its information.
     *
     * @param participantId the unique identifier of the participant
     * @param tripId the unique identifier of the trip
     * @return the ParticipantTrip object if the participant is in the trip, null object otherwise
     * @throws DataBaseException if there is an error accessing the database
     */
    public ParticipantTrip participantInTrip(int participantId, int tripId) throws DataBaseException;

    /**
     * Checks if a specific participant is participating in any trips.
     *
     * @param participantId the unique identifier of the participant
     * @return true if the participant is participating in any trips, false otherwise
     * @throws DataBaseException if there is an error accessing the database
     */
    public boolean passengerParticipateInTrips(int participantId) throws DataBaseException;

    /**
     * Checks whether this passenger is already on a trip with the same date and time as the one he wants to book
     *
     * @param participantId the unique identifier of the participant
     * @param tripId the unique identifier of the trip
     * @return true if the passenger has no other trips during this trip, false otherwise
     * @throws DataBaseException if there is an error accessing the database
     */
    public boolean checkUniqueDateParticipantTrip(int participantId, int tripId) throws DataBaseException;

    /**
     * Creates a new participant-trip association.
     *
     * @param participantId the unique identifier of the participant
     * @param tripId the unique identifier of the trip
     * @throws DataBaseException if there is an error accessing the database
     */
    public void createParticipantTrip(int participantId, int tripId) throws DataBaseException;

    /**
     * Set a driver's approval for a specific participant on a trip.
     *
     * @param participantId the unique identifier of the participant
     * @param tripId the unique identifier of the trip
     * @throws DataBaseException if there is an error accessing the database
     */
    public void setDriverApprove(int participantId, int tripId) throws DataBaseException;

    /**
     * Set a driver's disapproval for a specific participant on a trip.
     *
     * @param participantId the unique identifier of the participant
     * @param tripId the unique identifier of the trip
     * @throws DataBaseException if there is an error accessing the database
     */
    public void setDriverDisApprove(int participantId, int tripId) throws DataBaseException;

    /**
     * Sets the passenger's mark for a specific trip.
     *
     * @param mark the mark to be set for the passenger
     * @param participantId the unique identifier of the participant
     * @param tripId the unique identifier of the trip
     * @throws DataBaseException if there is an error accessing the database
     */
    public void setPassengerMark(int mark, int participantId, int tripId) throws DataBaseException;

    /**
     * Sets the driver's mark for a specific trip.
     *
     * @param mark the mark to be set for the driver
     * @param participantId the unique identifier of the participant
     * @param tripId the unique identifier of the trip
     * @throws DataBaseException if there is an error accessing the database
     */
    public void setDriverMark(int mark, int participantId, int tripId) throws DataBaseException;
}
