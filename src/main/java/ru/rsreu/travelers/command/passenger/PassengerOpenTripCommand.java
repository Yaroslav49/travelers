package ru.rsreu.travelers.command.passenger;

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

import java.util.Objects;

public class PassengerOpenTripCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.openTrip");
        String error = request.getParameter("error");
        System.out.printf("error = '%s'\n", error);
        if (Objects.equals(error, "book_trip")) {
            request.setAttribute("errorMessage", resourcer.getString("message.bookTripError"));
        }
        Page page = new Page(pagePath, TypeRedirect.FORWARD);

        Trip trip = TripLogic.getTrip(request);
        try {
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            HttpSession session = request.getSession();
            int accountId = (int) session.getAttribute("accountId");
            ParticipantTrip participantTrip = TripLogic.getParticipantInTrip(accountId, tripId);
            request.setAttribute("participantTrip", participantTrip);
        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

        String previousPage = request.getParameter("currentPage");

        request.setAttribute("trip", trip);
        request.setAttribute("currentPage", "passenger_trip");
        request.setAttribute("previousPage", previousPage);

        return page;
    }

    @Override
    public int getGroupId() {
        return 4;
    }
}
