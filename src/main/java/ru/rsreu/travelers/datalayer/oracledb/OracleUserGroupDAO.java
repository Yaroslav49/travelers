package ru.rsreu.travelers.datalayer.oracledb;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import ru.rsreu.travelers.datalayer.DataBaseException;
import ru.rsreu.travelers.datalayer.UserGroupDAO;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.datalayer.data.UserGroup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OracleUserGroupDAO implements UserGroupDAO {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private final int NUMBER_COLUMN_ACCOUNT_IDENTIFIER = 1;
    private final int NUMBER_COLUMN_NAME = 2;

    private Connection connection;

    public OracleUserGroupDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<UserGroup> getUserGroups() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.userGroups");
        List<UserGroup> groups = new ArrayList<UserGroup>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            while (result.next()) {
                UserGroup group = getUserGroup(result);
                groups.add(group);
            }
            result.close();
            statement.close();
            return groups;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    private UserGroup getUserGroup(ResultSet result) throws SQLException {
        int id = result.getInt(NUMBER_COLUMN_ACCOUNT_IDENTIFIER);
        String name = result.getString(NUMBER_COLUMN_NAME);
        return new UserGroup(id, name);
    }
}
