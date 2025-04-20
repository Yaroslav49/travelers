package ru.rsreu.travelers.command;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.logic.LoginLogic;

public class LogoutCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int accountId = (int) session.getAttribute("accountId");
        LoginLogic.resetAuthorized(accountId);
        session.invalidate();

        String pagePath = resourcer.getString("path.page.login");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        return page;
    }

    @Override
    public int getGroupId() {
        return 0;
    }
}
