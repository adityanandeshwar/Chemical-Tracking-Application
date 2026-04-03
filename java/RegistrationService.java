import java.sql.*;
import java.security.MessageDigest;

public class RegistrationService {

    private Connection conn;

    public RegistrationService(Connection conn) {
        this.conn = conn;
    }

    public String registerUser(String email, String username,
                           String password, String confirmPassword,
                           String role) {

    try {
        if (!ValidationUtil.isValidEmail(email)) {
            return "Invalid Email address";
        }

        if (!ValidationUtil.passwordsMatch(password, confirmPassword)) {
            return "Passwords do not match";
        }

        if (!ValidationUtil.isNotEmpty(username)) {
            return "Username required";
        }

        String hashedPassword = md5(password);

        PreparedStatement stmt1 = conn.prepareStatement(
                "SELECT * FROM users WHERE email = ?");
        stmt1.setString(1, email);
        if (stmt1.executeQuery().next()) {
            return "Email already exists";
        }

        PreparedStatement stmt2 = conn.prepareStatement(
                "SELECT * FROM users WHERE username = ?");
        stmt2.setString(1, username);
        if (stmt2.executeQuery().next()) {
            return "Username already exists";
        }

        PreparedStatement stmt3 = conn.prepareStatement(
                "INSERT INTO users (email, username, password, role) VALUES (?, ?, ?, ?)");
        stmt3.setString(1, email);
        stmt3.setString(2, username);
        stmt3.setString(3, hashedPassword);
        stmt3.setString(4, role);

        stmt3.executeUpdate();

        return "Registration successful";

    } catch (Exception e) {
        return "Error occurred";
    }
}

    private String md5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}