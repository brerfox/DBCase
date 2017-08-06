package helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CaseUtils {

    public static boolean checkAuth(HttpServletRequest request) {
        Object auth = request.getSession().getAttribute("authorized");
        return auth != null && ((String)auth).equals("true");
    }

}
