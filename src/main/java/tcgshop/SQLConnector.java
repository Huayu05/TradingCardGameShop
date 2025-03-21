package tcgshop;

import java.sql.*;

public class SQLConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/tcgshopdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection conn;

    private String userType;

    public SQLConnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public String login(String username, String password) {
        if (compareUsers(username, password)) {

            return userType;
        }
        else {
            return "false";
        }
    }

    public boolean addUser(String username, String password, String type) {
        if (compareUsers(username, password)) {
            return false;
        }
        String query = "INSERT INTO user(`Username`, `Password`, `UserType`) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, type);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean compareUsers(String username, String password) {
        String query = "SELECT * FROM user";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                if (rs.getString("Username").equals(username) && rs.getString("Password").equals(password)) {
                    userType = rs.getString("UserType");
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
