package ru.rsreu.travelers.datalayer.oracledb;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import ru.rsreu.travelers.datalayer.DataBaseException;
import ru.rsreu.travelers.datalayer.Trip;
import ru.rsreu.travelers.datalayer.TripDAO;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.datalayer.data.RawTrip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleTripDAO implements TripDAO {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private final int NUMBER_COLUMN_IDENTIFIER = 1;
    private final int NUMBER_COLUMN_START_POINT = 2;
    private final int NUMBER_COLUMN_FINAL_POINT = 3;
    private final int NUMBER_COLUMN_DATE_TIME = 4;
    private final int NUMBER_COLUMN_NUMBER_PASSENGERS = 5;
    private final int NUMBER_COLUMN_NUMBER_SEATS = 6;
    private final int NUMBER_COLUMN_PRICE = 7;
    private final int NUMBER_COLUMN_DRIVER_NAME = 8;
    private final int NUMBER_COLUMN_DRIVER_SUM_MARKS = 9;
    private final int NUMBER_COLUMN_DRIVER_COUNT_MARKS = 10;
    private final int NUMBER_COLUMN_COLLECTION_POINT = 11;
    private final int NUMBER_COLUMN_DESCRIPTION = 12;
    private final int NUMBER_COLUMN_STATE = 13;

    private Connection connection;

    public OracleTripDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Trip> getAllOpenTrips() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.allOpenTrips");
        return getTrips(sqlQuery);
    }

    @Override
    public List<Trip> getPassengerActiveTrips(int accountId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.passengerActiveTrips");
        return getTripsByAccount(accountId, sqlQuery);
    }

    @Override
    public List<Trip> getPassengerInactiveTrips(int accountId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.passengerInactiveTrips");
        return getTripsByAccount(accountId, sqlQuery);
    }

    @Override
    public List<Trip> getActiveTrips() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.activeTrips");
        return getTrips(sqlQuery);
    }

    @Override
    public List<Trip> getClosedTrips() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.closedTrips");
        return getTrips(sqlQuery);
    }

    @Override
    public List<Trip> getCanceledTrips() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.canceledTrips");
        return getTrips(sqlQuery);
    }

    @Override
    public List<Trip> getDeletedTrips() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.deletedTrips");
        return getTrips(sqlQuery);
    }

    @Override
    public List<Trip> getDriverActiveTrips(int accountId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.driverActiveTrips");
        return getTripsByAccount(accountId, sqlQuery);
    }

    @Override
    public List<Trip> getDriverInactiveTrips(int accountId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.driverInactiveTrips");
        return getTripsByAccount(accountId, sqlQuery);
    }

    @Override
    public Trip getPassengerTrip(int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.passengerTrip");
        Trip trip;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, tripId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                trip = getTrip(result);
            } else {
                result.close();
                preparedStatement.close();
                throw new DataBaseException();
            }
            result.close();
            preparedStatement.close();
            return trip;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public boolean driverHasTrips(int driverId) throws DataBaseException {
        try {
            String sqlQuery = resourcer.getString("query.driverHasTrips");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, driverId);
            ResultSet result = preparedStatement.executeQuery();
            boolean driverHasTrips = false;
            if (result.next()) {
                driverHasTrips = true;
            }
            result.close();
            preparedStatement.close();
            return driverHasTrips;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public boolean checkDateForClose(int tripId) throws DataBaseException {
        try {
            String sqlQuery = resourcer.getString("query.checkDateForClose");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, tripId);
            ResultSet result = preparedStatement.executeQuery();
            boolean dateCorrect = false;
            if (result.next()) {
                dateCorrect = true;
            }
            result.close();
            preparedStatement.close();
            return dateCorrect;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void refusalTrip(int accountId, int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.refusalTrip");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, tripId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void updateNumberPassengersAfterRefusal(int accountId, int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.updateNumberPassengersAfterRefusal");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, tripId);
            preparedStatement.setInt(3, tripId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void updateNumberPassengersAfterApprove(int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.updateNumberPassengersAfterApprove");
        updateTrip(tripId, sqlQuery);
    }

    @Override
    public void closeTrip(int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.closeTrip");
        updateTrip(tripId, sqlQuery);
    }

    @Override
    public void cancelTrip(int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.cancelTrip");
        updateTrip(tripId, sqlQuery);
    }

    @Override
    public void deleteTrip(int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.deleteTrip");
        updateTrip(tripId, sqlQuery);
    }

    @Override
    public void editTrip(int tripId, String collectionPoint, String description) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.editTrip");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, collectionPoint);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, tripId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void addTrip(RawTrip trip) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.createTrip");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, trip.getDriverId());
            preparedStatement.setString(2, trip.getDatetime());
            preparedStatement.setInt(3, trip.getPrice());
            preparedStatement.setInt(4, trip.getNumberSeats());
            preparedStatement.setInt(5, trip.getStartPoint());
            preparedStatement.setInt(6, trip.getFinalPoint());
            preparedStatement.setString(7, trip.getCollectionPoint());
            preparedStatement.setString(8, trip.getDescription());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    private List<Trip> getTripsByAccount(int accountId, String sqlQuery) throws DataBaseException {
        List<Trip> trips = new ArrayList<Trip>();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, accountId);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Trip trip = getTrip(result);
                trips.add(trip);
            }
            result.close();
            preparedStatement.close();
            return trips;
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    private List<Trip> getTrips(String sqlQuery) throws DataBaseException {
        List<Trip> trips = new ArrayList<Trip>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            while (result.next()) {
                Trip trip = getTrip(result);
                trips.add(trip);
            }
            result.close();
            statement.close();
            return trips;
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    private Trip getTrip(ResultSet result) throws SQLException {
        int id = result.getInt(NUMBER_COLUMN_IDENTIFIER);
        String startPoint = result.getString(NUMBER_COLUMN_START_POINT);
        String finalPoint = result.getString(NUMBER_COLUMN_FINAL_POINT);
        String dateTime = result.getString(NUMBER_COLUMN_DATE_TIME);
        int numberPassengers = result.getInt(NUMBER_COLUMN_NUMBER_PASSENGERS);
        int numberSeats = result.getInt(NUMBER_COLUMN_NUMBER_SEATS);
        int price = result.getInt(NUMBER_COLUMN_PRICE);
        String driverName = result.getString(NUMBER_COLUMN_DRIVER_NAME);
        int driverSumMarks = result.getInt(NUMBER_COLUMN_DRIVER_SUM_MARKS);
        int driverCountMarks = result.getInt(NUMBER_COLUMN_DRIVER_COUNT_MARKS);
        String collectionPoint = result.getString(NUMBER_COLUMN_COLLECTION_POINT);
        collectionPoint = collectionPoint == null ? "" : collectionPoint;
        String description = result.getString(NUMBER_COLUMN_DESCRIPTION);
        description = description == null ? "" : description;
        int state = result.getInt(NUMBER_COLUMN_STATE);
        return new Trip(id, startPoint, finalPoint, dateTime, numberPassengers, numberSeats,
                price, driverName, driverSumMarks, driverCountMarks, state, collectionPoint, description);
    }

    private void updateTrip(int tripId, String sqlQuery) throws DataBaseException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, tripId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }
}
