package tcgshop.main.shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CartList extends VBox {
    public CartList() {
        // Call constructor from parent
        super();

        // Category bar config
        GridPane.setMargin(this, new Insets(30));
        GridPane.setVgrow(this, Priority.ALWAYS);
        GridPane.setHgrow(this, Priority.ALWAYS);
        this.setMaxWidth(350);
        this.setPadding(new Insets(20));
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);

    }
}
