package tcgshop.main.setting;

import javafx.geometry.Insets;
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
        }

        Region spacer = new Region();
        spacer.setMinHeight(20);
        allFeedback.getChildren().add(spacer);

        ScrollPane scrollPane = new ScrollPane(allFeedback);
        scrollPane.setStyle("-fx-background-color: #FFFFFF");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        allFeedback.prefWidthProperty().bind(scrollPane.widthProperty());

        StackPane.setMargin(this, new Insets(10));
        this.getChildren().add(scrollPane);
        this.setStyle("-fx-background-color: #FFFFFF;");
    }
}
