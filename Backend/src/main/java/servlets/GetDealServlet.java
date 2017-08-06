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
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
 * ONLY after authentication!
 * GET /api/get/deal - all deals
 * GET /api/get/deal?id=... - deal with deal_id == id
 * GET /api/get/deal?page_id=... deals from page
 *      with id == page_id (1000 on one page)
 * GET /api/get/deal?page_id=...&page_size=... the same as above but
 *      with page_size deals on one page
 *
 * */
@WebServlet("/api/get/deal")
public final class GetDealServlet extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        DBService dbService = new DBService();

//        if user is not authenticated
        if (!CaseUtils.checkAuth(request)) {
            JSONResponse.unAuthResponse(response);
            return;
        }

        try {
            if (request.getParameterMap().containsKey("id")) {
                Long deal_id = Long.valueOf(request.getParameter("id"));
                JSONResponse.toJson(response, dbService.getDealById(deal_id));

            } else if (request.getParameterMap().containsKey("page_id")) {

                Integer page_id = Integer.valueOf(request.getParameter("page_id"));

                if (request.getParameterMap().containsKey("page_size")) {
                    Integer page_size = Integer.valueOf(request.getParameter("page_size"));
                    JSONResponse.toJson(response, dbService.getDealsPage(page_id, page_size));
                }
                else {
                    JSONResponse.toJson(response, dbService.getDealsPage(page_id));
                }
            } else {
                JSONResponse.toJson(response, dbService.getAllDeals());
            }

        } catch (DBException e) {
            JSONResponse.dbConnFailed(response);
        }

    }

}