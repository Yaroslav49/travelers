package ru.rsreu.travelers.command.moderator;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.datalayer.data.Locality;
import ru.rsreu.travelers.datalayer.data.TypeLocality;
import ru.rsreu.travelers.logic.AccountLogic;
import ru.rsreu.travelers.logic.CommonLogic;
import ru.rsreu.travelers.logic.LocalityLogic;
import ru.rsreu.travelers.logic.TripLogic;

import java.util.ArrayList;
import java.util.List;

public class ShowEditingLocalityCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.creatingLocality");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        List<TypeLocality> typeLocalities = LocalityLogic.getTypesLocalities();

        Locality locality;
        try {
            int localityId = Integer.parseInt(request.getParameter("localityId"));
            locality = LocalityLogic.getLocality(localityId);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
            locality = Locality.NULL_LOCALITY;
        }

        request.setAttribute("currentPage", "editing_locality");
        request.setAttribute("locality", locality);
        request.setAttribute("typeLocalities", typeLocalities);
        return page;
    }

    @Override
    public int getGroupId() {
        return 2;
    }
}
