package ru.rsreu.travelers.datalayer.oracledb;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import ru.rsreu.travelers.datalayer.DataBaseException;
import ru.rsreu.travelers.datalayer.LocalityDAO;
import ru.rsreu.travelers.datalayer.data.Locality;
import ru.rsreu.travelers.datalayer.data.UserGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleLocalityDAO implements LocalityDAO {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private final int NUMBER_COLUMN_IDENTIFIER = 1;
    private final int NUMBER_COLUMN_NAME = 2;
    private final int NUMBER_COLUMN_TYPE = 3;

    private Connection connection;

    public OracleLocalityDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Locality> getLocalities() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.getLocalities");
        List<Locality> localities = new ArrayList<Locality>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            while (result.next()) {
                Locality locality = getLocality(result);
                localities.add(locality);
            }
            result.close();
            statement.close();
            return localities;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public Locality getLocality(int localityId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.getLocality");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, localityId);
            ResultSet result = preparedStatement.executeQuery();
            Locality locality;
            if (result.next()) {
                locality = getLocality(result);
            } else {
                locality = Locality.NULL_LOCALITY;
            }
            result.close();
            preparedStatement.close();
            return locality;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public boolean createLocality(String name, int type) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.createLocality");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, type);
            boolean success = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return success;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public boolean localityUsed(int localityId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.localityIsUsed");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, localityId);
            preparedStatement.setInt(2, localityId);
            ResultSet result = preparedStatement.executeQuery();
            boolean success;
            if (result.next()) {
                success = true;
            } else {
                success = false;
            }
            result.close();
            preparedStatement.close();
            return success;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void editLocality(int localityId, String name, int type) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.editLocality");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, type);
            preparedStatement.setInt(3, localityId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void deleteLocality(int localityId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.deleteLocality");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, localityId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    private Locality getLocality(ResultSet result) throws SQLException {
        int id = result.getInt(NUMBER_COLUMN_IDENTIFIER);
        String name = result.getString(NUMBER_COLUMN_NAME);
        int type = result.getInt(NUMBER_COLUMN_TYPE);
        return new Locality(id, type, name);
    }
}
