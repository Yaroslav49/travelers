package ru.rsreu.travelers.command.driver;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.logic.CommonLogic;
import ru.rsreu.travelers.logic.TripLogic;

public class CloseTripCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        int tripId = -1;
        boolean success;
        try {
            tripId = Integer.parseInt(request.getParameter("trip_id"));
            success = TripLogic.closeTrip(tripId);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
            success = false;
        }

        String pagePath = resourcer.getString("path.controller.openDriverTrip");
        pagePath = String.format(pagePath, tripId);

        if (!success) {
            String error = resourcer.getString("error.closeTripBeforeToday");
            pagePath = CommonLogic.addParameterToUrl(pagePath, error);
        }

        Page page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);

        return page;
    }

    @Override
    public int getGroupId() {
        return 3;
    }
}
