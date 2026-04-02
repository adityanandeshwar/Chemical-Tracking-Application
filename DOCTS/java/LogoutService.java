import java.util.Map;

public class LogoutService {

    public String logout(Map<String, Object> session) {

        if (session != null) {
            session.remove("role");
            session.remove("username");
        }

        return "REDIRECT_INDEX";
    }
}