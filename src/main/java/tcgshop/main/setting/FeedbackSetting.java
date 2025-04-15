package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tcgshop.TCGApplication;

import java.util.function.UnaryOperator;

public class FeedbackSetting extends StackPane {
    public FeedbackSetting(TCGApplication tcgApplication) {
        super();

        // Text or password field limiter
        int maxLength = 750;
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.getControlNewText().length() > maxLength) {
                return null;
            }
            return change;
        };

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        StackPane.setMargin(vbox, new Insets(30));

        Label title = new Label("Feedback to Administrator");
        title.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        TextArea textArea = new TextArea();
        textArea.setPromptText("* Minimum 10 characters & Maximum 750 characters *");
        textArea.setWrapText(true);
        textArea.setTextFormatter(new TextFormatter<>(filter));
        textArea.setStyle("-fx-effect: innershadow(gaussian, gray, 5, 0.4, 0, 0);");
        vbox.getChildren().addAll(titleBox, textArea);

        Button submit = new Button("  Submit  ");
        HBox submitBox = new HBox(submit);
        submitBox.setAlignment(Pos.CENTER_RIGHT);
        vbox.getChildren().addAll(submitBox);

        Label response = new Label("");
        HBox respondBox = new HBox(response);
        respondBox.setAlignment(Pos.CENTER_RIGHT);
        vbox.getChildren().addAll(respondBox);

        submit.setOnAction(_ -> {
            if (tcgApplication.getSQLConnector().addFeedback(textArea.getText(), tcgApplication.getUsername())) {
                textArea.setText("");
                response.setText("Feedback Submitted");
                response.setStyle(
                        "-fx-font-family: verdana;" +
                                "-fx-text-fill: green;" +
                                "-fx-font-size: 10;"
                );
            }
            else {
                response.setText("Invalid Feedback");
                response.setStyle(
                        "-fx-font-family: verdana;" +
                                "-fx-text-fill: red;" +
                                "-fx-font-size: 10;"
                );
            }

        });

        this.getChildren().addAll(vbox);
        this.setAlignment(Pos.CENTER);
    }



}
