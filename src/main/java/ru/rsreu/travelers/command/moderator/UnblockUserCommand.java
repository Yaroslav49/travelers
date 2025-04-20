package ru.rsreu.travelers.command.moderator;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.logic.UserLogic;

public class UnblockUserCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String tripId = null;
        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            System.out.printf("accountId = %d\n", accountId);
            UserLogic.unblockUser(accountId);
            tripId = request.getParameter("trip_id");

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

        String pagePath;
        if (tripId != null && !tripId.equals("")) {
            pagePath = resourcer.getString("path.controller.openModeratorTrip");
            int tripIdNumber;
            try {
                tripIdNumber = Integer.parseInt(tripId);
            } catch (NumberFormatException exception) {
                tripIdNumber = -1;
            }
            pagePath = String.format(pagePath, tripIdNumber);
        } else {
            pagePath = resourcer.getString("path.controller.showAccountsForModerator");
        }
        Page page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);
        return page;
    }

    @Override
    public int getGroupId() {
        return 2;
    }
}
