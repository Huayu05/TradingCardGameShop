package tcgshop;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LogInPane extends GridPane {
    // Class Attributes
    private Pane blank;
    private VBox logInVBox;
    private StackPane logInStackPane;

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

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(200);
        VBox.setMargin(username, new Insets(0, 0, 10, 0));

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(200);
        VBox.setMargin(password, new Insets(0, 0, 10, 0));

        Button logInButton = new Button("Log In");
        VBox.setMargin(logInButton, new Insets(10, 0, 0, 0));


        Label errorRespond = new Label();

        // logInVBox Config
        logInVBox = new VBox();
        logInVBox.getChildren().addAll(topic, subtopic, username, password, logInButton, errorRespond);
        logInVBox.setAlignment(Pos.CENTER);


        // logInStackPane Config
        logInStackPane = new StackPane();
        logInStackPane.getChildren().add(logInVBox);
        logInStackPane.prefHeightProperty().bind(this.prefHeightProperty());
        this.add(logInStackPane, 0, 0);

        // LogInPane config, with a blank at left
        blank = new Pane();
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


        // Button Functions
        logInButton.setOnAction(e -> {
            // button function
            errorRespond.setText("Just testing");
        });
    }

    public void displayLogIn() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(e -> {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), this);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);;
            fadeIn.play();
        });
        pause.play();
    }

    public void disappearLogIn() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), this);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.play();
    }
}
