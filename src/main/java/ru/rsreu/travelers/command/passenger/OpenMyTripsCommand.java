package ru.rsreu.travelers.command.passenger;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.*;
import ru.rsreu.travelers.datalayer.data.ListTrip;
import ru.rsreu.travelers.logic.TripLogic;

import java.util.ArrayList;
import java.util.List;

public class OpenMyTripsCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.openTrips");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        HttpSession session = request.getSession();
        int accountId = (int) session.getAttribute("accountId");

        List<ListTrip> trips = TripLogic.getPassengerTrips(accountId);

        request.setAttribute("trips", trips);
        request.setAttribute("currentPage", "passenger_my_trips");
        return page;
    }

    @Override
    public int getGroupId() {
        return 4;
    }
}