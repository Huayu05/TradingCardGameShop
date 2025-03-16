package tcgshop;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

class LoginPage extends Scene {
    // Constructor
    public LoginPage() {
        // Call constructor from parent class
        super(new StackPane(), 1000, 600);

        // StackPane setup
        StackPane loginPageBase = (StackPane) getRoot();

        // Background Image
        Image image = new Image(getClass().getResource("/tcgshop/images/pokeball_background_picture.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(false);
        imageView.fitWidthProperty().bind(loginPageBase.widthProperty());
        imageView.fitHeightProperty().bind(loginPageBase.heightProperty());
        loginPageBase.getChildren().addFirst(imageView);

        Pane paneOne = new Pane();
        paneOne.setStyle("-fx-background-color: #FFFDE7;-fx-background-radius: 20px;");
        paneOne.setMinSize(600, 360);
        paneOne.setMaxSize(900, 540);

        loginPageBase.getChildren().add(paneOne);
        StackPane.setMargin(paneOne, new Insets(20));


    }
}
