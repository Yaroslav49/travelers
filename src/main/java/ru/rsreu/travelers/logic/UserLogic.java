package ru.rsreu.travelers.logic;

import ru.rsreu.travelers.datalayer.*;
import ru.rsreu.travelers.datalayer.data.User;

import java.util.ArrayList;
import java.util.List;

public class UserLogic {
    private static DAOFactory factory = DAOFactory.getInstance(DataBaseType.ORACLE);
    private static UserDAO userDAO = factory.getUserDAO();
    private static ParticipantTripDAO participantTripDAO = factory.getParticipantTripDAO();

    public static List<User> getUsers() {
        try {
            return userDAO.getUsers();
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return new ArrayList<User>();
        }
    }

    public static void blockUser(int id) {
        try {
            userDAO.blockUser(id);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void unblockUser(int id) {
        try {
            userDAO.unblockUser(id);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void rateDriver(int mark, int participantId, int tripId) {
        if (mark >= 1 && mark <= 5) {
            try {
                userDAO.rateDriver(mark, tripId);
                participantTripDAO.setPassengerMark(mark, participantId, tripId);
            } catch (DataBaseException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public static void ratePassenger(int mark, int participantId, int tripId) {
        if (mark >= 1 && mark <= 5) {
            try {
                userDAO.rateUser(mark, participantId);
                participantTripDAO.setDriverMark(mark, participantId, tripId);
            } catch (DataBaseException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
