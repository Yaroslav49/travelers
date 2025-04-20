package ru.rsreu.travelers.command.driver;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.Trip;
import ru.rsreu.travelers.datalayer.data.Locality;
import ru.rsreu.travelers.logic.LocalityLogic;

import java.util.List;

public class ShowCreatingTripCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.creatingTrip");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);

        List<Locality> localities = LocalityLogic.getLocalities();
        request.setAttribute("localities", localities);
        return page;
    }

    @Override
    public int getGroupId() {
        return 3;
    }
}
