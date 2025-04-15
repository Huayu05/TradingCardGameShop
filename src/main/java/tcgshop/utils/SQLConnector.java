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
}
