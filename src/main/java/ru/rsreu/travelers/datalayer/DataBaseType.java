package ru.rsreu.travelers.datalayer;

import java.sql.SQLException;

import ru.rsreu.travelers.datalayer.oracledb.OracleDAOFactory;

public enum DataBaseType {
	ORACLE {
		@Override
		public DAOFactory getDAOFactory() {
			DAOFactory oracleDBDAOFactory = null;
			try {
				oracleDBDAOFactory = OracleDAOFactory.getInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return oracleDBDAOFactory;
		}
	};

	public abstract DAOFactory getDAOFactory();
}
