package ru.rsreu.travelers.command.admin;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.datalayer.data.UserGroup;
import ru.rsreu.travelers.logic.AccountLogic;

import java.util.ArrayList;
import java.util.List;

public class ShowEditingAccountCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.creatingAccount");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);

        Account account;
        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            account = AccountLogic.getAccount(accountId);
        } catch (NumberFormatException exception) {
            account = Account.NULL_ACCOUNT;
            System.out.println(exception.getMessage());
        }
        List<UserGroup> userGroups = AccountLogic.getUserGroups();
        request.setAttribute("currentPage", "editing_account");
        request.setAttribute("previousPage", request.getParameter("previousPage"));
        System.out.println("1. " + request.getParameter("previousPage"));
        request.setAttribute("account", account);
        request.setAttribute("userGroups", userGroups);
        return page;
    }

    @Override
    public int getGroupId() {
        return 1;
    }
}
