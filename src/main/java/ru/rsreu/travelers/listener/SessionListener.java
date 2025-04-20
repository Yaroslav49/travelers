package ru.rsreu.travelers.listener;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import ru.rsreu.travelers.logic.LoginLogic;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        try {
            int accountId = (int) session.getAttribute("accountId");
            LoginLogic.resetAuthorized(accountId);
        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }

    }
}
