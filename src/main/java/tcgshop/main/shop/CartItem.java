package tcgshop.main.shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import tcgshop.TCGApplication;
import tcgshop.main.MainScene;

public class CartItem extends HBox {
    public CartItem(ItemBox itemBox) {
        // Call constructor from parent
        super();

        String itemName = itemBox.getItemName();
        int itemChosen = itemBox.getItemChosen();
        double itemPrice = itemBox.getItemPrice();

        // Item picture
        Image image = new Image("file:src/main/resources/tcgshop/images/items/" + itemName + ".png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(80);
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        imageView.setClip(clip);

        // Image border
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.setMaxSize(60, 80);
        stackPane.setStyle(
                "-fx-border-radius:20;" +
                        "-fx-border-color: #393E46;" +
                        "-fx-border-width: 5;"
        );

        // This class config
        this.getChildren().add(stackPane);
        VBox.setMargin(this, new Insets(20, 20, 0, 20));
        this.setPadding(new Insets(10));
        this.setStyle(
                "-fx-background-color: #EEEEEE;" +
                "-fx-background-radius: 20px");

    }
}
