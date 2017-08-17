package servlets;

import helpers.CaseUtils;
import helpers.JSONResponse;
import helpers.SimpleCache;
import services.dbService.DBException;
import services.dbService.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/get/overview")
public class GetOverview extends HttpServlet {

    static String SERVLET_KEY = "/api/get/overview";

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        if (SimpleCache.containsKey(SERVLET_KEY) && CaseUtils.checkAuth(request)) {
            JSONResponse.toJson(response, SimpleCache.getCacheData(SERVLET_KEY));
            return;
        }

        DBService dbService = new DBService();

//        db is not alive
        if (!dbService.validateConnection()) {
            JSONResponse.dbConnFailed(response);
            return;
        }

        try {

//        if user is not authenticated
            if (!CaseUtils.checkAuth(request)) {
                JSONResponse.unAuthResponse(response);
                return;
            }

            Object data = dbService.getOverview();
            SimpleCache.setCacheData(SERVLET_KEY, data);
            JSONResponse.toJson(response, data);

        } catch (DBException e) {
            JSONResponse.dbConnFailed(response);
        }
    }
}