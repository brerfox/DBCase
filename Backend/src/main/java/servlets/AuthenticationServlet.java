package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.CaseUtils;
import helpers.JSONResponse;
import services.PropertyService;
import services.dbService.DBException;
import services.dbService.DBService;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/api/auth")
public class AuthenticationServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");

        DBService dbService = new DBService();

        HttpSession session = request.getSession();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        System.out.println(login + " , " + password);


        if (login == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("unauthorized");
            return;
        }

        try {
            if (!dbService.validateUser(login, password)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print("unauthorized");
            } else {
                response.getWriter().print("authorized");
                response.setStatus(HttpServletResponse.SC_OK);
                session.setAttribute("authorized", "true");
            }
        } catch (DBException e) {
            JSONResponse.dbConnFailed(response);
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        JSONResponse.toJson(response, CaseUtils.checkAuth(request));
    }

}
