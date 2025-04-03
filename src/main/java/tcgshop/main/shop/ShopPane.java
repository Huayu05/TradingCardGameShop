package tcgshop.main.shop;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ShopPane extends GridPane {
    public ShopPane() {
        // Call constructor from parent
        super();

        // Build category bar
        CategoryBar catBar = new CategoryBar();
        this.add(catBar, 0, 0);

        // Column constraint assign
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);

        // Grid pane config
        this.getColumnConstraints().addAll(col1, col2);

    }
}
