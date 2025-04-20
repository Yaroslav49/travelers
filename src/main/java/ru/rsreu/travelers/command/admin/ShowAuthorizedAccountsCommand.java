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
import java.util.Objects;

public class ShowAuthorizedAccountsCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.showAccounts");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        List<Account> accounts = AccountLogic.getAuthorizedAccounts();
        List<UserGroup> userGroups = AccountLogic.getUserGroups();

        String error = request.getParameter("error");
        System.out.printf("error = '%s'\n", error);
        if (Objects.equals(error, "delete_account")) {
            request.setAttribute("errorMessage", resourcer.getString("message.deleteAccountError"));
        } else if (Objects.equals(error, "delete_self")) {
            request.setAttribute("errorMessage", resourcer.getString("message.deleteSelfError"));
        } else if (Objects.equals(error, "edit_account")) {
            request.setAttribute("errorMessage", resourcer.getString("message.editAccountError"));
        } else if (Objects.equals(error, "edit_self")) {
            request.setAttribute("errorMessage", resourcer.getString("message.editSelfError"));
        }

        request.setAttribute("accounts", accounts);
        request.setAttribute("userGroups", userGroups);
        request.setAttribute("currentPage", "authorized_accounts");
        return page;
    }

    @Override
    public int getGroupId() {
        return 1;
    }
}
