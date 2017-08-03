
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Simple Hello servlet.
 */

@WebServlet("/hello")
// you can use also URL pattern <url-pattern>/test/*</url-pattern>
public final class HelloServlet extends HttpServlet {

    /**
     * Respond to a GET request for the content produced by
     * this servlet.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are producing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {


//        response.setContentType("text/html");
//        PrintWriter writer = response.getWriter();
//        writer.println("the Hello, World application.");

        request.setAttribute("message", "I was in servlet!!!");
        request.getRequestDispatcher("WEB-INF/views/hello.jsp").forward(request, response);

    }
} 