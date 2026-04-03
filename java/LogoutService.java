import java.util.Map;

public class LogoutService {

    public void logout(Session session) {
    if (session != null) {
        session.clear();
    }
}
}