package ru.rsreu.travelers.command.moderator;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.Trip;
import ru.rsreu.travelers.datalayer.data.ParticipantTrip;
import ru.rsreu.travelers.datalayer.data.User;
import ru.rsreu.travelers.logic.TripLogic;

import java.util.List;

public class ModeratorOpenTripCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.openTrip");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);

        int tripId;
        try {
            tripId = Integer.parseInt(request.getParameter("tripId"));
            Trip trip = TripLogic.getTrip(tripId);
            List<ParticipantTrip> passengers = TripLogic.getParticipantsForModerator(tripId);

            User driver = TripLogic.getDriver(tripId);

            request.setAttribute("trip", trip);
            request.setAttribute("passengers", passengers);
            request.setAttribute("driver", driver);
            request.setAttribute("currentPage", "moderator_trip");

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
            //ошибка
        }

        return page;
    }

    @Override
    public int getGroupId() {
        return 2;
    }
}
