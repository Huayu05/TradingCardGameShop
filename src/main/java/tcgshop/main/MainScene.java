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
    private TCGApplication tcgApplication;
    private StackPane root;
    private BorderPane borderPane;
    private ShopPane shopPane;
    private CartPane cartPane;
    private TopMenu topMenu;

    public MainScene(TCGApplication tcgApplication, double width, double height) {
        // Call constructor from parent class
        super(new StackPane(), width, height);

        // Initialize dynamic nodes
        this.tcgApplication = tcgApplication;

        // Background StackPane setup
        root = (StackPane) getRoot();
        root.setPrefSize(width, height);

        // Background Image
        Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("/tcgshop/images/pokeball_background_picture.png")).toExternalForm());
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setOpacity(0.5);
        bgImageView.setPreserveRatio(false);
        bgImageView.fitWidthProperty().bind(root.widthProperty());
        bgImageView.fitHeightProperty().bind(root.heightProperty());
        root.getChildren().addFirst(bgImageView);

        // Root initializing
        borderPane = new BorderPane();
        root.getChildren().add(borderPane);

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
        borderPane.setTop(topMenu);
        borderPane.setCenter(stackPane);
    }


    // Reset all layouts
    public void resetAll() {
        root.getChildren().clear();

        Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("/tcgshop/images/pokeball_background_picture.png")).toExternalForm());
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setOpacity(0.5);
        bgImageView.setPreserveRatio(false);
        bgImageView.fitWidthProperty().bind(root.widthProperty());
        bgImageView.fitHeightProperty().bind(root.heightProperty());
        root.getChildren().addFirst(bgImageView);

        borderPane = new BorderPane();
        root.getChildren().add(borderPane);
        topMenu = new TopMenu(tcgApplication, this);
        shopPane = new ShopPane(tcgApplication, this);
        cartPane = new CartPane(tcgApplication, this);
        cartPane.setVisible(false);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(cartPane, shopPane);
        borderPane.setTop(topMenu);
        borderPane.setCenter(stackPane);
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
