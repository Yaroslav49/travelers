package ru.rsreu.travelers.command.driver;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.data.ListTrip;
import ru.rsreu.travelers.logic.TripLogic;

import java.util.List;
import java.util.Objects;

public class OpenDriverTripsCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.openTrips");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        HttpSession session = request.getSession();
        int accountId = (int) session.getAttribute("accountId");

        List<ListTrip> trips = TripLogic.getDriverTrips(accountId);

        String error = request.getParameter("error");
        System.out.printf("error = '%s'\n", error);
        if (Objects.equals(error, "create_trip")) {
            request.setAttribute("errorMessage", resourcer.getString("message.createNotUniqueTrip"));
        }

        request.setAttribute("trips", trips);
        request.setAttribute("currentPage", "driver_my_trips");
        return page;
    }

    @Override
    public int getGroupId() {
        return 3;
    }
}
