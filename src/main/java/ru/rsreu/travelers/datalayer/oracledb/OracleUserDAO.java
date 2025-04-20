package ru.rsreu.travelers.datalayer.oracledb;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import ru.rsreu.travelers.datalayer.DataBaseException;
import ru.rsreu.travelers.datalayer.UserDAO;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.datalayer.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleUserDAO implements UserDAO {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    private final int NUMBER_COLUMN_IDENTIFIER = 1;
    private final int NUMBER_COLUMN_NAME = 2;
    private final int NUMBER_COLUMN_SUM_MARKS = 3;
    private final int NUMBER_COLUMN_COUNT_MARKS = 4;
    private final int NUMBER_COLUMN_GROUP_ID = 5;
    private final int NUMBER_COLUMN_IS_BLOCKED = 6;
    private Connection connection;

    public OracleUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> getUsers() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.getUsers");
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            while (result.next()) {
                User user = getUser(result);
                users.add(user);
            }
            result.close();
            statement.close();
            return users;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public User getDriver(int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.getDriver");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, tripId);
            ResultSet result = preparedStatement.executeQuery();
            User account;
            if (result.next()) {
                account = getUser(result);
            } else {
                account =  User.NULL_USER;
            }
            result.close();
            preparedStatement.close();
            return account;
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void addUser(int id, String name) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.createUser");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void deleteUser(int id) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.deleteUser");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void updateNameUser(int id, String name) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.updateNameUser");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void blockUser(int id) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.blockUser");
        changeBlockedUser(id, sqlQuery);
    }

    @Override
    public void unblockUser(int id) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.unblockUser");
        changeBlockedUser(id, sqlQuery);
    }

    @Override
    public void rateUser(int mark, int userId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.rateUser");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, mark);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void rateDriver(int mark, int tripId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.rateDriver");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, mark);
            preparedStatement.setInt(2, tripId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    private User getUser(ResultSet result) throws SQLException {
        int id = result.getInt(NUMBER_COLUMN_IDENTIFIER);
        String name = result.getString(NUMBER_COLUMN_NAME);
        int sumMarks = result.getInt(NUMBER_COLUMN_SUM_MARKS);
        int countMarks = result.getInt(NUMBER_COLUMN_COUNT_MARKS);
        int groupId = result.getInt(NUMBER_COLUMN_GROUP_ID);
        boolean isBlocked = result.getInt(NUMBER_COLUMN_IS_BLOCKED) > 0;
        return new User(id, name, groupId, sumMarks, countMarks, isBlocked);
    }

    private void changeBlockedUser(int id, String sqlQuery) throws DataBaseException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }
}
