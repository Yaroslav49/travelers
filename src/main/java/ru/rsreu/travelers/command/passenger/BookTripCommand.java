package ru.rsreu.travelers.command.passenger;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.Trip;
import ru.rsreu.travelers.logic.CommonLogic;
import ru.rsreu.travelers.logic.TripLogic;

public class BookTripCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        int tripId = -1;
        boolean success;
        try {
            HttpSession session = request.getSession();
            int accountId = (int) session.getAttribute("accountId");
            tripId = Integer.parseInt(request.getParameter("trip_id"));
            success = TripLogic.createParticipant(accountId, tripId);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
            success = false;
        }

        String pagePath = resourcer.getString("path.controller.openTrip");
        pagePath = String.format(pagePath, tripId);

        if (!success) {
            String error = resourcer.getString("error.bookTrip");
            pagePath = CommonLogic.addParameterToUrl(pagePath, error);
        }

        Page page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);

        return page;
    }

    @Override
    public int getGroupId() {
        return 4;
    }
}
