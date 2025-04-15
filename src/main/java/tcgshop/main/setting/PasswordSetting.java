package tcgshop.main.setting;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import tcgshop.TCGApplication;

import java.util.function.UnaryOperator;

public class PasswordSetting extends StackPane {
    // Dynamic nodes
    private TCGApplication tcgApplication;
    private PasswordField oldPasswordField;
    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;
    private Label errorLabel;

    public PasswordSetting(TCGApplication tcgApplication) {
        super();

        this.tcgApplication = tcgApplication;

        // Text or password field limiter
        int maxLength = 20;
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.getControlNewText().length() > maxLength) {
                return null;
            }
            return change;
        };

        Label title = new Label("Change Your Password Here ");
        title.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );
        title.setAlignment(Pos.CENTER);
        GridPane.setMargin(title, new Insets(10));

        Label oldPasswordLabel = new Label("Old Password: ");
        oldPasswordLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );

        oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Old Password");
        oldPasswordField.setTextFormatter(new TextFormatter<>(filter));

        Label newPasswordLabel = new Label("New Password: ");
        newPasswordLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );

        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        newPasswordField.setTextFormatter(new TextFormatter<>(filter));

        Label confirmPasswordLabel = new Label("Confirm Password: ");
        confirmPasswordLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setTextFormatter(new TextFormatter<>(filter));

        Button submit = new Button("Change Password");
        submit.setOnAction(_ -> changePassword(oldPasswordField.getText(), newPasswordField.getText(), confirmPasswordField.getText() ));
        GridPane.setHalignment(submit, HPos.CENTER);
        GridPane.setValignment(submit, VPos.CENTER);
        GridPane.setMargin(submit, new Insets(10));

        errorLabel = new Label("");
        GridPane.setHalignment(errorLabel, HPos.CENTER);
        GridPane.setValignment(errorLabel, VPos.CENTER);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(title, 0, 0, 2, 1);
        grid.add(oldPasswordLabel, 0, 1);
        grid.add(oldPasswordField, 1, 1);
        grid.add(newPasswordLabel, 0, 2);
        grid.add(newPasswordField, 1, 2);
        grid.add(confirmPasswordLabel, 0, 3);
        grid.add(confirmPasswordField, 1, 3);
        grid.add(submit, 0, 4, 2, 1);
        grid.add(errorLabel, 0, 5, 2, 1);

        this.getChildren().addAll(grid);
        StackPane.setAlignment(grid, Pos.CENTER);
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }


    // Button action
    public void changePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (!tcgApplication.getPassword().equals(oldPassword)) {
            errorLabel.setText("Old password does not match");
            errorLabel.setStyle(
                    "-fx-font-family: verdana;" +
                    "-fx-text-fill: red;" +
                    "-fx-font-size: 10;"
            );
        }
        else if (!newPassword.equals(confirmPassword)) {
            errorLabel.setText("New password does not match");
            errorLabel.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-text-fill: red;" +
                            "-fx-font-size: 10;"
            );
        }
        else if (newPassword.equals(oldPassword)) {
            errorLabel.setText("New password same with old password");
            errorLabel.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-text-fill: red;" +
                            "-fx-font-size: 10;"
            );
        }
        else {
            if (tcgApplication.getSQLConnector().changePassword(tcgApplication.getUsername(), newPassword)) {
                tcgApplication.setUserInformation(tcgApplication.getUsername(), newPassword, tcgApplication.isAdmin());
                errorLabel.setText("Password changed");
                errorLabel.setStyle(
                        "-fx-font-family: verdana;" +
                                "-fx-text-fill: green;" +
                                "-fx-font-size: 10;"
                );
                oldPasswordField.setText("");
                newPasswordField.setText("");
                confirmPasswordField.setText("");
            }
        }
    }
}
