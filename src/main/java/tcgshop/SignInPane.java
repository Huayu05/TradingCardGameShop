package tcgshop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class SignInPane extends GridPane {
    private Button signInButton;
    private Label errorRespond;
    private TextField username;
    private PasswordField password;
    private ComboBox<String> role;

    // Constructor
    public SignInPane() {
        // Call constructor from parent class
        super();


        // Contents in the signInVBox
        Label topic = new Label("Create an Account");
        topic.setFont(Font.font("Verdana", 30));
        VBox.setMargin(topic, new Insets(20, 0, 5, 0));

        Label subtopic = new Label("Let's begin to start your journey at here!");
        subtopic.setFont(Font.font("Verdana", 10));
        VBox.setMargin(subtopic, new Insets(0, 0, 30, 0));

        username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(200);
        VBox.setMargin(username, new Insets(0, 0, 10, 0));

        password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(200);
        VBox.setMargin(password, new Insets(0, 0, 10, 0));

        role = new ComboBox<>();
        role.setPromptText(" - Choose Your Role - ");
        role.getItems().addAll("User", "Admin");
        role.setMaxWidth(200);
        VBox.setMargin(role, new Insets(0, 0, 10, 0));

        signInButton = new Button("Sign In");
        VBox.setMargin(signInButton, new Insets(10, 0, 0, 0));


        errorRespond = new Label();
        errorRespond.setStyle("-fx-text-fill: red;");

        // signInVBox Config
        VBox signInVBox = new VBox();
        signInVBox.getChildren().addAll(topic, subtopic, username, password, role, signInButton, errorRespond);
        signInVBox.setAlignment(Pos.CENTER);


        // signInStackPane Config
        StackPane signInStackPane = new StackPane();
        signInStackPane.getChildren().add(signInVBox);
        signInStackPane.prefHeightProperty().bind(this.prefHeightProperty());
        this.add(signInStackPane, 1, 0);

        // SignInPane config, with a blank at left
        // Class Attributes
        Pane blank = new Pane();
        this.add(blank, 0, 0);

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

    // Getter Method for Button
    public Button getSignInButton() {
        return signInButton;
    }
    // Getter Method for Respond
    public Label getErrorRespond() {
        return errorRespond;
    }
}
