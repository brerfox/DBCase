package servlets;

import helpers.CaseUtils;
import helpers.JSONResponse;
import services.dbService.DBException;
import services.dbService.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/get/endingposition")
public class GetEndingPosition extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

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

            JSONResponse.toJson(response, dbService.getEndingPosition());

        } catch (DBException e) {
            JSONResponse.dbConnFailed(response);
        }
    }
}

