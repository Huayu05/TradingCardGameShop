package tcgshop.main.shop;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class ItemBox extends VBox {
    // Item information
    private int itemLeft;
    private int itemChosen = 0;

    public ItemBox(ArrayList<Object> item) {
        // Call constructor from parent
        super();

        // Save item details
        this.itemLeft = (int) item.get(3);

        // Item picture
        Image image = new Image("file:src/main/resources/tcgshop/images/items/" + item.get(1) + ".png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(120);
        imageView.setFitHeight(160);
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        imageView.setClip(clip);

        // Item left label
        Label itemLeft = new Label("Item Left : " + item.get(3));
        itemLeft.setTextAlignment(TextAlignment.CENTER);
        itemLeft.setMaxWidth(120);
        itemLeft.setMaxHeight(20);
        itemLeft.setAlignment(Pos.CENTER);
        itemLeft.setStyle(
                "-fx-text-fill: white;" +
                "-fx-background-color: #393E46;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: Verdana;" +
                "-fx-background-radius:10;"
        );

        // Stack pane for both image and item left
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, itemLeft);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.setMaxSize(120, 160);
        this.getChildren().add(stackPane);
        stackPane.setStyle(
                "-fx-border-radius:20;" +
                "-fx-border-color: #393E46;" +
                "-fx-border-width: 5;"
        );

        // Item name
        Label itemName = new Label((String) item.get(1));
        this.getChildren().add(itemName);
        itemName.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-font-size: 14;"
        );

        // Item price
        Label itemPrice = new Label("RM" + item.get(2));
        this.getChildren().add(itemPrice);
        itemPrice.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-font-size: 14;"
        );

        // Buttons to add or remove with a text field
        HBox hBox = createCountBox();
        this.getChildren().add(hBox);

        // VBox config
        this.setMaxSize(150, 250);
        this.setMinSize(150, 250);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(0);
    }


    // Create a hbox of - + and a text field
    public HBox createCountBox() {
        // Nodes initialize
        Button minusButton = new Button("-");
        minusButton.setMaxSize(30, 30);
        Button plusButton = new Button("+");
        plusButton.setMaxSize(30, 30);
        TextField textField = new TextField();
        textField.setMaxSize(30, 30);
        textField.setText("0");  // Default value

        // Set the text field to only accept integers and validate range
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {  // Allow only digits
                textField.setText(oldValue);
            }
            else {
                try {
                    int value = Integer.parseInt(newValue);
                    if (value < 0 || value > itemLeft) {  // Ensure value is within the range 0-100
                        textField.setText(oldValue);  // Revert to the old value if out of range
                    }
                    if (value == itemLeft) {
                        plusButton.setDisable(true);
                        minusButton.setDisable(false);
                    }
                    if (value == 0) {
                        plusButton.setDisable(false);
                        minusButton.setDisable(true);
                    }
                }
                catch (NumberFormatException e) {
                    textField.setText(oldValue);  // Revert to the old value if invalid number
                }
            }
        });

        // Minus button action
        minusButton.setOnAction(e -> {
            itemChosen = Integer.parseInt(textField.getText());
            if (itemChosen > 0) {
                textField.setText(String.valueOf(itemChosen - 1));
                plusButton.setDisable(false);
            }
            if (itemChosen == 1) {
                minusButton.setDisable(true);
            }
        });

        // Plus button action
        plusButton.setOnAction(e -> {
            itemChosen = Integer.parseInt(textField.getText());
            if (itemChosen < itemLeft) {
                textField.setText(String.valueOf(itemChosen + 1));
                minusButton.setDisable(false);
            }
            if (itemChosen == itemLeft - 1) {
                plusButton.setDisable(true);
            }
        });
        HBox hBox = new HBox(minusButton, textField, plusButton);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }
}
