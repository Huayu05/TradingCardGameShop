package tcgshop.authentication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import tcgshop.TCGApplication;

import java.util.Objects;

public class LoginScene extends Scene {
    // Class attributes
    private StackPane loginMain;
    private SignUpPane signUpPane;
    private LogInPane logInPane;

    // Constructor
    public LoginScene(TCGApplication tcgApplication) {
        // Call constructor from parent class
        super(new StackPane(), 1000, 600);

        // Background StackPane setup
        StackPane loginPageRoot = (StackPane) getRoot();

        // Background Image
        Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("/tcgshop/images/pokeball_background_picture.png")).toExternalForm());
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setPreserveRatio(false);
        bgImageView.fitWidthProperty().bind(loginPageRoot.widthProperty());
        bgImageView.fitHeightProperty().bind(loginPageRoot.heightProperty());
        loginPageRoot.getChildren().addFirst(bgImageView);

        // Main Login and Signin Container
        loginMain = new StackPane();
        loginMain.setStyle("-fx-background-color: #FFFDE7;-fx-background-radius: 20px;-fx-effect: dropshadow(gaussian, #0000006F, 30, 0.5, 0, 0);");
        loginMain.setMinSize(600, 360);
        loginMain.setMaxSize(900, 540);
        loginMain.setPadding(new Insets(0, 50, 0 ,50));
        loginPageRoot.getChildren().add(loginMain);
        StackPane.setMargin(loginMain, new Insets(20));

        // Login Pane Setup
        logInPane = new LogInPane(tcgApplication);
        logInPane.prefHeightProperty().bind(loginMain.heightProperty());
        logInPane.setOpacity(0);
        loginMain.getChildren().add(logInPane);

        // Signin Pane
        signUpPane = new SignUpPane(tcgApplication);
        signUpPane.prefHeightProperty().bind(loginMain.heightProperty());
        loginMain.getChildren().add(signUpPane);

        // Moving Pane
        MovingPane movingPane = new MovingPane(this);
        movingPane.prefHeightProperty().bind(loginMain.heightProperty());
        movingPane.prefWidthProperty().bind(loginMain.widthProperty().multiply(0.38));
        movingPane.maxWidthProperty().bind(loginMain.widthProperty().multiply(0.38));
        StackPane.setAlignment(movingPane, Pos.CENTER_LEFT);
        loginMain.getChildren().add(movingPane);


    }


    // Getter method ( Login Main Pane )
    public StackPane getLoginMain() {
        return loginMain;
    }


    // Getter method ( Log In Pane )
    public LogInPane getLogInPane() {
        return logInPane;
    }


    // Getter method ( Sign In Pane )
    public SignUpPane getSignUpPane() {
        return signUpPane;
    }
}
