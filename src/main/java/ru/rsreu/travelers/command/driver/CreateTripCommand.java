package ru.rsreu.travelers.command.driver;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.data.RawTrip;
import ru.rsreu.travelers.logic.CommonLogic;
import ru.rsreu.travelers.logic.LocalityLogic;
import ru.rsreu.travelers.logic.TripLogic;

public class CreateTripCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private static final int MAX_LENGTH_COLLECTION_POINT = 126;
    private static final int MAX_LENGTH_DESCRIPTION = 255;

    @Override
    public Page execute(HttpServletRequest request) {
        boolean success = false;
        try {
            HttpSession session = request.getSession();
            int driverId = (int) session.getAttribute("accountId");
            int startPoint = Integer.parseInt(request.getParameter("startPoint"));
            int finalPoint = Integer.parseInt(request.getParameter("finalPoint"));
            String datetime = request.getParameter("datetime");
            //System.out.printf("datetime before: %s\n", datetime);
            // Преобразование формата даты: "2023-10-05T14:30" -> "2023-10-05 14:30"
            datetime = datetime.replace("T", " ");
            //System.out.printf("datetime after: %s\n", datetime);
            int numberSeats = Integer.parseInt(request.getParameter("numberSeats"));
            int price = Integer.parseInt(request.getParameter("price"));
            String collectionPoint = CommonLogic.trimToLength(request.getParameter("collectionPoint"), MAX_LENGTH_COLLECTION_POINT);
            String description = CommonLogic.trimToLength(request.getParameter("description"), MAX_LENGTH_DESCRIPTION);
            success = TripLogic.createTrip(new RawTrip(driverId, datetime, price, numberSeats, startPoint, finalPoint, collectionPoint, description));

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

        String pagePath = resourcer.getString("path.controller.openDriverTrips");
        if (!success) {
            String error = resourcer.getString("error.createTrip");
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
