package tcgshop.authentication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import tcgshop.TCGApplication;

import java.util.function.UnaryOperator;

public class LogInPane extends GridPane {
    // Dynamic nodes
    private Label errorRespond;
    private TextField username;
    private PasswordField password;

    // Constructor
    public LogInPane(TCGApplication tcgApplication) {
        // Call constructor from parent class
        super();

        // Text or password field limiter
        int maxLength = 20;
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.getControlNewText().length() > maxLength) {
                return null; // Reject the change
            }
            return change;
        };

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
        username.setTextFormatter(new TextFormatter<>(filter));
        VBox.setMargin(username, new Insets(0, 0, 10, 0));

        // Password input password field
        password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(200);
        password.setTextFormatter(new TextFormatter<>(filter));
        VBox.setMargin(password, new Insets(0, 0, 25, 0));

        // Submit button for login
        Button logInButton = new Button("Log In");
        logInButton.setMinWidth(100);
        logInButton.setOnAction(_ -> logIn(tcgApplication));
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

        // Column constraints config
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);

        // Log in pane config
        this.getColumnConstraints().addAll(col1, col2);
        this.setPadding(new Insets(20));
        this.setMinHeight(300);
        this.setMaxHeight(300);
    }


    // Login method
    public void logIn(TCGApplication tcgApplication) {
        String[] data = new String[]{username.getText(), password.getText()};
        if (data[0].isEmpty() || data[1].isEmpty()) {
            errorRespond.setText("Please fill all information!");
        } else {
            int userType = tcgApplication.getSQLConnector().login(data[0], data[1]);
            if (userType != -1) {
                reset();
                tcgApplication.resetMainScene();
                tcgApplication.setUserInformation(data[0], data[1], userType == 1);
                tcgApplication.setPrimaryStage(tcgApplication.getShopScene());
            } else {
                errorRespond.setText("Wrong username or password!");
            }
        }
    }


    // Reset all input field
    public void reset() {
        username.setText("");
        password.setText("");
        errorRespond.setText("");
    }
}