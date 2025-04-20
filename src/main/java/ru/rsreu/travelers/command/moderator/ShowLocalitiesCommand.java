package ru.rsreu.travelers.command.moderator;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.data.Locality;
import ru.rsreu.travelers.datalayer.data.TypeLocality;
import ru.rsreu.travelers.datalayer.data.User;
import ru.rsreu.travelers.logic.LocalityLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowLocalitiesCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.showLocalities");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        List<TypeLocality> typeLocalities = LocalityLogic.getTypesLocalities();
        List<Locality> localities = LocalityLogic.getLocalities();

        String error = request.getParameter("error");
        System.out.printf("error = '%s'\n", error);
        if (Objects.equals(error, "create_locality")) {
            request.setAttribute("errorMessage", resourcer.getString("message.createLocality"));
        } else if (Objects.equals(error, "edit_locality")) {
            request.setAttribute("errorMessage", resourcer.getString("message.editLocality"));
        } else if (Objects.equals(error, "delete_locality")) {
            request.setAttribute("errorMessage", resourcer.getString("message.deleteLocality"));
        }

        request.setAttribute("typeLocalities", typeLocalities);
        request.setAttribute("localities", localities);
        return page;
    }

    @Override
    public int getGroupId() {
        return 2;
    }
}
