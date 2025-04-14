module com.tcgshop.tradingcardgameshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.java;
    requires jdk.compiler;


    opens tcgshop to javafx.fxml;
    exports tcgshop;
    exports tcgshop.authentication;
    opens tcgshop.authentication to javafx.fxml;
    exports tcgshop.utils;
    opens tcgshop.utils to javafx.fxml;
    exports tcgshop.main;
    opens tcgshop.main to javafx.fxml;
    exports tcgshop.main.shop to javafx.fxml;
    opens tcgshop.main.shop to javafx.fxml;
    exports tcgshop.main.setting to javafx.fxml;
    opens tcgshop.main.setting to javafx.fxml;
}