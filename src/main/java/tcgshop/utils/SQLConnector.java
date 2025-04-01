package tcgshop.utils;

import java.sql.*;

public class SQLConnector {
    // SQL connect attribute
    private static final String URL = "jdbc:mysql://localhost:3306/tcgshopdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection conn;

    // Constructor
    public SQLConnector() {
        // Establish MySQL connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }

        // Print error if failed
        catch (Exception e) {
            System.out.println("( MySQL Connection Failed )");
            // DEBUG USE // System.out.println(e);
        }
    }

    // Login method by return userType for validation or "false" if failed
    public String login(String username, String password) {
        // MySQL query find related username and password
        String query = "SELECT * FROM user WHERE `Username` = ? AND `Password` = ?";

        // Try to prepare the statement and execute
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("UserType");
                }
            }
            return "false";
        }

        // Print error if failed
        catch (SQLException e) {
            System.out.println("( MySQL Login Failed )");
            return "false";
        }
    }

    // Sign up method
    public boolean addUser(String username, String password) {
        // Comparison to check username existing
        if (compareUsers(username)) {
            return false;
        }

        // MySQL query setup
        String query = "INSERT INTO user(`Username`, `Password`, `UserType`) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, "user");
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }

        // Print error if failed
        catch (Exception e) {
            System.out.println("( MySQL Data Insert Failed )");
            // DEBUG USE // System.out.println(e);
            return false;
        }
    }

    // User comparison from database method
    public boolean compareUsers(String username) {
        // MySQL query find related username
        String query = "SELECT * FROM user WHERE `Username` = ?";

        // Try to prepare the statement and execute
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }

        // Print error if failed
        catch (SQLException e) {
            System.out.println("( MySQL Compare User Failed )");
            // DEBUG USE // System.out.println(e);
        }
        return false;
    }
}
