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

public class SignUpPane extends GridPane {
    // Dynamic nodes
    private TextField username;
    private PasswordField password;
    private PasswordField confirmPassword;
    private Label errorRespond;

    // Constructor
    public SignUpPane(TCGApplication tcgApplication) {
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
        Label title = new Label("Create Account");
        title.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 34;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #393E46;" +
                "-fx-effect: dropshadow(gaussian, #0000004F, 3, 0, 1, 1)"
        );
        VBox.setMargin(title, new Insets(0, 0, 10, 0));

        // Subtitle label
        Label subtitle = new Label("Let's get started! Your journey begins here!");
        subtitle.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-text-fill: #393E46;" +
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
        VBox.setMargin(password, new Insets(0, 0, 10, 0));

        // Password confirmation input password field
        confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");
        confirmPassword.setMaxWidth(200);
        confirmPassword.setTextFormatter(new TextFormatter<>(filter));
        VBox.setMargin(confirmPassword, new Insets(0, 0, 25, 0));

        // Submit button for sign up
        Button signUpButton = new Button("Sign Up");
        signUpButton.setMinWidth(100);
        signUpButton.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-text-fill: #EEEEEE;" +
                        "-fx-background-color: #393E46;" +
                        "-fx-effect: dropshadow(gaussian, #0000004F, 10, 0, 1, 1);"
        );
        signUpButton.setOnMouseEntered(_ -> signUpButton.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-text-fill: #EEEEEE;" +
                        "-fx-background-color: #393E46BF;" +
                        "-fx-effect: dropshadow(gaussian, #0000004F, 6, 0, 1, 1);"
        ));
        signUpButton.setOnMouseExited(_ -> signUpButton.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-text-fill: #EEEEEE;" +
                        "-fx-background-color: #393E46;" +
                        "-fx-effect: dropshadow(gaussian, #0000004F, 10, 0, 1, 1);"
        ));
        signUpButton.setOnAction(_ -> signUp(tcgApplication));
        VBox.setMargin(signUpButton, new Insets(0, 0, 5, 0));


        // Respond label for button click
        errorRespond = new Label();
        errorRespond.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-text-fill: #FF0000;"
        );

        // signUpVBox Config
        VBox signUpVBox = new VBox();
        signUpVBox.getChildren().addAll(title, subtitle, username, password, confirmPassword, signUpButton, errorRespond);
        signUpVBox.setAlignment(Pos.CENTER);

        // signInStackPane Config
        StackPane signInStackPane = new StackPane();
        signInStackPane.getChildren().add(signUpVBox);
        signInStackPane.prefHeightProperty().bind(this.prefHeightProperty());
        this.add(signInStackPane, 1, 0);

        // Blank pane for align right
        Pane blank = new Pane();
        this.add(blank, 0, 0);

        // Column constraint assign
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);

        // Sign up pane config
        this.getColumnConstraints().addAll(col1, col2);
        this.setPadding(new Insets(20));
        this.setMinHeight(300);
        this.setMaxHeight(300);
    }


    // Sign up method
    public void signUp(TCGApplication tcgApplication) {
        String[] data = new String[] {username.getText(), password.getText()};
        if (data[0].isEmpty() || data[1].isEmpty() || confirmPassword.getText().isEmpty()) {
            errorRespond.setText("Please fill in all the information!");
        }
        else if (!password.getText().equals(confirmPassword.getText())) {
            errorRespond.setText("Password doesn't match!");
        }
        else {
            if (tcgApplication.getSQLConnector().addUser(data[0], data[1])) {
                tcgApplication.setUserInformation(data[0],data[1],false);
                reset();
                tcgApplication.resetMainScene();
                tcgApplication.setPrimaryStage(tcgApplication.getShopScene());
            }
            else {
                errorRespond.setText("Username exists!");
            }
        }
    }


    // Reset all input nodes
    public void reset() {
        username.setText("");
        password.setText("");
        errorRespond.setText("");
    }
}
