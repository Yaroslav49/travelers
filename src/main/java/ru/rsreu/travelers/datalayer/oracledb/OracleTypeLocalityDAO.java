package ru.rsreu.travelers.datalayer.oracledb;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import ru.rsreu.travelers.datalayer.DataBaseException;
import ru.rsreu.travelers.datalayer.TypeLocalityDAO;
import ru.rsreu.travelers.datalayer.data.Locality;
import ru.rsreu.travelers.datalayer.data.TypeLocality;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OracleTypeLocalityDAO implements TypeLocalityDAO {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private final int NUMBER_COLUMN_IDENTIFIER = 1;
    private final int NUMBER_COLUMN_NAME = 2;
    private Connection connection;

    public OracleTypeLocalityDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<TypeLocality> getTypesLocalities() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.getTypesLocalities");
        List<TypeLocality> typesLocalities = new ArrayList<TypeLocality>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            while (result.next()) {
                TypeLocality typeLocality = getTypeLocality(result);
                typesLocalities.add(typeLocality);
            }
            result.close();
            statement.close();
            return typesLocalities;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    private TypeLocality getTypeLocality(ResultSet result) throws SQLException {
        int id = result.getInt(NUMBER_COLUMN_IDENTIFIER);
        String name = result.getString(NUMBER_COLUMN_NAME);
        return new TypeLocality(id, name);
    }
}
