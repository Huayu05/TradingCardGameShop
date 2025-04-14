package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tcgshop.TCGApplication;

public class SettingBar extends VBox {
    public SettingBar(TCGApplication tcgApplication) {
        // Call constructor from parent
        super();

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

        // Add message if the list was empty
        if (flowPane.getChildren().isEmpty()) {
            Label nothingLabel = new Label("   Choose an option   ");
            nothingLabel.setStyle(
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: black;" +
                    "-fx-font-size: 18;" +
                    "-fx-font-family: verdana;"
            );
            flowPane.getChildren().add(nothingLabel);
            FlowPane.setMargin(nothingLabel, new Insets(20, 20, 0, 20));
        }


        // Stack pane config
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(flowPane);
        stackPane.setEffect(innerShadow);
        stackPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(stackPane, Priority.ALWAYS);
        stackPane.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.5, 0, 0);"
        );

        // Scroll pane in wrapper clipping
        Rectangle clip = new Rectangle();
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        stackPane.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
            clip.setWidth(newVal.getWidth());
            clip.setHeight(newVal.getHeight());
        });
        stackPane.setClip(clip);

        // Root config
        GridPane.setMargin(this, new Insets(30));
        this.getChildren().add(stackPane);
        this.setPadding(new Insets(20, 0, 20, 0));
        this.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(this, Priority.ALWAYS);
    }
}
