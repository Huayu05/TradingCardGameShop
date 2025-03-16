module com.tcgshop.tradingcardgameshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens tcgshop to javafx.fxml;
    exports tcgshop;
}