package ru.rsreu.travelers.command.moderator;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.*;
import ru.rsreu.travelers.datalayer.data.ListTrip;
import ru.rsreu.travelers.logic.TripLogic;

import java.util.ArrayList;
import java.util.List;

public class OpenTripsModeratorCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.openTrips");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);

        List<ListTrip> trips = TripLogic.getModeratorTrips();

        request.setAttribute("trips", trips);
        request.setAttribute("currentPage", "moderator_trips");
        return page;
    }

    @Override
    public int getGroupId() {
        return 2;
    }
}
