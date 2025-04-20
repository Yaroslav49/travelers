package ru.rsreu.travelers.datalayer.oracledb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ru.rsreu.travelers.datalayer.*;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public class OracleDAOFactory extends DAOFactory {
    private static volatile OracleDAOFactory instance;
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    private Connection connection;
    private final String connectionUrl = OracleDAOFactory.resourcer.getString("connectionUrl");

    private OracleDAOFactory() {
    }

    public static OracleDAOFactory getInstance()
            throws ClassNotFoundException, SQLException {
        OracleDAOFactory factory = instance;
        if (instance == null) {
            synchronized (OracleDAOFactory.class) {
                instance = factory = new OracleDAOFactory();
                factory.connected();
            }
        }
        return factory;
    }

    private void connected() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.connection = DriverManager.getConnection(this.connectionUrl);
        if (this.connection == null) {
            System.out.println("connection is null");
        } else {
            System.out.println("connection is NOT null");
        }
    }

    @Override
    public AccountDAO getAccountDAO() {
        return new OracleAccountDAO(this.connection);
    }

    @Override
    public TripDAO getTripDAO() { return new OracleTripDAO(this.connection); }

    @Override
    public ParticipantTripDAO getParticipantTripDAO() {
        return new OracleParticipantTripDAO(this.connection);
    }

    @Override
    public UserDAO getUserDAO() { return new OracleUserDAO(this.connection); }

    @Override
    public UserGroupDAO getUserGroupDAO() { return new OracleUserGroupDAO(this.connection); }

    @Override
    public LocalityDAO getLocalityDAO() { return new OracleLocalityDAO(this.connection); }

    @Override
    public TypeLocalityDAO getTypeLocalityDAO() { return new OracleTypeLocalityDAO(this.connection); }
}
