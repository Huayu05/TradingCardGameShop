package tcgshop.main.shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tcgshop.main.MainScene;

import java.text.DecimalFormat;

public class CartBar extends HBox {
    // Dynamic nodes
    private Label itemCount;
    private Label subtotal;

    public CartBar(MainScene mainScene, ShopPane shopPane) {
        // Call constructor from parent
        super();

        itemCount = new Label("Items Count: 0");
        itemCount.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;"
        );

        subtotal = new Label("Subtotal: RM0.00");
        subtotal.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;"
        );

        Button toCart = new Button("View Cart");
        toCart.setOnAction(e -> {
            shopPane.setVisible(false);
            mainScene.getCartPane().setVisible(true);
            mainScene.getCartPane().getCartTotal().refreshAmount();});
        toCart.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;"
        );

        VBox.setMargin(this, new Insets(0, 30, 0, 0));
        this.getChildren().addAll(itemCount, subtotal, toCart);
        this.setPadding(new Insets(10, 40, 10, 10));
        this.setSpacing(30);
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setMaxHeight(100);
        this.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.5, 0, 0);"
        );
    }


    // Setter method ( Item Count )
    public void setItemChosen(int itemChosen) {
        this.itemCount.setText("Items Count: " + itemChosen);
    }


    // Setter method ( Subtotal )
    public void setSubtotal(double subtotal) {
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        this.subtotal.setText("Subtotal: RM" + numberFormat.format(subtotal));
    }
}
