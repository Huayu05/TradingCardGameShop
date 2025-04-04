package tcgshop.main.shop;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import tcgshop.TCGApplication;

public class ShopPane extends GridPane {
    // Dynamic nodes
    private TCGApplication tcgApplication;
    private ItemBar itemBar;

    public ShopPane(TCGApplication tcgApplication) {
        // Call constructor from parent
        super();

        // Initialize root
        this.tcgApplication = tcgApplication;

        // Build category bar
        CategoryBar catBar = new CategoryBar(tcgApplication, this);
        this.add(catBar, 0, 0);

        // Build item bar
        itemBar = new ItemBar(tcgApplication, "All");
        this.add(itemBar, 1, 0);

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


    // Item bar reset method
    public void resetItemBar(String category) {
        this.getChildren().remove(itemBar);
        this.itemBar = new ItemBar(tcgApplication, category);
        this.add(itemBar, 1, 0);
    }
}
