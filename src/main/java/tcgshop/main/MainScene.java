package tcgshop.main;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import tcgshop.TCGApplication;
import tcgshop.main.shop.CartPane;
import tcgshop.main.shop.ShopPane;

import java.util.Objects;

public class MainScene extends Scene {
    // Dynamic nodes
    private ShopPane shopPane;
    private CartPane cartPane;
    private TopMenu topMenu;

    public MainScene(TCGApplication tcgApplication) {
        // Call constructor from parent class
        super(new StackPane(), 1000, 650);

        // Background StackPane setup
        StackPane stackPaneRoot = (StackPane) getRoot();

        // Background Image
        Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("/tcgshop/images/pokeball_background_picture.png")).toExternalForm());
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setOpacity(0.5);
        bgImageView.setPreserveRatio(false);
        bgImageView.fitWidthProperty().bind(stackPaneRoot.widthProperty());
        bgImageView.fitHeightProperty().bind(stackPaneRoot.heightProperty());
        stackPaneRoot.getChildren().addFirst(bgImageView);

        // Root initializing
        BorderPane root = new BorderPane();
        stackPaneRoot.getChildren().add(root);

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
