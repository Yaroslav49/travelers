package ru.rsreu.travelers.filter;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.command.ActionFactory;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.logic.AccountLogic;
import ru.rsreu.travelers.logic.LoginLogic;

import java.io.IOException;

public class CheckingAccessFilter implements Filter {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain next) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String command = request.getParameter("command");

        boolean success;
        if (command.equals("login")) {
            success = true;
        } else {
            try {
                int accountId = (int) session.getAttribute("accountId");
                Account account = AccountLogic.getAccount(accountId);
                if (account.getId() > 0) {
                    int groupId = account.getGroupId();
                    ActionFactory client = new ActionFactory();
                    ActionCommand commandObject = client.defineCommand(request);
                    int needGroupId = commandObject.getGroupId();
                    if (groupId == needGroupId || needGroupId == 0) {
                        success = true;
                    } else {
                        success = false;
                    }
                } else {
                    success = false;
                }
            } catch (NumberFormatException exception) {
                success = false;
                System.out.println(exception.getMessage());
            }
        }

        if (success) {
            next.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(resourcer.getString("path.page.start"));
        }
    }
}
