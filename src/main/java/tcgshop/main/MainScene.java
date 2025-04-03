package tcgshop.main;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import tcgshop.TCGApplication;
import tcgshop.main.shop.ShopPane;

public class MainScene extends Scene {
    // User information
    private String username = "huayu";
    private String password = "123456";
    private boolean isAdmin = true;

    // Dynamic nodes
    private TopMenu topMenu;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label adminLabel;

    public MainScene(TCGApplication tcgApplication) {
        // Call constructor from parent class
        super(new BorderPane(), 1000, 600);

        // Root initializing
        BorderPane root = (BorderPane) getRoot();

        // HBox of the top row menu
        topMenu = new TopMenu(this);

        // Shop page
        ShopPane shopPane = new ShopPane();


        // Temp
        usernameLabel = new Label("Username: " + username);
        passwordLabel = new Label("Password: " + password);
        adminLabel = new Label("Admin: " + (isAdmin ? "Admin" : "User"));
        Button backButton = new Button("Back");
        backButton.setOnAction(_ -> tcgApplication.setPrimaryStage(tcgApplication.getLoginScene()));
        VBox vBox = new VBox();
        vBox.getChildren().addAll(usernameLabel, passwordLabel, adminLabel, backButton);

        // Root scene config
        root.setTop(topMenu);
        root.setCenter(shopPane);
        root.setStyle("-fx-background-color: #EEEEEE");
    }

    // Getter method ( is admin ? )
    public boolean getIsAdmin() {
        return isAdmin;
    }

    // Setter method ( account details )
    public void setUserInformation(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.topMenu.setUsernameLabel(username);

        // Temp code
        usernameLabel.setText(username);
        passwordLabel.setText(password);
        adminLabel.setText(isAdmin ? "Admin" : "User");
    }
}
