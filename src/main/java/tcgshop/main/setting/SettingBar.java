package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tcgshop.TCGApplication;

public class SettingBar extends VBox {
    // Dynamic nodes
    private TCGApplication tcgApplication;
    private StackPane base;

    public SettingBar(TCGApplication tcgApplication) {
        // Call constructor from parent
        super();

        // Dynamic setup
        this.tcgApplication = tcgApplication;

        // Shadow config
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.rgb(0, 0, 0, 0.3));
        innerShadow.setRadius(8);
        innerShadow.setChoke(0.5);
        innerShadow.setOffsetX(0);
        innerShadow.setOffsetY(0);
        innerShadow.setRadius(0);

        // Stack pane config
        base = new StackPane();
        base.setEffect(innerShadow);
        base.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(base, Priority.ALWAYS);
        base.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.5, 0, 0);"
        );

        // Base stack pane in wrapper clipping
        Rectangle clip = new Rectangle();
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        base.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
            clip.setWidth(newVal.getWidth());
            clip.setHeight(newVal.getHeight());
        });
        base.setClip(clip);

        // Add message if the list was empty
        if (base.getChildren().isEmpty()) {
            Label nothingLabel = new Label("   Choose an option   ");
            nothingLabel.setStyle(
                    "-fx-font-weight: bold;" +
                            "-fx-text-fill: black;" +
                            "-fx-font-size: 18;" +
                            "-fx-font-family: verdana;"
            );
            base.getChildren().add(nothingLabel);
            StackPane.setMargin(nothingLabel, new Insets(20, 20, 0, 20));
        }

        // Root config
        GridPane.setMargin(this, new Insets(30));
        this.getChildren().add(base);
        this.setPadding(new Insets(20, 0, 20, 0));
        this.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(this, Priority.ALWAYS);
    }


    // Change setting by index
    public void settingChosen(int index) {
        base.getChildren().clear();
        switch (index) {
            case 0:
                base.getChildren().add(new HistorySetting(tcgApplication));
                break;
            case 1:
                base.getChildren().add(new PasswordSetting(tcgApplication));
                break;
            case 2:
                base.getChildren().add(new FeedbackSetting(tcgApplication));
                break;
            case 3:
                base.getChildren().add(new ItemSetting(tcgApplication));
                break;
            case 4:
                base.getChildren().add(new SalesSetting(tcgApplication));
                break;
            case 5:
                base.getChildren().add(new AccountSetting(tcgApplication));
                break;
            case 6:
                base.getChildren().add(new FeedbackShow(tcgApplication));
                break;
            case 7:
                base.getChildren().add(new DiscountSetting(tcgApplication));
                break;
            default:
                break;
        }
    }
}
