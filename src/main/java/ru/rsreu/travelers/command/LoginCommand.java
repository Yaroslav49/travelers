package ru.rsreu.travelers.command;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.logic.LoginLogic;
import ru.rsreu.travelers.logic.LoginResult;

public class LoginCommand implements ActionCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = null;
        Page page = null;

        // извлечение из запроса логина и пароля
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        // проверка логина и пароля
        LoginResult result = LoginLogic.tryLogin(login, pass);
        if (result == LoginResult.OK) {
            pagePath = resourcer.getString("path.page.main");
            int groupId = LoginLogic.getCurrentAccountGroupId();
            if (groupId == 4) {
                pagePath = resourcer.getString("path.controller.openTrips");
            } else if (groupId == 3) {
                pagePath = resourcer.getString("path.controller.openDriverTrips");
            } else if (groupId == 2) {
                pagePath = resourcer.getString("path.controller.openTripsModerator");
            } else if (groupId == 1) {
                pagePath = resourcer.getString("path.controller.showAccounts");
            } else {
                pagePath = resourcer.getString("path.page.start");
            }
            page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);
            int accountId = LoginLogic.getCurrentAccountId();
            String name = LoginLogic.getCurrentAccountName();
            HttpSession session = request.getSession(true);
            Object oldAccountId = session.getAttribute("accountId");
            if (oldAccountId != null) {
                LoginLogic.resetAuthorized((int) oldAccountId);
            }

            session.setAttribute("accountId", accountId);
            session.setAttribute("login", login);
            session.setAttribute("pass", pass);
            session.setAttribute("groupId", groupId);
            session.setAttribute("name", name);
            session.setMaxInactiveInterval(300);
            LoginLogic.setAuthorized(accountId);

        } else {
            if (result == LoginResult.INCORRECT_PASSWORD) {
                pagePath = resourcer.getString("path.page.login");
                request.setAttribute("errorLoginPassMessage", resourcer.getString("message.loginError"));
            } else if (result == LoginResult.BAN) {
                pagePath = resourcer.getString("path.page.login");
                request.setAttribute("errorLoginPassMessage", resourcer.getString("message.banError"));
            } else {
                pagePath = resourcer.getString("path.page.login");
                request.setAttribute("errorLoginPassMessage", resourcer.getString("message.unknownLoginError"));
            }
            page = new Page(pagePath, TypeRedirect.FORWARD);
        }
        return page;
    }

    @Override
    public int getGroupId() {
        return 0;
    }
}
