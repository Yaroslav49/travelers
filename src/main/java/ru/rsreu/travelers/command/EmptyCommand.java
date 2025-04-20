package ru.rsreu.travelers.command;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;

public class EmptyCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.login");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        return page;
    }


    @Override
    public int getGroupId() {
        return 0;
    }
}