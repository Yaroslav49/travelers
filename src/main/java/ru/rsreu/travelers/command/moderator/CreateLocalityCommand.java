package ru.rsreu.travelers.command.moderator;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.logic.AccountLogic;
import ru.rsreu.travelers.logic.CommonLogic;
import ru.rsreu.travelers.logic.LocalityLogic;

public class CreateLocalityCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private static final int MAX_LENGTH = 30;

    @Override
    public Page execute(HttpServletRequest request) {
        boolean success = false;
        try {
            String name = CommonLogic.trimToLength(request.getParameter("name"), MAX_LENGTH);
            int type = Integer.parseInt(request.getParameter("type"));
            success = LocalityLogic.createLocality(name, type);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

        String pagePath = resourcer.getString("path.controller.showLocalities");
        if (!success) {
            String error = resourcer.getString("error.createLocality");
            pagePath = CommonLogic.addParameterToUrl(pagePath, error);
        }
        Page page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);

        return page;
    }

    @Override
    public int getGroupId() {
        return 2;
    }
}
