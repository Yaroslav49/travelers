package ru.rsreu.travelers.datalayer;

import ru.rsreu.travelers.datalayer.data.TypeLocality;

import java.util.List;

/**
 * Interface for Data Access Object that manages operations related to types of localities.
 */
public interface TypeLocalityDAO {

    /**
     * Retrieves a list of all types of localities.
     *
     * @return a List of TypeLocality objects representing the different types of localities
     * @throws DataBaseException if there is an error accessing the database
     */
    public List<TypeLocality> getTypesLocalities() throws DataBaseException;
}
