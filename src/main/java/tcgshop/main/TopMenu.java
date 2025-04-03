package tcgshop.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class TopMenu extends HBox {
    // Dynamic nodes
    private Label usernameLabelTwo;

    public TopMenu(MainScene mainScene) {
        // Call constructor from parent
        super();

        // Shop name vbox
        Label shopNameOne = new Label("HY Trading Card Game");
        Label shopNameTwo = new Label("Online Shop");
        VBox shopNameBox = new VBox(shopNameOne, shopNameTwo);
        shopNameBox.setAlignment(Pos.CENTER_LEFT);
        shopNameOne.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 20;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #EEEEEE;"
        );
        shopNameTwo.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 20;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #EEEEEE;"
        );

        // Hbox spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Username welcome vbox
        Label usernameLabelOne = new Label("Welcome Back,");
        usernameLabelTwo = new Label("Anonymous ");
        VBox usernameBox = new VBox(usernameLabelOne, usernameLabelTwo);
        usernameBox.setAlignment(Pos.CENTER_RIGHT);
        usernameLabelOne.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-text-fill: #EEEEEE;"
        );
        usernameLabelTwo.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-text-fill: #EEEEEE;"
        );

        // Account options dropdown button
        Button dropboxButton = createDropdownButton(mainScene.getIsAdmin());
        dropboxButton.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 20;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: #EEEEEE;" +
                "-fx-text-fill: #222831;" +
                "-fx-background-radius: 8px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.5, 0, 0);"
        );

        // HBox config
        this.setPadding(new Insets(0, 40, 0, 40));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        this.setMinHeight(80);
        this.setMaxHeight(120);
        this.getChildren().addAll(shopNameBox, spacer, usernameBox, dropboxButton);
        this.setStyle("-fx-background-color: #222831;");
        this.prefHeightProperty().bind(mainScene.heightProperty().multiply(0.2));

    }

    private Button createDropdownButton(boolean isAdmin) {
        Button dropboxButton = new Button("≡");

        // Create MenuItems for the dropdown
        MenuItem option1 = new MenuItem("Purchase History");
        option1.setOnAction(_ -> System.out.println("Option 1 clicked"));
        MenuItem option2 = new MenuItem("Reset Password");
        option2.setOnAction(_ -> System.out.println("Option 2 clicked"));
        ContextMenu contextMenu = new ContextMenu(option1, option2);
        contextMenu.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-weight: normal;" +
                "-fx-font-size: 12;" +
                "-fx-background-fill: #EEEEEE;"
        );

        // If is admin add more option
        if (isAdmin) {
            MenuItem option3 = new MenuItem("Option 3");
            option3.setOnAction(_ -> System.out.println("Option 3 clicked"));
            contextMenu.getItems().add(option3);
        }

        // Show the ContextMenu when the button is clicked
        dropboxButton.setOnAction(_ -> {
            Window window = dropboxButton.getScene().getWindow();

            contextMenu.show(dropboxButton, 0, 0);
            double menuWidth = contextMenu.getWidth();
            contextMenu.hide();

            double xPos = window.getX() + dropboxButton.getLayoutX() + dropboxButton.getWidth() - menuWidth/1.3;
            double yPos = window.getY() + dropboxButton.getLayoutY() + dropboxButton.getHeight()*2;


            contextMenu.show(dropboxButton, xPos, yPos);
        });

        return dropboxButton;
    }

    public void setUsernameLabel(String username) {
        this.usernameLabelTwo.setText(username + " ");
    }
}
