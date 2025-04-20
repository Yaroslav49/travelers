package ru.rsreu.travelers.datalayer;

public abstract class DAOFactory {
	public static DAOFactory getInstance(DataBaseType dataBaseType) {
		DAOFactory result = dataBaseType.getDAOFactory();
		return result;
	}

	public abstract AccountDAO getAccountDAO();
	public abstract TripDAO getTripDAO();
	public abstract ParticipantTripDAO getParticipantTripDAO();
	public abstract UserDAO getUserDAO();
	public abstract UserGroupDAO getUserGroupDAO();
	public abstract LocalityDAO getLocalityDAO();
	public abstract TypeLocalityDAO getTypeLocalityDAO();
}
