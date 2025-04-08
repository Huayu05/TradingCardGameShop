package tcgshop.main.shop;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import tcgshop.TCGApplication;
import tcgshop.main.MainScene;

public class CartPane extends GridPane {
    // Dynamic node
    private CartTotal cartTotal;

    public CartPane(TCGApplication tcgApplication, MainScene mainScene) {
        // Call constructor from parent
        super();

        // Cart list build
        CartList cartList = new CartList();
        this.add(cartList, 0, 0);

        cartTotal = new CartTotal(tcgApplication, mainScene);
        this.add(cartTotal, 1, 0);

        // Column constraint assign
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(70);
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);

        // Grid pane config
        this.getColumnConstraints().addAll(col1, col2);
    }


    // Getter method ( Cart Total )
    public CartTotal getCartTotal() {
        return cartTotal;
    }
}
