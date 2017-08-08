package servlets;

import helpers.CaseUtils;
import helpers.JSONResponse;
import services.PropertyService;
import services.dbService.DBException;
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
@WebServlet("/api/test")
public class TestAPIServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        Map<String, Object> info = new HashMap<>();
        PropertyService propertyService = new PropertyService();

        DBService dbService = new DBService();

        info.put("DB name: ", "not connected");
        info.put("DB version: ", "not connected");
        info.put("Driver: ", "not connected");
        info.put("Autocommit: ", "not connected");

        if (dbService.validateConnection()) {
            try {
                Connection connection = dbService.getConnection();
                info.put("DB name: ", connection.getMetaData().getDatabaseProductName());
                info.put("DB version: ", connection.getMetaData().getDatabaseProductVersion());
                info.put("Driver: ", connection.getMetaData().getDriverName());
                info.put("Autocommit: ", connection.getAutoCommit());
            } catch (SQLException e) {}
        }


        info.put("Properties", propertyService.getProperties());
        final String dir = System.getProperty("user.dir");
        info.put("Working dir", dir);
        JSONResponse.toJson(response, info);
    }
}

