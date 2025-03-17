package tcgshop;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class SignInPane extends GridPane {
    // Class Attributes
    private Pane blank;
    private VBox signInVBox;
    private StackPane signInStackPane;

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

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(200);
        VBox.setMargin(username, new Insets(0, 0, 10, 0));

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(200);
        VBox.setMargin(password, new Insets(0, 0, 10, 0));

        ComboBox<String> role = new ComboBox<>();
        role.setPromptText(" - Choose Your Role - ");
        role.getItems().addAll("User", "Admin");
        role.setMaxWidth(200);
        VBox.setMargin(role, new Insets(0, 0, 10, 0));

        Button signInButton = new Button("Sign in");
        VBox.setMargin(signInButton, new Insets(10, 0, 0, 0));


        Label errorRespond = new Label();

        // signInVBox Config
        signInVBox = new VBox();
        signInVBox.getChildren().addAll(topic, subtopic, username, password, role, signInButton, errorRespond);
        signInVBox.setAlignment(Pos.CENTER);


        // signInStackPane Config
        signInStackPane = new StackPane();
        signInStackPane.getChildren().add(signInVBox);
        signInStackPane.prefHeightProperty().bind(this.prefHeightProperty());
        this.add(signInStackPane, 1, 0);

        // SignInPane config, with a blank at left
        blank = new Pane();
        this.add(blank, 0, 0);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().addAll(col1, col2);

        this.setPadding(new Insets(20));
        this.setMinHeight(300);
        this.setMaxHeight(300);


        // Button Functions
        signInButton.setOnAction(e -> {
            // button function
            errorRespond.setText("Just testing");
        });
    }
}
