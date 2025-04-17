package tcgshop.utils;

import tcgshop.main.shop.ItemBox;

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


    // Create bill
    public int createBill(String username) {
        // Check connection
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return 0;
        }

        // MySQL query create new bill
        String query = "INSERT INTO bills (`UserID`, `BillDate`, `TaxPercent`, `DiscountPercent`) VALUES ((SELECT UserID FROM users WHERE userName = ?), NOW(), ?, ?);";
        // Try to prepare the statement and execute
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, username);
            stmt.setInt(2, GeneralFunction.loadTax());
            stmt.setInt(3, GeneralFunction.loadDiscount());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        // Print error if failed
        catch (SQLException e) {
            System.out.println(" ERROR: Bill create failed.");
            System.out.println(username);
            System.out.println(e);
        }
        return -1;
    }


    // Deduct sold item
    public void deductItem(ItemBox itemBox, int billID) {
        // Check connection
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return;
        }

        // MySQL query update to new amount
        String query1 = "INSERT INTO sells (`ItemID`, `ItemCount`, `ItemPrice`, `BillID`) VALUES ((SELECT `ItemID` FROM items WHERE `ItemName` = ?), ?, (SELECT `ItemPrice` FROM items WHERE `ItemName` = ?), ?);";
        String query2 = "UPDATE items SET `ItemLeft` = `ItemLeft` - ? WHERE (`ItemName` = ?);";

        // Try to prepare the statement and execute
        try {
            try (PreparedStatement stmt = conn.prepareStatement(query1)) {
                stmt.setString(1, itemBox.getItemName());
                stmt.setInt(2, itemBox.getItemChosen());
                stmt.setString(3, itemBox.getItemName());
                stmt.setInt(4, billID);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(query2)) {
                stmt.setInt(1, itemBox.getItemChosen());
                stmt.setString(2, itemBox.getItemName());
                stmt.executeUpdate();
            }
        }

        // Print error if failed
        catch (SQLException e) {
            System.out.println(" ERROR: Item deduct failed.");
            System.out.println(e.getMessage());
        }
    }


    // Get bill details [ [ BillInfo<>, Item1<>, ... ItemN<> ], [..], .. ]
    public ArrayList<ArrayList<ArrayList<Object>>> getBill(String username) {
        // Check connection
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return null;
        }

        ArrayList<ArrayList<ArrayList<Object>>> returnList = new ArrayList<>();
        ArrayList<Integer> billList = new ArrayList<>();

        // Query preparation ( Get bill for a user )
        String query1 = "SELECT * FROM bills WHERE UserID = (SELECT UserID FROM users WHERE userName = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query1)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                billList.add(rs.getInt("BillID"));
            }
        }
        catch (Exception e) {
            System.out.println(" ERROR: Bill getter failed.");
        }

        // Query preparation ( Get Items in bill )
        for (Integer billID : billList) {
            ArrayList<ArrayList<Object>> bills = new ArrayList<>();

            String query2 = "SELECT * FROM bills WHERE BillID = ?";
            String query3 = "SELECT i.ItemName, s.ItemPrice, s.ItemCount FROM sells s JOIN items i ON s.ItemID=i.ItemID WHERE s.BillID = ?";

            ArrayList<Object> billDetails = new ArrayList<>();
            try (PreparedStatement stmt = conn.prepareStatement(query2)) {
                stmt.setInt(1, billID);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                billDetails.add(rs.getTimestamp("BillDate"));
                billDetails.add(rs.getInt("DiscountPercent"));
                billDetails.add(rs.getInt("TaxPercent"));
                bills.add(billDetails);
            }
            catch (Exception e) {
                System.out.println(" ERROR: Bill date getter failed.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(query3)) {
                stmt.setInt(1, billID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    ArrayList<Object> itemList = new ArrayList<>();
                    itemList.add(rs.getString("ItemName"));
                    itemList.add(rs.getInt("ItemCount"));
                    itemList.add(rs.getDouble("ItemPrice"));
                    bills.add(itemList);
                }
            }
            catch (Exception e) {
                System.out.println(" ERROR: Item getter failed.");
            }

            returnList.add(bills);
        }
        return returnList;
    }


    // Method change password
    public boolean changePassword(String username, String password) {
        // Check connection
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return false;
        }

        String query = "UPDATE users SET `Password` = ? WHERE `UserID` = (SELECT UserID FROM users WHERE userName = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, username);
            stmt.executeUpdate();
            return true;
        }
        catch (Exception e) {
            System.out.println(" ERROR: Password change failed.");
            return false;
        }
    }


    // Fill in feedback
    public boolean addFeedback(String feedback, String username) {
        // Check text len
        if (feedback.length() < 10) {
            return false;
        }

        // Check connection
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return false;
        }

        String query = "INSERT INTO feedbacks(`FeedbackText`, `UserID`, `FeedbackTime`) VALUES (?, (SELECT UserID FROM users WHERE userName = ?), NOW())";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, feedback);
            stmt.setString(2, username);
            stmt.executeUpdate();
            return true;
        }
        catch (Exception e) {
            System.out.println(" ERROR: Feedback add failed.");
            return false;
        }
    }


    // Edit items detail for admin
    public boolean editItem(String name, String priceStr, String countStr, ArrayList<Object> item) {
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return false;
        }

        double price;
        int count;

        if (name.length() > 20 || name.isEmpty()) {
            return false;
        }
        try {
            price = Double.parseDouble(priceStr);
            if (price > 10000) {
                return false;
            }

            count = Integer.parseInt(countStr);
            if (count > 1000) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }

        if (name.equals(item.get(1).toString()) && GeneralFunction.twoDecimalPlaces(price).equals(item.get(2).toString()) && count == (Integer) item.get(3)) {
            return false;
        }


        String query = "UPDATE items SET `ItemName` = ?, `ItemPrice` = ?, `ItemLeft` = ? WHERE `ItemID` = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, count);
            stmt.setInt(4, (Integer) item.getFirst());
            stmt.executeUpdate();
            return true;
        }
        catch (Exception e) {
            System.out.println(" ERROR: Item edit failed.");
            return false;
        }
    }


    // Delete selected item from db
    public void deleteItem(int itemID) {
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return;
        }


        String query = "DELETE FROM items WHERE `ItemID` = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, itemID);
            stmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(" ERROR: Item delete failed.");
        }
    }


    // Method to add new item
    public boolean addItem(String name, String priceStr, String countStr, String category) {
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return false;
        }

        double price;
        int count;

        if (name.length() > 20 || name.isEmpty()) {
            return false;
        }
        try {
            price = Double.parseDouble(priceStr);
            if (price > 10000) {
                return false;
            }

            count = Integer.parseInt(countStr);
            if (count > 1000) {
                return false;
            }
        }
        catch (Exception e) {
            System.out.println(" ERROR: Item add failed.");
            return false;
        }

        ArrayList<Object> catList = new ArrayList<>();
        for (String exist : getCategory()) {
            catList.add(exist.trim().toLowerCase().replaceAll("\\s+", ""));
        }
        if (!catList.contains(category.trim().replaceAll("\\s+", "").toLowerCase())) {
            String query1 = "INSERT INTO categories (`CategoryName`) VALUES (?)";
            try (PreparedStatement stmt1 = conn.prepareStatement(query1)) {
                stmt1.setString(1, category);
                stmt1.executeUpdate();
            } catch (Exception e) {
                System.out.println(" ERROR: Category add failed.");
                return false;
            }
        }
        else {
            for (String exist : getCategory()) {
                if (exist.trim().replaceAll("\\s+", "").equalsIgnoreCase(category.trim().replaceAll("\\s+", ""))) {
                    category = exist;
                }
            }
        }


        String query2 = "INSERT INTO items (`ItemName`, `ItemPrice`, `ItemLeft`, `CategoryID`) VALUES (?, ?, ?, (SELECT CategoryID FROM categories WHERE CategoryName = ?))";
        try (PreparedStatement stmt2 = conn.prepareStatement(query2)) {
            stmt2.setString(1, name);
            stmt2.setDouble(2, price);
            stmt2.setInt(3, count);
            stmt2.setString(4, category);
            stmt2.executeUpdate();
            return true;
        }
        catch (Exception e) {
            System.out.println(" ERROR: Item add failed.");
            return false;
        }
    }


    // Method change category name
    public void deleteCategory(String oldCategory, String newCategory) {
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return;
        }

        String query;
        if (newCategory.isEmpty()) {
            query = "DELETE FROM categories WHERE `CategoryName` = ?";
        }
        else {
            query = "UPDATE categories SET `CategoryName` = ? WHERE `CategoryName` = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            if (!newCategory.isEmpty()) {
                stmt.setString(1, newCategory);
                stmt.setString(2, oldCategory);
            }
            else {
                stmt.setString(1, oldCategory);
            }
            stmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(" ERROR: Category change failed.");
        }
    }


    // Method get feedback
    public ArrayList<ArrayList<Object>> getFeedback() {
        ArrayList<ArrayList<Object>> feedbacks = new ArrayList<>();

        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return feedbacks;
        }

        String query = "SELECT * FROM feedbacks JOIN users ON users.`UserID` = feedbacks.`UserID`";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ArrayList<Object> feedback = new ArrayList<>();
                feedback.add(rs.getString("FeedbackText"));
                feedback.add(rs.getString("Username"));
                feedback.add(rs.getTimestamp("FeedbackTime"));
                feedbacks.add(feedback);
            }
            return feedbacks;
        }
        catch (Exception e) {
            System.out.println(" ERROR: Feedback query failed.");
            return feedbacks;
        }
    }


    // Method get all user separate by usertype
    public ArrayList<ArrayList<String>> getAllUser() {
        ArrayList<ArrayList<String>> accList = new ArrayList<>();
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return accList;
        }

        String query1 = "SELECT * FROM users WHERE `IsAdmin` = 0 AND `UserID` > 1";
        String query2 = "SELECT * FROM users WHERE `IsAdmin` = 1 AND `UserID` > 1";

        try {
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(query1);
            ArrayList<String> users = new ArrayList<>();
            while (rs1.next()) {
                users.add(rs1.getString("Username"));
            }
            accList.add(users);
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query2);
            ArrayList<String> admins = new ArrayList<>();
            while (rs2.next()) {
                admins.add(rs2.getString("Username"));
            }
            accList.add(admins);
            return accList;
        }
        catch (Exception e) {
            System.out.println(" ERROR: User query failed.");
            return accList;
        }
    }


    // Method change user type
    public void changeUserType(boolean isAdmin, String username) {
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return;
        }

        String query = "UPDATE users SET `IsAdmin` = ? WHERE `UserID` = (SELECT `UserID` FROM users WHERE `Username` = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, isAdmin ? 1 : 0);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(" ERROR: User change failed.");
        }
    }


    public ArrayList<ArrayList<Object>> getItemSales() {
        ArrayList<ArrayList<Object>> itemSales = new ArrayList<>();
        if (conn == null) {
            System.out.println(" ERROR: No MySQL connection available.");
            return itemSales;
        }

        String query = "SELECT i.ItemName, COALESCE(SUM(s.ItemCount), 0) AS ItemCount  FROM items i LEFT JOIN sells s ON s.ItemID = i.ItemID GROUP BY i.ItemID ORDER BY i.ItemName;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ArrayList<Object> item = new ArrayList<>();
                item.add(rs.getString("ItemName"));
                item.add(rs.getInt("ItemCount"));
                itemSales.add(item);
            }
            return itemSales;

        }
        catch (Exception e) {
            System.out.println(" ERROR: Item query failed.");
            return itemSales;
        }
    }
}
