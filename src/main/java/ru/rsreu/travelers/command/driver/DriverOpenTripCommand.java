package ru.rsreu.travelers.command.driver;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.*;
import ru.rsreu.travelers.datalayer.data.ParticipantTrip;
import ru.rsreu.travelers.logic.TripLogic;

import java.util.List;
import java.util.Objects;

public class DriverOpenTripCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.openTrip");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        request.setAttribute("currentPage", "driver_trip");

        try {
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            Trip trip = TripLogic.getTrip(tripId);
            List<ParticipantTrip> passengers = TripLogic.getParticipants(tripId);
            HttpSession session = request.getSession();
            String name = (String) session.getAttribute("name");
            boolean isDriver;
            if (!name.equals("") && name.equals(trip.getDriverName())) {
                isDriver = true;
            } else {
                isDriver = false;
            }

            String error = request.getParameter("error");
            System.out.printf("error = '%s'\n", error);
            if (Objects.equals(error, "driver_approve")) {
                request.setAttribute("errorMessage", resourcer.getString("message.driverApproveError"));
            } else if (Objects.equals(error, "close_trip")) {
                request.setAttribute("errorMessage", resourcer.getString("message.closeTripBeforeToday"));
            }

            request.setAttribute("isDriver", isDriver);
            request.setAttribute("trip", trip);
            request.setAttribute("passengers", passengers);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
            //ошибка
        }

        return page;
    }

    @Override
    public int getGroupId() {
        return 3;
    }
}
