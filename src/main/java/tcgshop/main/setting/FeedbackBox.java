package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import tcgshop.TCGApplication;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class FeedbackBox extends VBox {
    public FeedbackBox(TCGApplication tcgApplication, ArrayList<Object> feedback) {
        super();

        Timestamp ts = (Timestamp) feedback.get(2);
        LocalDateTime ldt = ts.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM d, h.mma", Locale.ENGLISH);
        String formatted = ldt.format(formatter);
        Label detailsLabel = new Label(feedback.get(1).toString() + "  @  " + formatted);
        detailsLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 16;"
        );
        Label feedbackLabel = new Label(feedback.getFirst().toString());
        feedbackLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );

        this.getChildren().addAll(detailsLabel, feedbackLabel);
        VBox.setMargin(this, new Insets(20, 20, 0, 20));
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(5);
        this.setStyle(
                "-fx-background-color: #EEEEEE;" +
                        "-fx-background-radius: 20px;"+
                        "-fx-effect: dropshadow(gaussian, #0000006F, 15, 0.3, 0, 0);"
        );
    }
}
