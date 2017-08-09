package servlets;

import helpers.JSONResponse;
import services.PropertyService;
import services.dbService.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/logout")
public class LogOutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        JSONResponse.toJson(response, session.getAttribute("authorized") != null);
        request.getSession().removeAttribute("authorized");
    }
}
