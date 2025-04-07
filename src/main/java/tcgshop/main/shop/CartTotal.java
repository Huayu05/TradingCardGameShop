package tcgshop.main.shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CartTotal extends VBox {
    public CartTotal() {
        // Call constructor from parent
        super();

        // Title
        Label cartTotal = new Label("Bill Summary");
        this.getChildren().add(cartTotal);

        // Category bar config
        GridPane.setMargin(this, new Insets(40, 30, 40, 30));
        GridPane.setVgrow(this, Priority.ALWAYS);
        GridPane.setHgrow(this, Priority.ALWAYS);
        this.setMaxWidth(350);
        this.setPadding(new Insets(20));
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle(
                "-fx-background-color: #393E46;" +
                "-fx-background-radius: 20px;"
        );
    }
}
