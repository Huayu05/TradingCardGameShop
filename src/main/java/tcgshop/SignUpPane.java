package tcgshop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.function.UnaryOperator;

public class SignUpPane extends GridPane {
    // Nodes needed interact from outside class
    private Button signUpButton;
    private Label errorRespond;
    private TextField username;
    private PasswordField password;
    private ComboBox<String> role;

    // Constructor
    public SignUpPane() {
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
        Label title = new Label("Create an Account");
        title.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 34;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #4A3B2F;" +
                "-fx-effect: dropshadow(gaussian, #0000004F, 3, 0, 1, 1)"
        );
        VBox.setMargin(title, new Insets(0, 0, 10, 0));

        // Subtitle label
        Label subtitle = new Label("Let's begin to start your journey at here!");
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
        VBox.setMargin(password, new Insets(0, 0, 10, 0));

        // User type combo box
        role = new ComboBox<>();
        role.setPromptText(" - Choose Your Role - ");
        role.getItems().addAll("User", "Admin");
        role.setMaxWidth(200);
        VBox.setMargin(role, new Insets(0, 0, 25, 0));

        // Submit button for sign up
        signUpButton = new Button("Sign Up");
        signUpButton.setMinWidth(100);
        signUpButton.setStyle(
            "-fx-font-size: 12px;" + 
            "-fx-background-radius: 5px;"
        );
        VBox.setMargin(signUpButton, new Insets(0, 0, 5, 0));


        // Respond label for button click
        errorRespond = new Label();
        errorRespond.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-text-fill: #FF0000;"
        );

        // signUpVBox Config
        VBox signUpVBox = new VBox();
        signUpVBox.getChildren().addAll(title, subtitle, username, password, role, signUpButton, errorRespond);
        signUpVBox.setAlignment(Pos.CENTER);


        // signInStackPane Config
        StackPane signInStackPane = new StackPane();
        signInStackPane.getChildren().add(signUpVBox);
        signInStackPane.prefHeightProperty().bind(this.prefHeightProperty());
        this.add(signInStackPane, 1, 0);

        // Blank pane for align right
        Pane blank = new Pane();
        this.add(blank, 0, 0);

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
        return new String[] {username.getText(), password.getText(), role.getValue()};
    }

    // Reset all input
    public void reset() {
        username.setText("");
        password.setText("");
        role.getSelectionModel().clearSelection();
        role.setValue(null);
        role.setPromptText(" - Choose Your Role - ");
        errorRespond.setText("");
    }

    // Getter Method ( Sign Up Button )
    public Button getSignUpButton() {
        return signUpButton;
    }
    // Getter Method ( Error Respond )
    public Label getErrorRespond() {
        return errorRespond;
    }
}
