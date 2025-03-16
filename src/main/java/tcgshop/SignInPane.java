package tcgshop;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class SignInPane extends GridPane {
    // Constructor
    public SignInPane() {
        // Call constructor from parent class
        super();

        Pane blank = new Pane();
        VBox signInVBox = new VBox();
        this.add(blank, 0, 0);
        this.add(signInVBox, 1, 0);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().addAll(col1, col2);

        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: white;");


        Label topic = new Label("Create an Account");
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        ChoiceBox<String> role = new ChoiceBox<String>();
        role.getItems().addAll("User", "Admin");
        signInVBox.getChildren().addAll(topic, username, password, role);


    }
}
