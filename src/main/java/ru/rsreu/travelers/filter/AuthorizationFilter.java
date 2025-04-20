package ru.rsreu.travelers.filter;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain next) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Object accountId = session.getAttribute("accountId");
        String command = request.getParameter("command");
        if (command == null || !command.equals("login") && accountId == null) {
            response.sendRedirect(resourcer.getString("path.page.start"));
        } else {
            next.doFilter(servletRequest, servletResponse);
        }
    }
}
