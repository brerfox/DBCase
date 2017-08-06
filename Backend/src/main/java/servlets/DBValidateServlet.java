package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.JSONResponse;
import services.PropertyService;
import services.dbService.DBService;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/db_is_alive")
public final class DBValidateServlet extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        DBService dbService = new DBService();
        JSONResponse.toJson(response, dbService.validateConnection());
    }

}