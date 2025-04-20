package ru.rsreu.travelers.command.admin;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.logic.AccountLogic;
import ru.rsreu.travelers.logic.CommonLogic;

public class CreateAccountCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private static final int MAX_LENGTH = 30;

    @Override
    public Page execute(HttpServletRequest request) {
        try {
            String login = CommonLogic.trimToLength(request.getParameter("login"), MAX_LENGTH);
            String password = CommonLogic.trimToLength(request.getParameter("password"), MAX_LENGTH);
            int groupId = Integer.parseInt(request.getParameter("group"));
            String name = CommonLogic.trimToLength(request.getParameter("name"), MAX_LENGTH);
            AccountLogic.addAccount(login, password, groupId, name);

        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

        String pagePath = resourcer.getString("path.controller.showAccounts");
        Page page = new Page(pagePath, TypeRedirect.SEND_REDIRECT);

        return page;
    }

    @Override
    public int getGroupId() {
        return 1;
    }
}
