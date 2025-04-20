package ru.rsreu.travelers.command;

import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.controller.Page;

public interface ActionCommand {
    public Page execute(HttpServletRequest request);
    public int getGroupId();
}
