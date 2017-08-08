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

/*
* GET /api/get/instrument - get all instruments
* GET /api/get/instrument?id=... - get instrument with exact id
* */

@WebServlet("/api/get/instrument")
public final class GetInstrumentServlet extends HttpServlet {

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

            if (request.getParameterMap().containsKey("id")) {
                Long instrument_id = Long.valueOf(request.getParameter("id"));
                JSONResponse.toJson(response, dbService.getInstrumentByID(instrument_id));
            } else {
                JSONResponse.toJson(response, dbService.getAllInstruments());
            }
        } catch (DBException e) {
            JSONResponse.dbConnFailed(response);
        }
    }

}
