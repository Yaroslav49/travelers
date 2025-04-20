package ru.rsreu.travelers.command.admin;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.data.UserGroup;
import ru.rsreu.travelers.logic.AccountLogic;

import java.util.ArrayList;
import java.util.List;

public class ShowCreatingAccountCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.creatingAccount");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        List<UserGroup> userGroups = AccountLogic.getUserGroups();
        request.setAttribute("currentPage", "creating_account");
        request.setAttribute("userGroups", userGroups);
        return page;
    }

    @Override
    public int getGroupId() {
        return 1;
    }
}
