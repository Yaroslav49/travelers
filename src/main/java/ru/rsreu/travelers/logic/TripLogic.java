package ru.rsreu.travelers.logic;

import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.datalayer.*;
import ru.rsreu.travelers.datalayer.data.ListTrip;
import ru.rsreu.travelers.datalayer.data.ParticipantTrip;
import ru.rsreu.travelers.datalayer.data.RawTrip;
import ru.rsreu.travelers.datalayer.data.User;

import java.util.ArrayList;
import java.util.List;

public class TripLogic {
    private static DAOFactory factory = DAOFactory.getInstance(DataBaseType.ORACLE);
    private static TripDAO tripDAO = factory.getTripDAO();
    private static ParticipantTripDAO participantTripDAO = factory.getParticipantTripDAO();
    private static UserDAO userDAO = factory.getUserDAO();

    private final static String EMPTY_NAME = "";
    private final static String INACTIVE_NAME = "Неактивные поездки:";
    private final static String CLOSED_NAME = "Прошедшие поездки:";
    private final static String CANCELED_NAME = "Отменённые поездки";
    private final static String DELETED_NAME = "Удалённые поездки:";

    public static List<ListTrip> getAllOpenTrips() {
        List<ListTrip> trips = new ArrayList<ListTrip>();
        try {
            trips.add(new ListTrip("", tripDAO.getAllOpenTrips()));

        } catch (DataBaseException exception) {

        }
        return trips;
    }

    public static List<ListTrip> getPassengerTrips(int accountId) {
        List<ListTrip> trips = new ArrayList<ListTrip>();
        try {
            trips.add(new ListTrip(EMPTY_NAME, tripDAO.getPassengerActiveTrips(accountId)));
            trips.add(new ListTrip(INACTIVE_NAME, tripDAO.getPassengerInactiveTrips(accountId)));
            removeEmptyListTrip(trips);

        } catch (DataBaseException exception) {

        }
        return trips;
    }

    public static List<ListTrip> getDriverTrips(int accountId) {
        List<ListTrip> trips = new ArrayList<ListTrip>();
        try {
            trips.add(new ListTrip(EMPTY_NAME, tripDAO.getDriverActiveTrips(accountId)));
            trips.add(new ListTrip(INACTIVE_NAME, tripDAO.getDriverInactiveTrips(accountId)));
            removeEmptyListTrip(trips);

        } catch (DataBaseException exception) {

        }
        return trips;
    }

    public static List<ListTrip> getModeratorTrips() {
        List<ListTrip> trips = new ArrayList<ListTrip>();
        try {
            trips.add(new ListTrip(EMPTY_NAME, tripDAO.getActiveTrips()));
            trips.add(new ListTrip(CLOSED_NAME, tripDAO.getClosedTrips()));
            trips.add(new ListTrip(CANCELED_NAME, tripDAO.getCanceledTrips()));
            trips.add(new ListTrip(DELETED_NAME, tripDAO.getDeletedTrips()));
            removeEmptyListTrip(trips);

        } catch (DataBaseException exception) {

        }
        return trips;
    }

    public static Trip getTrip(HttpServletRequest request) {
        try {
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            Trip trip = getTripById(tripId);
            return trip;

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
            return new Trip();
        }
    }

    public static Trip getTrip(int tripId) {
        return getTripById(tripId);
    }

    public static List<ParticipantTrip> getParticipants(int tripId) {
        try {
            return participantTripDAO.getParticipants(tripId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return new ArrayList<ParticipantTrip>();
        }
    }

    public static List<ParticipantTrip> getParticipantsForModerator(int tripId) {
        try {
            return participantTripDAO.getParticipantsForModerator(tripId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return new ArrayList<ParticipantTrip>();
        }
    }

    public static ParticipantTrip getParticipantInTrip(int participantId, int tripId) {
        try {
            return participantTripDAO.participantInTrip(participantId, tripId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return ParticipantTrip.NULL_PARTICIPANT_TRIP;
        }
    }

    public static User getDriver(int tripId) {
        try {
            return userDAO.getDriver(tripId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return User.NULL_USER;
        }
    }

    public static boolean createTrip(RawTrip trip) {
        try {
            tripDAO.addTrip(trip);
            return true;
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean createParticipant(int participantId, int tripId) {
        try {
            if (checkUniqueDateParticipantTrip(participantId, tripId)) {
                participantTripDAO.createParticipantTrip(participantId, tripId);
                return true;
            }
            return false;
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean setDriverApprove(int participantId, int tripId) {
        try {
            Trip trip = getTripById(tripId);
            if (trip.getNumberSeats() > trip.getNumberPassengers()) {
                participantTripDAO.setDriverApprove(participantId, tripId);
                tripDAO.updateNumberPassengersAfterApprove(tripId);
                return true;
            }
            return false;
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    //setters

    public static void refusalTrip(int participantId, int tripId) {
        try {
            tripDAO.refusalTrip(participantId, tripId);
            tripDAO.updateNumberPassengersAfterRefusal(participantId, tripId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void setDriverDisApprove(int participantId, int tripId) {
        try {
            participantTripDAO.setDriverDisApprove(participantId, tripId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static boolean closeTrip(int tripId) {
        try {
            if (tripDAO.checkDateForClose(tripId)) {
                tripDAO.closeTrip(tripId);
                return true;
            }
            return false;
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static void cancelTrip(int tripId) {
        try {
            tripDAO.cancelTrip(tripId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void deleteTrip(int tripId) {
        try {
            tripDAO.deleteTrip(tripId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void editTrip(int tripId, String collectionPoint, String description) {
        try {
            tripDAO.editTrip(tripId, collectionPoint, description);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
        }
    }


    //private

    private static Trip getTripById(int tripId) {
        try {
            return tripDAO.getPassengerTrip(tripId);
        } catch (DataBaseException exception) {
            return new Trip();
        }
    }

    private static boolean checkUniqueDateParticipantTrip(int participantId, int tripId) {
        try {
            return participantTripDAO.checkUniqueDateParticipantTrip(participantId, tripId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    private static void removeEmptyListTrip(List<ListTrip> listTrips) {
        for (int i = listTrips.size() - 1; i >= 0; i--) {
            ListTrip listTrip = listTrips.get(i);
            if (listTrip.getData().isEmpty()) {
                listTrips.remove(i);
            }
        }
    }

}
