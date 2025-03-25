package tcgshop;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ShopScene extends Scene {
    // User information
    private String username;
    private String password;
    private boolean isAdmin;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label adminLabel;

    public ShopScene(TCGApplication tcgApplication) {
        // Call constructor from parent class
        super(new VBox());

        VBox vBox = (VBox) getRoot();

        //
        usernameLabel = new Label("Username: " + username);
        passwordLabel = new Label("Password: " + password);
        adminLabel = new Label("Admin: " + (isAdmin ? "Admin" : "User"));
        Button backButton = new Button("Back");
        backButton.setOnAction(_ -> tcgApplication.setPrimaryStage(tcgApplication.getLoginScene()));
        vBox.getChildren().addAll(usernameLabel, passwordLabel, adminLabel, backButton);


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
