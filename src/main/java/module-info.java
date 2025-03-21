module com.tcgshop.tradingcardgameshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.java;


    opens tcgshop to javafx.fxml;
    exports tcgshop;
}