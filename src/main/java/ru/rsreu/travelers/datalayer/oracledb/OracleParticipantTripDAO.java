package ru.rsreu.travelers.datalayer.oracledb;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import ru.rsreu.travelers.datalayer.DataBaseException;
import ru.rsreu.travelers.datalayer.ParticipantTripDAO;
import ru.rsreu.travelers.datalayer.data.ParticipantTrip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleParticipantTripDAO implements ParticipantTripDAO {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private final int NUMBER_COLUMN_IDENTIFIER = 1;
    private final int NUMBER_COLUMN_NAME = 2;
    private final int NUMBER_COLUMN_SUM_MARKS = 3;
    private final int NUMBER_COLUMN_COUNT_MARKS = 4;
    private final int NUMBER_COLUMN_DRIVER_APPROVE = 5;
    private final int NUMBER_COLUMN_PASSENGER_REFUSAL = 6;
    private final int NUMBER_COLUMN_DRIVER_MARK = 7;
    private final int NUMBER_COLUMN_PASSENGER_MARK = 8;
    private final int NUMBER_COLUMN_IS_BLOCKED = 9;

    private Connection connection;

    public OracleParticipantTripDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ParticipantTrip> getParticipants(int tripId) throws DataBaseException {
        List<ParticipantTrip> participants = new ArrayList<ParticipantTrip>();
        String sqlQuery = resourcer.getString("query.participantsTrip");
        return getParticipantsByTrip(tripId, sqlQuery, false);
    }

    @Override
    public List<ParticipantTrip> getParticipantsForModerator(int tripId) throws DataBaseException {
        List<ParticipantTrip> participants = new ArrayList<ParticipantTrip>();
        String sqlQuery = resourcer.getString("query.participantsTripForModerator");
        return getParticipantsByTrip(tripId, sqlQuery, true);
    }

    @Override
    public ParticipantTrip participantInTrip(int participantId, int tripId) throws DataBaseException {
        try {
            String sqlQuery = resourcer.getString("query.participantInTrip");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, participantId);
            preparedStatement.setInt(2, tripId);
            System.out.printf("participantId = %d, tripId = %d\n", participantId, tripId);
            ResultSet result = preparedStatement.executeQuery();
            ParticipantTrip participantTrip;
            if (result.next()) {
                participantTrip = getParticipant(result);
            } else {
                participantTrip = ParticipantTrip.NULL_PARTICIPANT_TRIP;
            }
            result.close();
            preparedStatement.close();
            return participantTrip;
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public boolean passengerParticipateInTrips(int participantId) throws DataBaseException {
        try {
            String sqlQuery = resourcer.getString("query.passengerParticipateInTrips");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, participantId);
            ResultSet result = preparedStatement.executeQuery();
            boolean passengerParticipateInTrips = false;
            if (result.next()) {
                passengerParticipateInTrips = true;
            }
            result.close();
            preparedStatement.close();
            return passengerParticipateInTrips;
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public boolean checkUniqueDateParticipantTrip(int participantId, int tripId) throws DataBaseException {
        try {
            String sqlQuery = resourcer.getString("query.uniqueBook");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, participantId);
            preparedStatement.setInt(2, tripId);
            ResultSet result = preparedStatement.executeQuery();
            boolean unique = true;
            if (result.next()) {
                unique = false;
            }
            result.close();
            preparedStatement.close();
            return unique;
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void createParticipantTrip(int participantId, int tripId) throws DataBaseException {
        try {
            String sqlQuery = resourcer.getString("query.createParticipantId");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, participantId);
            preparedStatement.setInt(2, tripId);
            boolean isExecute = preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void setDriverApprove(int participantId, int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.driverApprove");
        changeDriverApprove(participantId, tripId, sqlQuery);
    }

    @Override
    public void setDriverDisApprove(int participantId, int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.driverDisapprove");
        changeDriverApprove(participantId, tripId, sqlQuery);
    }

    @Override
    public void setPassengerMark(int mark, int participantId, int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.setPassengerMark");
        setMark(sqlQuery, mark, participantId, tripId);
    }

    @Override
    public void setDriverMark(int mark, int participantId, int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.setDriverMark");
        setMark(sqlQuery, mark, participantId, tripId);
    }

    private List<ParticipantTrip> getParticipantsByTrip(int tripId, String sqlQuery, boolean isModeratorRequest) throws DataBaseException {
        List<ParticipantTrip> participants = new ArrayList<ParticipantTrip>();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, tripId);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                ParticipantTrip participant = isModeratorRequest ? getParticipantForModerator(result) : getParticipant(result);
                participants.add(participant);
            }
            result.close();
            preparedStatement.close();
            return participants;
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    private ParticipantTrip getParticipant(ResultSet result) throws SQLException {
        int id = result.getInt(NUMBER_COLUMN_IDENTIFIER);
        String name = result.getString(NUMBER_COLUMN_NAME );
        int sumMarks = result.getInt(NUMBER_COLUMN_SUM_MARKS);
        int countMarks = result.getInt(NUMBER_COLUMN_COUNT_MARKS);
        int driverApprove = result.getInt(NUMBER_COLUMN_DRIVER_APPROVE);
        boolean passengerRefusal = result.getInt(NUMBER_COLUMN_PASSENGER_REFUSAL) > 0;
        int driverMark = result.getInt(NUMBER_COLUMN_DRIVER_MARK);
        int passengerMark = result.getInt(NUMBER_COLUMN_PASSENGER_MARK);
        return new ParticipantTrip(id, name, sumMarks, countMarks, driverApprove, passengerRefusal, passengerMark, driverMark);
    }

    private ParticipantTrip getParticipantForModerator(ResultSet result) throws SQLException {
        ParticipantTrip participant = getParticipant(result);
        participant.setIsBlocked(result.getInt(NUMBER_COLUMN_IS_BLOCKED) > 0);
        return participant;
    }

    private void changeDriverApprove(int participantId, int tripId, String sqlQuery) throws DataBaseException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, participantId);
            preparedStatement.setInt(2, tripId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    private void setMark(String sqlQuery, int mark, int participantId, int tripId) throws DataBaseException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, mark);
            preparedStatement.setInt(2, participantId);
            preparedStatement.setInt(3, tripId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }
}
