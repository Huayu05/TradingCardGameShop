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
import tcgshop.utils.GeneralFunction;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ItemBox extends VBox {
    // Item information
    private  String itemName;
    private double itemPrice;
    private int itemLeft;
    private int itemChosen = 0;

    // Dynamic nodes
    private ShopPane shopPane;
    private TextField textField;
    private Button plusButton;
    private Button minusButton;

    public ItemBox(ShopPane shopPane, ArrayList<Object> item) {
        // Call constructor from parent
        super();

        // Save item details and add to array list in parent
        this.itemName = (String) item.get(1);
        this.itemPrice = (double) item.get(2);
        this.itemLeft = (int) item.get(3);
        this.shopPane = shopPane;
        shopPane.addItem(this);

        // Out of stock image
        Image noStock = new Image("file:src/main/resources/tcgshop/images/out_of_stock.png");
        ImageView noStockView = new ImageView(noStock);
        noStockView.setFitHeight(100);
        noStockView.setFitWidth(100);

        // Item picture
        String imagePath = null;
        File dir = new File(System.getProperty("user.dir")+"\\item_pictures\\");
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().contains(item.get(1).toString())) {
                File path = new File(dir, file.getName());
                imagePath = path.getAbsolutePath();
            }
        }
        Image image = new Image(Objects.requireNonNullElse(imagePath, "file:src/main/resources/tcgshop/images/no_image.png"));
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
        itemLeft.setAlignment(Pos.BOTTOM_CENTER);
        itemLeft.setStyle(
                "-fx-text-fill: white;" +
                "-fx-background-color: #393E46;" +
                "-fx-font-size: 12;" +
                "-fx-font-family: Verdana;" +
                "-fx-background-radius:10;"
        );

        // Stack pane for both image and item left
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, itemLeft);
        if (this.itemLeft == 0) {
            stackPane.getChildren().add(noStockView);
        }
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
                "-fx-text-fill: #000000;" +
                "-fx-font-size: 14;"
        );

        // Item price
        Label itemPrice = new Label("RM" + GeneralFunction.twoDecimalPlaces(this.itemPrice));
        this.getChildren().add(itemPrice);
        itemPrice.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-text-fill: #000000;" +
                "-fx-font-size: 14;"
        );

        // Buttons to add or remove with a text field
        HBox hBox = createCountBox();
        this.getChildren().add(hBox);

        // VBox config
        this.setMaxSize(180, 250);
        this.setMinSize(180, 250);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(0);
    }


    // Create a hbox of - + and a text field
    public HBox createCountBox() {
        // Nodes initialize
        minusButton = new Button("-");
        minusButton.setMinSize(25, 25);
        minusButton.setMaxSize(25, 25);
        if (this.itemChosen == 0) {
            minusButton.setDisable(true);
        }
        plusButton = new Button("+");
        plusButton.setMinSize(25, 25);
        plusButton.setMaxSize(25, 25);
        if (this.itemChosen == itemLeft) {
            plusButton.setDisable(true);
        }
        textField = new TextField();
        textField.setMaxSize(30, 30);
        textField.setText("0");  // Default value

        // Set the text field to only accept integers and validate range
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                return;
            }
            if (!newValue.matches("\\d*")) {
                textField.setText(oldValue);
                return;
            }
            String cleaned = newValue.replaceFirst("^0+(?!$)", "");
            try {
                int value = Integer.parseInt(cleaned);
                if (value < 0 || value > itemLeft) {
                    textField.setText(oldValue);
                }
                else {
                    if (!newValue.equals(cleaned)) {
                        textField.setText(cleaned); // Update to cleaned version
                    }
                    itemChosen = value;
                    shopPane.refreshCart();
                    if (value == itemLeft) {
                        plusButton.setDisable(true);
                        minusButton.setDisable(false);
                    }
                    if (value == 0) {
                        plusButton.setDisable(false);
                        minusButton.setDisable(true);
                    }
                }
            }
            catch (NumberFormatException e) {
                textField.setText(oldValue);
            }
        });

        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                if (textField.getText().isEmpty()) {
                    textField.setText("0");
                }
            }
        });

        // Minus button action
        minusButton.setOnAction(e -> {
            itemChosen = Integer.parseInt(textField.getText());
            if (itemChosen > 0) {
                itemChosen --;
                shopPane.refreshCart();
                textField.setText(String.valueOf(itemChosen));
                plusButton.setDisable(false);
            }
            if (itemChosen == 0) {
                minusButton.getScene().getRoot().requestFocus();
                minusButton.setDisable(true);
            }
        });

        // Plus button action
        plusButton.setOnAction(e -> {
            itemChosen = Integer.parseInt(textField.getText());
            if (itemChosen < itemLeft) {
                itemChosen ++;
                shopPane.refreshCart();
                textField.setText(String.valueOf(itemChosen));
                minusButton.setDisable(false);
            }
            if (itemChosen == itemLeft) {
                plusButton.getScene().getRoot().requestFocus();
                plusButton.setDisable(true);
            }
        });
        HBox hBox = new HBox(minusButton, textField, plusButton);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }


    // Item count edit method
    public void editItemChosen(int count) {
        this.textField.setText(String.valueOf(count));
        if (count == itemLeft) {
            plusButton.setDisable(true);
        }
        if (count == 0) {
            minusButton.setDisable(true);
        }
        this.itemChosen = count;
    }


    // Getter method ( Name )
    public String getItemName() {
        return itemName;
    }


    // Getter method ( Item Left )
    public int getItemLeft() {
        return itemLeft;
    }


    // Getter method ( Quantity )
    public int getItemChosen() {
        return itemChosen;
    }


    // Getter method ( Price )
    public double getItemPrice() {
        return itemPrice;
    }
}
