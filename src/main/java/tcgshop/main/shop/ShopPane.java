package tcgshop.main.shop;

import com.sun.tools.javac.Main;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import tcgshop.TCGApplication;
import tcgshop.main.MainScene;

import java.util.ArrayList;

public class ShopPane extends GridPane {
    // Items information
    private ArrayList<ItemBox> items = new ArrayList<>();

    // Dynamic nodes
    private TCGApplication tcgApplication;
    private ItemBar itemBar;
    private CartBar cartBar;
    private VBox vBox;

    public ShopPane(TCGApplication tcgApplication, MainScene mainScene) {
        // Call constructor from parent
        super();

        // Initialize root
        this.tcgApplication = tcgApplication;

        // Build category bar
        CategoryBar catBar = new CategoryBar(tcgApplication, this);
        this.add(catBar, 0, 0);

        // Build item bar and cart bar in a Vbox
        itemBar = new ItemBar(tcgApplication, this, "All");
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        cartBar = new CartBar(mainScene, this);
        vBox = new VBox();
        vBox.getChildren().addAll(itemBar, spacer, cartBar);
        GridPane.setMargin(vBox, new Insets(30));
        this.add(vBox, 1, 0);

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
        this.getChildren().remove(vBox);
        itemBar = new ItemBar(tcgApplication, this, category);
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        vBox = new VBox();
        vBox.getChildren().addAll(itemBar, spacer, cartBar);
        GridPane.setMargin(vBox, new Insets(30));
        this.add(vBox, 1, 0);
    }


    // Add items
    public void addItem(ItemBox item) {
        items.add(item);
    }


    // Refresh total price and count
    public void refreshCart() {
        double subtotal = 0;
        int itemCount = 0;
        for (ItemBox item : items) {
            itemCount += item.getItemChosen();
            subtotal += (item.getItemChosen() * item.getItemPrice());
        }
        this.cartBar.setItemChosen(itemCount);
        this.cartBar.setSubtotal(subtotal);
    }
}
