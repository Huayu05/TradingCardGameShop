package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HistoryBox extends VBox {
    public HistoryBox() {
        super();

        Label dateTime = new Label("*Date Time*");
        dateTime.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 14;"
        );
        Label itemOne = new Label("*ItemOne*");
        itemOne.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );

        this.getChildren().addAll(dateTime, itemOne);
        VBox.setMargin(this, new Insets(20, 20, 0, 20));
        this.setStyle(
                "-fx-background-color: #EEEEEE;" +
                        "-fx-background-radius: 20px;"+
                        "-fx-effect: dropshadow(gaussian, #0000006F, 15, 0.3, 0, 0);"
        );
    }
}
