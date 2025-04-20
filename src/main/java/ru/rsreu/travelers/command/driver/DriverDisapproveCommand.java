package ru.rsreu.travelers.command.driver;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.logic.TripLogic;

public class DriverDisapproveCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        int tripId = -1;
        try {
            int passengerId = Integer.parseInt(request.getParameter("passenger_id"));
            tripId = Integer.parseInt(request.getParameter("trip_id"));
            TripLogic.setDriverDisApprove(passengerId, tripId);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

        String pagePath = resourcer.getString("path.controller.openDriverTrip");
        pagePath = String.format(pagePath, tripId);
        Page page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);

        return page;
    }

    @Override
    public int getGroupId() {
        return 3;
    }
}
