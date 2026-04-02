public class IndexService {

    public String handleRequest(Integer role) {

        if (role == null) {
            return "SHOW_AUTH_PAGE";
        }

        return "REDIRECT_CHECK_PRODUCT";
    }
}