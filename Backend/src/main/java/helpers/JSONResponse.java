package helpers;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSONResponse {

    public static HttpServletResponse toJson(final HttpServletResponse response, Object obj)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(obj)
        );
        response.getWriter().flush();
        return response;
    }

    public static HttpServletResponse unAuthResponse(final HttpServletResponse response)
            throws IOException{

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print("unauthorized");
        return response;
    }

    public static HttpServletResponse dbConnFailed(final HttpServletResponse response)
            throws IOException{
        response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        response.getWriter().print("db_failed");
        return response;
    }

}
