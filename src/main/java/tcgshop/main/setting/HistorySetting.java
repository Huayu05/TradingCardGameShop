package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HistorySetting extends StackPane {
    public HistorySetting() {
        super();

        VBox vBox = new VBox();
        vBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        vBox.setStyle("-fx-background-color: #FFFFFF;");

        for (int i=0; i<15; i++) {
            vBox.getChildren().add(new HistoryBox());
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setStyle("-fx-background-color: #FFFFFF;");


        this.getChildren().add(scrollPane);
        StackPane.setMargin(this, new Insets(10));
        this.setStyle("-fx-background-color: #FFFFFF;");
    }
}
