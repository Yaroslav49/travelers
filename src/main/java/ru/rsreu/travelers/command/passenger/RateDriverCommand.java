package ru.rsreu.travelers.command.passenger;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.logic.TripLogic;
import ru.rsreu.travelers.logic.UserLogic;

public class RateDriverCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        int tripId = -1;
        try {
            HttpSession session = request.getSession();
            int accountId = (int) session.getAttribute("accountId");
            tripId = Integer.parseInt(request.getParameter("trip_id"));
            int mark = Integer.parseInt(request.getParameter("mark"));
            UserLogic.rateDriver(mark, accountId, tripId);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

        String pagePath = resourcer.getString("path.controller.openTrip");
        pagePath = String.format(pagePath, tripId);
        Page page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);

        return page;
    }

    @Override
    public int getGroupId() {
        return 4;
    }
}
