package ru.rsreu.travelers.command.moderator;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.logic.AccountLogic;
import ru.rsreu.travelers.logic.CommonLogic;
import ru.rsreu.travelers.logic.TripLogic;

public class EditTripCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private static final int MAX_LENGTH_COLLECTION_POINT = 126;
    private static final int MAX_LENGTH_DESCRIPTION = 255;

    @Override
    public Page execute(HttpServletRequest request) {
        int tripId = -1;
        try {
            String collectionPoint = CommonLogic.trimToLength(request.getParameter("collectionPoint"), MAX_LENGTH_COLLECTION_POINT);
            String description = CommonLogic.trimToLength(request.getParameter("description"), MAX_LENGTH_DESCRIPTION);
            tripId = Integer.parseInt(request.getParameter("trip_id"));
            TripLogic.editTrip(tripId, collectionPoint, description);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

        String pagePath = resourcer.getString("path.controller.openModeratorTrip");
        pagePath = String.format(pagePath, tripId);
        Page page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);

        return page;
    }

    @Override
    public int getGroupId() {
        return 2;
    }
}
