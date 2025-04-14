package tcgshop.main.shop;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tcgshop.TCGApplication;

import java.util.ArrayList;

public class ItemBar extends VBox {
    public ItemBar(TCGApplication tcgApplication, ShopPane shopPane, ArrayList<ItemBox> oldItemBoxArrayList, String category) {
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

        // Shadow config
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.rgb(0, 0, 0, 0.3));
        innerShadow.setRadius(8);
        innerShadow.setChoke(0.5);
        innerShadow.setOffsetX(0);
        innerShadow.setOffsetY(0);
        innerShadow.setRadius(0);

        // Flow pane config
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);


        // Add item boxes into the list by retrieve from MySQL
        ArrayList<ArrayList<Object>> itemList = tcgApplication.getSQLConnector().getItems(category);
        for (ArrayList<Object> item : itemList) {
            ItemBox itemBox = null;
            for (ItemBox oldItemBox : oldItemBoxArrayList) {
                if (oldItemBox.getItemName().equals(item.get(1).toString())) {
                    itemBox = oldItemBox;
                    break;
                }
            }
            if (itemBox != null) {
                flowPane.getChildren().add(itemBox);
            }
            else {
                itemBox = new ItemBox(shopPane, item);
                flowPane.getChildren().add(itemBox);
            }
        }

        // Add message if the list was empty
        if (flowPane.getChildren().isEmpty()) {
            Label nothingLabel = new Label("   No Items Available now   ");
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
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        // Stack pane config
        StackPane scrollPaneWrapper = new StackPane();
        scrollPaneWrapper.getChildren().add(scrollPane);
        scrollPaneWrapper.setEffect(innerShadow);
        scrollPaneWrapper.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(scrollPaneWrapper, Priority.ALWAYS);
        scrollPaneWrapper.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.5, 0, 0);"
        );

        // Scroll pane in wrapper clipping
        Rectangle clip = new Rectangle();
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        scrollPaneWrapper.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
            clip.setWidth(newVal.getWidth());
            clip.setHeight(newVal.getHeight());
        });
        scrollPaneWrapper.setClip(clip);

        // Root config
        VBox.setMargin(this, new Insets(0, 30, 0, 0));
        this.getChildren().add(scrollPaneWrapper);
        this.setPadding(new Insets(20, 0, 20, 0));
        this.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(this, Priority.ALWAYS);
    }
}
