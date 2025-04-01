package tcgshop.main;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tcgshop.TCGApplication;

import java.util.Objects;

public class MainScene extends Scene {
    // User information
    private String username;
    private String password;
    private boolean isAdmin;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label adminLabel;

    public MainScene(TCGApplication tcgApplication) {
        // Call constructor from parent class
        super(new BorderPane(), 1000, 600);

        // Root initializing
        BorderPane root = (BorderPane) getRoot();

        // HBox of the top row
        HBox topMenu = new HBox();
        topMenu.setMinHeight(80);
        topMenu.setMaxHeight(80);
        topMenu.setStyle("-fx-background-color: #222831;");

        // Nodes in the top menu


        // Temp
        usernameLabel = new Label("Username: " + username);
        passwordLabel = new Label("Password: " + password);
        adminLabel = new Label("Admin: " + (isAdmin ? "Admin" : "User"));
        Button backButton = new Button("Back");
        backButton.setOnAction(_ -> tcgApplication.setPrimaryStage(tcgApplication.getLoginScene()));
        VBox vBox = new VBox();
        vBox.getChildren().addAll(usernameLabel, passwordLabel, adminLabel, backButton);
        root.setTop(topMenu);
        root.setCenter(vBox);


    }

    // Get account details method
    public void setUserInformation(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;

        // Temp code
        usernameLabel.setText(username);
        passwordLabel.setText(password);
        adminLabel.setText(isAdmin ? "Admin" : "User");
    }
}
