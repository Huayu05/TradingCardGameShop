package tcgshop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class LogInPane extends GridPane {
    private Button logInButton;
    private Label errorRespond;
    private TextField username;
    private PasswordField password;

    // Constructor
    public LogInPane() {
        // Call constructor from parent class
        super();

        // Contents in the logInVBox
        Label topic = new Label("Log In Account");
        topic.setFont(Font.font("Verdana", 30));
        VBox.setMargin(topic, new Insets(20, 0, 5, 0));

        Label subtopic = new Label("Welcome back and ready to continue your journey!");
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

        logInButton = new Button("Log In");
        VBox.setMargin(logInButton, new Insets(10, 0, 0, 0));


        errorRespond = new Label();

        // logInVBox Config
        VBox logInVBox = new VBox();
        logInVBox.getChildren().addAll(topic, subtopic, username, password, logInButton, errorRespond);
        logInVBox.setAlignment(Pos.CENTER);


        // logInStackPane Config
        StackPane logInStackPane = new StackPane();
        logInStackPane.getChildren().add(logInVBox);
        logInStackPane.prefHeightProperty().bind(this.prefHeightProperty());
        this.add(logInStackPane, 0, 0);

        // LogInPane config, with a blank at left
        // Class Attributes
        Pane blank = new Pane();
        this.add(blank, 1, 0);

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

    // Getter Method for Button
    public Button getLogInButton() {
        return logInButton;
    }

    // Getter Method for Respond
    public Label getErrorRespond() {
        return errorRespond;
    }
}
