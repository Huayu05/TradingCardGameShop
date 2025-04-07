package tcgshop.main;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tcgshop.TCGApplication;
import tcgshop.main.shop.CartPane;
import tcgshop.main.shop.ShopPane;

public class MainScene extends Scene {
    // Dynamic nodes
    private ShopPane shopPane;
    private CartPane cartPane;
    private TopMenu topMenu;

    public MainScene(TCGApplication tcgApplication) {
        // Call constructor from parent class
        super(new BorderPane(), 1000, 600);

        // Root initializing
        BorderPane root = (BorderPane) getRoot();

        // HBox of the top row menu
        topMenu = new TopMenu(tcgApplication, this);

        // Shop page
        shopPane = new ShopPane(tcgApplication, this);

        // Cart page
        cartPane = new CartPane(tcgApplication, this);
        cartPane.setVisible(false);

        // Stack Pane for all main UI
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(cartPane, shopPane);

        // Root scene config
        root.setTop(topMenu);
        root.setCenter(stackPane);
        root.setStyle("-fx-background-color: #EEEEEE");
    }


    // Getter method ( Top Menu )
    public TopMenu getTopMenu() {
        return topMenu;
    }


    // Getter method ( Shop Pane )
    public ShopPane getShopPane() {
        return shopPane;
    }


    // Getter method ( Cart Pane )
    public CartPane getCartPane() {
        return cartPane;
    }
}
