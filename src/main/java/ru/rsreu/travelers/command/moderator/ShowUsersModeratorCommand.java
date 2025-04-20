package ru.rsreu.travelers.command.moderator;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.datalayer.data.User;
import ru.rsreu.travelers.logic.UserLogic;

import java.util.ArrayList;
import java.util.List;

public class ShowUsersModeratorCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.showUsers");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        List<User> users = UserLogic.getUsers();

                /*new ArrayList<>();
        users.add(new User(1, "Иван", 3, 45, 10, false));
        users.add(new User(2, "Ашот", 3, 40, 10, false));
        users.add(new User(3, "Ксюша", 4, 23, 10, false));
        users.add(new User(4, "Настя", 4, 3, 2, true));*/
        request.setAttribute("users", users);
        return page;
    }

    @Override
    public int getGroupId() {
        return 2;
    }
}
