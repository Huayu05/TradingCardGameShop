package tcgshop;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

class LoginPage extends Scene {
    // Class Attribute
    private Image bgImage;
    private ImageView bgImageView;
    private StackPane loginMain;
    private SignInPane signInPane;

    // Constructor
    public LoginPage() {
        // Call constructor from parent class
        super(new StackPane(), 1000, 600);

        // StackPane setup
        StackPane loginPageRoot = (StackPane) getRoot();

        // Background Image
        bgImage = new Image(getClass().getResource("/tcgshop/images/pokeball_background_picture.png").toExternalForm());
        bgImageView = new ImageView(bgImage);
        bgImageView.setPreserveRatio(false);
        bgImageView.fitWidthProperty().bind(loginPageRoot.widthProperty());
        bgImageView.fitHeightProperty().bind(loginPageRoot.heightProperty());
        loginPageRoot.getChildren().addFirst(bgImageView);

        // Main Login and Signin Pane
        loginMain = new StackPane();
        loginMain.setStyle("-fx-background-color: #FFFDE7;-fx-background-radius: 20px;");
        loginMain.setMinSize(600, 360);
        loginMain.setMaxSize(900, 540);
        loginMain.setPadding(new Insets(20));
        loginPageRoot.getChildren().add(loginMain);
        StackPane.setMargin(loginMain, new Insets(20));

        // Signin Pane
        signInPane = new SignInPane();
        signInPane.prefHeightProperty().bind(loginMain.heightProperty());
        loginMain.getChildren().add(signInPane);
    }
}
