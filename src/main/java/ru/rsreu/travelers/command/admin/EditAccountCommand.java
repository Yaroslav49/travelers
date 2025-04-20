package ru.rsreu.travelers.command.admin;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.logic.AccountLogic;
import ru.rsreu.travelers.logic.CommonLogic;
import ru.rsreu.travelers.logic.EditAccountResult;

public class EditAccountCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private static final int MAX_LENGTH = 30;

    @Override
    public Page execute(HttpServletRequest request) {
        EditAccountResult result;
        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            String login = CommonLogic.trimToLength(request.getParameter("login"), MAX_LENGTH);
            String password = CommonLogic.trimToLength(request.getParameter("password"), MAX_LENGTH);
            int groupId = Integer.parseInt(request.getParameter("group"));
            String name = CommonLogic.trimToLength(request.getParameter("name"), MAX_LENGTH);
            Account newAccount = new Account(accountId, login, password, groupId, false, false, name);
            HttpSession session = request.getSession();
            int myAccountId = (int) session.getAttribute("accountId");
            result = AccountLogic.editAccount(myAccountId, newAccount);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
            result = EditAccountResult.UNKNOWN_ERROR;
        }

        String pagePath;
        String currentPage = (String) request.getParameter("previousPage");
        if ("authorized_accounts".equals(currentPage)) {
            pagePath = resourcer.getString("path.controller.showAuthorizedAccounts");
        } else {
            pagePath = resourcer.getString("path.controller.showAccounts");
        }

        if (result.equals(EditAccountResult.ERROR_EDIT_SELF)) {
            String error = resourcer.getString("error.editSelfAccount");
            pagePath = CommonLogic.addParameterToUrl(pagePath, error);
        } else if (result.equals(EditAccountResult.ERROR_EDIT_OTHER)) {
            String error = resourcer.getString("error.editAccount");
            pagePath = CommonLogic.addParameterToUrl(pagePath, error);
        }

        Page page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);

        return page;
    }

    @Override
    public int getGroupId() {
        return 1;
    }
}
