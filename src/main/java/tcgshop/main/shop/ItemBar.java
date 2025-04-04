package tcgshop.main.shop;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import tcgshop.TCGApplication;

import java.util.ArrayList;

public class ItemBar extends VBox {
    public ItemBar(TCGApplication tcgApplication, String category) {
        // Call constructor from parent
        super();

        // Category label
        Label title = new Label("Items");
        title.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 30px;" +
                "-fx-text-fill: #00ADB5;"
        );
        this.getChildren().add(title);

        // Flow pane config
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setStyle("-fx-background-color: #EEEEEE;");

        // Add item boxes into the list by retrieve from MySQL
        ArrayList<ArrayList<Object>> itemList = tcgApplication.getSQLConnector().getItems(category);
        for (ArrayList<Object> item : itemList) {
            ItemBox itemBox = new ItemBox(item);
            flowPane.getChildren().add(itemBox);
        }

        // Add message if the list was empty
        if (flowPane.getChildren().isEmpty()) {
            Label nothingLabel = new Label("No Items Available now");
            nothingLabel.setStyle(
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 18;" +
                    "-fx-font-family: verdana;"
            );
            flowPane.getChildren().add(nothingLabel);
        }

        // Scroll pane config
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(flowPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle(
                "-fx-background-color: #EEEEEE;" +
                "-fx-background-radius: 20;"
        );

        // Root config
        this.getChildren().add(scrollPane);
        GridPane.setMargin(this, new Insets(20));
        this.setPadding(new Insets(20));
        this.setSpacing(20);
    }
}
