import java.sql.*;
import java.security.MessageDigest;

public class RegistrationService {

    private Connection conn;

    public RegistrationService(Connection conn) {
        this.conn = conn;
    }

    public String registerUser(String email, String username, String password, String role) {

        try {
            if (email == null || username == null || password == null || role == null) {
                return "Invalid input";
            }

            String hashedPassword = md5(password);

            String emailQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt1 = conn.prepareStatement(emailQuery);
            stmt1.setString(1, email);
            ResultSet rs1 = stmt1.executeQuery();

            if (rs1.next()) {
                return "Email is already used";
            }

            String userQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt2 = conn.prepareStatement(userQuery);
            stmt2.setString(1, username);
            ResultSet rs2 = stmt2.executeQuery();

            if (rs2.next()) {
                return "Username already exists";
            }

            String insertQuery = "INSERT INTO users (email, username, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt3 = conn.prepareStatement(insertQuery);
            stmt3.setString(1, email);
            stmt3.setString(2, username);
            stmt3.setString(3, hashedPassword);
            stmt3.setString(4, role);

            stmt3.executeUpdate();

            return "Registration successful";

        } catch (Exception e) {
            e.printStackTrace();
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