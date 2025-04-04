package tcgshop.main;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import tcgshop.TCGApplication;
import tcgshop.main.shop.ShopPane;

public class MainScene extends Scene {
    // Dynamic nodes
    private TopMenu topMenu;

    public MainScene(TCGApplication tcgApplication) {
        // Call constructor from parent class
        super(new BorderPane(), 1000, 600);

        // Root initializing
        BorderPane root = (BorderPane) getRoot();

        // HBox of the top row menu
        topMenu = new TopMenu(tcgApplication, this);

        // Shop page
        ShopPane shopPane = new ShopPane(tcgApplication);

        // Root scene config
        root.setTop(topMenu);
        root.setCenter(shopPane);
        root.setStyle("-fx-background-color: #EEEEEE");
    }


    // Getter method ( Top Menu )
    public TopMenu getTopMenu() {
        return topMenu;
    }
}
