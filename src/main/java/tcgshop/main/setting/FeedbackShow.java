package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tcgshop.TCGApplication;

import java.util.ArrayList;

public class FeedbackShow extends VBox {
    public FeedbackShow(TCGApplication tcgApplication) {
        super();

        VBox allFeedback = new VBox();
        allFeedback.setStyle("-fx-background-color: #FFFFFF;");

        ArrayList<ArrayList<Object>> feedbacks = tcgApplication.getSQLConnector().getFeedback();
        if (!feedbacks.isEmpty()) {
            for (ArrayList<Object> feedback : feedbacks) {
                FeedbackBox feedbackBox = new FeedbackBox(tcgApplication, feedback);
                feedbackBox.prefWidthProperty().bind(allFeedback.widthProperty());
                allFeedback.getChildren().add(feedbackBox);
            }
            Region spacer = new Region();
            spacer.setMinHeight(20);
            allFeedback.getChildren().add(spacer);
        }
        else {
            Label noFeedbackLabel = new Label("No Feedback Available Now");
            noFeedbackLabel.setPadding(new Insets(20));
            noFeedbackLabel.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 14;"
            );
            allFeedback.getChildren().add(noFeedbackLabel);
        }



        ScrollPane scrollPane = new ScrollPane(allFeedback);
        scrollPane.setStyle("-fx-background-color: #FFFFFF");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        allFeedback.prefWidthProperty().bind(scrollPane.widthProperty());

        Label title = new Label("Feedback From Users");
        title.setPadding(new Insets(20, 20, 0, 20));
        title.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );

        VBox mainBox = new VBox(title, scrollPane);

        StackPane.setMargin(this, new Insets(10));
        this.getChildren().add(mainBox);
        this.setStyle("-fx-background-color: #FFFFFF;");
    }
}
