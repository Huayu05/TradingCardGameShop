package tcgshop.main.shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tcgshop.main.MainScene;

import java.util.ArrayList;

public class CartList extends VBox {
    public CartList(MainScene mainScene) {
        // Call constructor from parent
        super();

        ArrayList<ItemBox> items = mainScene.getShopPane().getItems();

        // Shadow config
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.rgb(0, 0, 0, 0.3));
        innerShadow.setRadius(8);
        innerShadow.setChoke(0.5);
        innerShadow.setOffsetX(0);
        innerShadow.setOffsetY(0);
        innerShadow.setRadius(0);

        // Flow pane config
        VBox vBox = new VBox(10);

        // Add items pane
        for (ItemBox item : items) {
            if (item.getItemChosen() > 0) {
                CartItem cartItem = new CartItem(mainScene, item);
                vBox.getChildren().add(cartItem);
            }
        }

        // Add message if the list was empty
        if (vBox.getChildren().isEmpty()) {
            Label nothingLabel = new Label("No Items Available now");
            nothingLabel.setStyle(
                    "-fx-font-weight: bold;" +
                            "-fx-font-size: 18;" +
                            "-fx-font-family: verdana;"
            );
            vBox.getChildren().add(nothingLabel);
        }

        // Scroll pane config
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
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

        // Category bar config
        this.getChildren().add(scrollPaneWrapper);
        GridPane.setMargin(this, new Insets(30, 30, 30, 50));
        GridPane.setVgrow(this, Priority.ALWAYS);
        GridPane.setHgrow(this, Priority.ALWAYS);
        this.setMaxWidth(Double.MAX_VALUE);
        this.setAlignment(Pos.TOP_CENTER);
    }
}
