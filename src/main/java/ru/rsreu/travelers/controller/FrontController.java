package ru.rsreu.travelers.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.command.ActionFactory;

/**
 * FrontController is a servlet that accepts HTTP requests from the client, extracts a command from the request, executes it, and redirects the client to a JSP page based on the command.
 */
public class FrontController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * doGet is a method that is invoked when a GET request is received from a client
     * @param request is an HttpServletRequest object that means a client request
     * @param response is an HttpServletResponse object that means a response to the client
     * @throws ServletException a general exception a servlet can throw when it encounters difficulty
     * @throws IOException signals that an input/output exception of some sort has occurred
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * doPost is a method that is invoked when a POST request is received from a client
     * @param request is an HttpServletRequest object that means a client request
     * @param response is an HttpServletResponse object that means a response to the client
     * @throws ServletException a general exception a servlet can throw when it encounters difficulty
     * @throws IOException signals that an input/output exception of some sort has occurred
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * processRequest is a method that is invoked when any request is received from a client
     * @param request is an HttpServletRequest object that means a client request
     * @param response is an HttpServletResponse object that means a response to the client
     * @throws ServletException a general exception a servlet can throw when it encounters difficulty
     * @throws IOException signals that an input/output exception of some sort has occurred
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Page page = null;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        page = command.execute(request);
        if (page != null) {
            System.out.print("page = " + page.getPath());
            System.out.println(", redirect = " + page.getTypeRedirect().name());
            if (page.isSendRedirect()) {
                response.sendRedirect(page.getPath());
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page.getPath());
                dispatcher.forward(request, response);
            }
        } else {
            System.out.println("page is null");
        }
    }
}
