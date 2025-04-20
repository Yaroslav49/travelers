package ru.rsreu.travelers.datalayer;

import ru.rsreu.travelers.datalayer.data.Locality;

import java.util.List;

/**
 * Interface for Data Access Object that manages locality operations.
 */
public interface LocalityDAO {

    /**
     * Retrieves a list of all localities.
     *
     * @return a List of Locality objects
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<Locality> getLocalities() throws DataBaseException;

    /**
     * Retrieves a locality by its unique identifier.
     *
     * @param localityId the unique identifier of the locality
     * @return the Locality object corresponding to the given id
     * @throws DataBaseException if there is an error accessing the database
     */
    public Locality getLocality(int localityId) throws DataBaseException;

    /**
     * Creates a new locality with the specified name and type.
     *
     * @param name the name of the new locality
     * @param type the type of the new locality
     * @return true if the locality was successfully created, false otherwise
     * @throws DataBaseException if there is an error accessing the database
     */
    public boolean createLocality(String name, int type) throws DataBaseException;

    /**
     * Checks whether the current locality is being used for trips based on its unique identifier.
     *
     * @param localityId the unique identifier of the locality to check
     * @return true if the locality is in use, false otherwise
     * @throws DataBaseException if there is an error accessing the database
     */
    public boolean localityUsed(int localityId) throws DataBaseException;

    /**
     * Edits an existing locality's details by its unique identifier.
     *
     * @param localityId the unique identifier of the locality to edit
     * @param name the new name for the locality
     * @param type the new type for the locality
     * @throws DataBaseException if there is an error accessing the database
     */
    public void editLocality(int localityId, String name, int type) throws DataBaseException;

    /**
     * Deletes a locality by its unique identifier.
     *
     * @param localityId the unique identifier of the locality to delete
     * @throws DataBaseException if there is an error accessing the database
     */
    public void deleteLocality(int localityId) throws DataBaseException;
}
