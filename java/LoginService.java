import java.sql.*;
import java.security.MessageDigest;

public class LoginService {

    private Connection conn;

    public LoginService(Connection conn) {
        this.conn = conn;
    }

    public LoginResponse login(String email, String password) {

    try {
        if (!ValidationUtil.isValidEmail(email)) {
            return new LoginResponse(false, "Invalid Email", null, null);
        }

        String hashed = md5(password);

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password = ?");
        stmt.setString(1, email);
        stmt.setString(2, hashed);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new LoginResponse(
                    true,
                    "Login successful",
                    rs.getInt("role"),
                    rs.getString("username")
            );
        }

        return new LoginResponse(false, "Invalid credentials", null, null);

    } catch (Exception e) {
        return new LoginResponse(false, "Error", null, null);
    }
}

    private String md5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(input.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}