package tcgshop.utils;

import java.sql.*;
import java.util.ArrayList;

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

    // Login method by return userType for validation or -1 if failed
    public int login(String username, String password) {
        // Check MySQL connection
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return -1;
        }

        // MySQL query find related username and password
        String query = "SELECT * FROM users WHERE `Username` = ? AND `Password` = ?";

        // Try to prepare the statement and execute
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IsAdmin");
                }
            }
            return -1;
        }

        // Print error if failed
        catch (SQLException e) {
            System.out.println(" ERROR: User login failed.");
            return -1;
        }
    }

    // Sign up method
    public boolean addUser(String username, String password) {
        // Check MySQL connection
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return false;
        }

        // Comparison to check username existing
        if (compareUsers(username)) {
            return false;
        }

        // MySQL query setup
        String query = "INSERT INTO users(`Username`, `Password`, `IsAdmin`) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, 0);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }

        // Print error if failed
        catch (Exception e) {
            System.out.println(" ERROR: Unable to add users");
            return false;
        }
    }

    // User comparison from database method
    public boolean compareUsers(String username) {
        // MySQL query find related username
        String query = "SELECT * FROM users WHERE `Username` = ?";

        // Try to prepare the statement and execute
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }

        // Print error if failed
        catch (SQLException e) {
            System.out.println(" ERROR: Comparison failed.");
        }
        return false;
    }


    // Call category from the category list
    public ArrayList<String> getCategory() {
        // Initialize return list and query
        ArrayList<String> category = new ArrayList<>();
        String query = "SELECT * FROM categories ORDER BY `CategoryName`";

        // Check MySQL connection
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return category;
        }

        // Execute MySQL query, get all category
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    category.add(rs.getString("CategoryName"));
                }
            }
        }

        // Catch error
        catch (SQLException e) {
            System.out.println(" ERROR: Category query failed.");
        }

        return category;
    }


    // Call items from the item list ( ItemID, ItemName, ItemPrice, ItemLeft, Description )
    public ArrayList<ArrayList<Object>> getItems(String category) {
        // Initialize return list and query
        ArrayList<ArrayList<Object>> itemsList = new ArrayList<>();
        String query;

        // Check MySQL connection
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return itemsList;
        }

        // If category is All, change query to retrieve all items
        if (category.equals("All")) {
            query = "SELECT * FROM items ORDER BY `ItemName`";
        }
        else {
            query = "SELECT * FROM tcgshopdb.items LEFT JOIN categories ON items.CategoryID = categories.CategoryID " +
                    "WHERE CategoryName = ? ORDER BY `ItemName`;";
        }

        // Execute queries, add all data into array list
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            if (!category.equals("All")) {
                stmt.setString(1, category);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ArrayList<Object> item = new ArrayList<>();
                    item.add(rs.getInt("ItemID"));
                    item.add(rs.getString("ItemName"));
                    item.add(rs.getDouble("ItemPrice"));
                    item.add(rs.getInt("ItemLeft"));
                    item.add(rs.getString("Description"));
                    itemsList.add(item);
                }
            }
        }

        // Catch error
        catch (SQLException e) {
            System.out.println(" ERROR: Item query failed.");
        }

        return itemsList;
    }
}
