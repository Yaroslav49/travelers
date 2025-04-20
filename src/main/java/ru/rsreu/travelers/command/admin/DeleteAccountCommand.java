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

public class DeleteAccountCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        boolean errorDeletingSelf = false;
        boolean errorDeletingOther = false;
        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            HttpSession session = request.getSession();
            int myAccountId = (int) session.getAttribute("accountId");
            if (myAccountId == accountId) {
                errorDeletingSelf = true;
            } else {
                errorDeletingOther = !AccountLogic.deleteAccount(accountId);
            }

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

        String pagePath;
        String currentPage = (String) request.getParameter("previousPage");
        if ("authorized_accounts".equals(currentPage)) {
            pagePath = resourcer.getString("path.controller.showAuthorizedAccounts");
        } else {
            pagePath = resourcer.getString("path.controller.showAccounts");
        }

        if (errorDeletingOther) {
            String error = resourcer.getString("error.deleteAccount");
            pagePath = CommonLogic.addParameterToUrl(pagePath, error);
        } else if (errorDeletingSelf) {
            String error = resourcer.getString("error.deleteSelfAccount");
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
