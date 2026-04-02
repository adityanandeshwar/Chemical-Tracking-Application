import java.sql.*;
import java.security.MessageDigest;

public class LoginService {

    private Connection conn;

    public LoginService(Connection conn) {
        this.conn = conn;
    }

    public LoginResponse login(String email, String password) {

        try {
            if (email == null || password == null) {
                return new LoginResponse(false, "Invalid input", null, null);
            }

            String hashedPassword = md5(password);

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int role = rs.getInt("role");
                String username = rs.getString("username");

                return new LoginResponse(true, "Login successful", role, username);
            } else {
                return new LoginResponse(false, "Invalid email or password", null, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(false, "Error occurred", null, null);
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