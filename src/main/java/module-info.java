module com.tcgshop.tradingcardgameshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.tcgshop.tradingcardgameshop to javafx.fxml;
    exports com.tcgshop.tradingcardgameshop;
}