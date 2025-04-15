package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tcgshop.TCGApplication;

import java.util.ArrayList;

public class HistorySetting extends StackPane {
    public HistorySetting(TCGApplication tcgApplication) {
        super();

        VBox vBox = new VBox(10);
        StackPane.setMargin(this, new Insets(10));
        vBox.setStyle("-fx-background-color: #FFFFFF;");

        ArrayList<ArrayList<ArrayList<Object>>> bills = tcgApplication.getSQLConnector().getBill(tcgApplication.getUsername());

        if (!bills.isEmpty()) {
            for (ArrayList<ArrayList<Object>> bill : bills) {
                vBox.getChildren().add(new HistoryBox(bill));
            }

            Region spacer = new Region();
            spacer.setMinHeight(20);
            vBox.getChildren().add(spacer);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vBox);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color: #FFFFFF;");
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            vBox.prefWidthProperty().bind(scrollPane.widthProperty());
            this.getChildren().add(scrollPane);
        }
        else {
            Label nothingLabel = new Label("   No Purchase History Found   ");
            nothingLabel.setStyle(
                    "-fx-font-weight: bold;" +
                            "-fx-text-fill: black;" +
                            "-fx-font-size: 18;" +
                            "-fx-font-family: verdana;"
            );
            this.getChildren().add(nothingLabel);
        }

        this.setStyle("-fx-background-color: #FFFFFF;");
    }
}
