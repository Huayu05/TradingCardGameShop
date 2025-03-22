package tcgshop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LogInPane extends GridPane {
    // Nodes needed interact from outside class
    private Button logInButton;
    private Label errorRespond;
    private TextField username;
    private PasswordField password;

    // Constructor
    public LogInPane() {
        // Call constructor from parent class
        super();

        // Topic label
        Label title = new Label("Log In Account");
        title.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 34;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #4A3B2F;" +
                "-fx-effect: dropshadow(gaussian, #0000004F, 3, 0, 1, 1)"
        );
        VBox.setMargin(title, new Insets(0, 0, 10, 0));

        // Subtitle label
        Label subtitle = new Label("Welcome back and continue your journey here!");
        subtitle.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 12;" +
                "-fx-text-fill: #4A3B2F;" +
                "-fx-effect: dropshadow(gaussian, #0000004F, 3, 0, 1, 1)"
        );
        VBox.setMargin(subtitle, new Insets(0, 0, 25, 0));

        // Username input text field
        username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(200);
        VBox.setMargin(username, new Insets(0, 0, 10, 0));

        // Password input password field
        password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(200);
        VBox.setMargin(password, new Insets(0, 0, 25, 0));

        // Submit button for login
        logInButton = new Button("Log In");
        logInButton.setMinWidth(100);
        logInButton.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 5px;"
        );
        VBox.setMargin(logInButton, new Insets(0, 0, 5, 0));

        // Respond label for button click
        errorRespond = new Label();
        errorRespond.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-text-fill: #FF0000;"
        );

        // logInVBox Config
        VBox logInVBox = new VBox();
        logInVBox.getChildren().addAll(title, subtitle, username, password, logInButton, errorRespond);
        logInVBox.setAlignment(Pos.CENTER);

        // logInStackPane Config
        StackPane logInStackPane = new StackPane();
        logInStackPane.getChildren().add(logInVBox);
        logInStackPane.prefHeightProperty().bind(this.prefHeightProperty());
        this.add(logInStackPane, 0, 0);

        // Blank pane for align left
        Pane blank = new Pane();
        this.add(blank, 1, 0);

        // GridPane config
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().addAll(col1, col2);
        this.setPadding(new Insets(20));
        this.setMinHeight(300);
        this.setMaxHeight(300);
    }

    // Retrieve user input
    public String[] getInput() {
        return new String[] {username.getText(), password.getText()};
    }

    // Reset all input
    public void reset() {
        username.setText("");
        password.setText("");
        errorRespond.setText("");
    }

    // Getter Method ( Log In Button )
    public Button getLogInButton() {
        return logInButton;
    }

    // Getter Method ( Error Respond Label )
    public Label getErrorRespond() {
        return errorRespond;
    }
}
