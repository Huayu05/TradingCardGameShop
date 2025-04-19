package tcgshop.main.shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import tcgshop.main.MainScene;
import tcgshop.utils.GeneralFunction;

import java.io.File;
import java.util.Objects;

public class CartItem extends HBox {
    // Items information
    private int itemChosen;
    private double itemPrice;

    // Dynamic node
    private Label priceLabel;
    private Label priceCalculate;

    public CartItem(MainScene mainScene, ItemBox itemBox) {
        // Call constructor from parent
        super(20);

        String itemName = itemBox.getItemName();
        itemChosen = itemBox.getItemChosen();
        itemPrice = itemBox.getItemPrice();
        int itemLeft = itemBox.getItemLeft();

        // Item picture
        String imagePath = null;
        File dir = new File(System.getProperty("user.dir")+"\\item_pictures\\");
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().contains(itemBox.getItemName())) {
                File path = new File(dir, file.getName());
                imagePath = path.getAbsolutePath();
            }
        }
        Image image = new Image(Objects.requireNonNullElse(imagePath, "file:src/main/resources/tcgshop/images/no_image.png"));        ImageView imageView = new ImageView(image);
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

        // Item name label
        Label itemNameLabel = new Label(itemName);
        itemNameLabel.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-text-fill: #000000;" +
                "-fx-font-size: 18;"
        );

        // Item chose edit
        Button minusButton = new Button("-");
        minusButton.setMinSize(20, 20);
        minusButton.setMaxSize(20, 20);
        minusButton.setStyle("-fx-font-size: 10;");
        Button plusButton = new Button("+");
        plusButton.setMinSize(20, 20);
        plusButton.setMaxSize(20, 20);
        plusButton.setStyle("-fx-font-size: 10;");
        TextField textField = new TextField();
        textField.setText(String.valueOf(itemChosen));
        textField.setMaxSize(25, 20);
        textField.setText(itemChosen + "");
        textField.setStyle("-fx-font-size: 10;");
        if (itemChosen == 0) {
            minusButton.setDisable(true);
        }
        if (itemChosen == itemLeft) {
            plusButton.setDisable(true);
        }

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
                    itemBox.editItemChosen(itemChosen);
                    refreshPrice();
                    mainScene.getShopPane().refreshCart();
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
                itemBox.editItemChosen(itemChosen);
                refreshPrice();
                mainScene.getShopPane().refreshCart();
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
                itemBox.editItemChosen(itemChosen);
                refreshPrice();
                mainScene.getShopPane().refreshCart();
                textField.setText(String.valueOf(itemChosen));
                minusButton.setDisable(false);
            }
            if (itemChosen == itemLeft) {
                plusButton.getScene().getRoot().requestFocus();
                plusButton.setDisable(true);
            }
        });

        HBox itemCountBox = new HBox(minusButton, textField, plusButton);
        itemCountBox.setSpacing(10);
        itemCountBox.setAlignment(Pos.CENTER);


        // Item name and count
        VBox nameVBox = new VBox(10);
        nameVBox.setAlignment(Pos.CENTER_LEFT);
        nameVBox.getChildren().addAll(itemNameLabel, itemCountBox);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Price label
        priceLabel = new Label("Price (RM) : " + GeneralFunction.twoDecimalPlaces(itemChosen * itemPrice));
        priceLabel.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-text-fill: #000000;" +
                "-fx-font-size: 16;"
        );

        // Price calculation
        priceCalculate = new Label(itemChosen + " x RM" + GeneralFunction.twoDecimalPlaces(itemPrice));
        priceCalculate.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-text-fill: #000000;" +
                "-fx-font-size: 12;"
        );

        // Price box
        VBox priceBox = new VBox(5);
        priceBox.setAlignment(Pos.CENTER);
        priceBox.getChildren().addAll(priceLabel, priceCalculate);

        // This class config
        this.getChildren().addAll(stackPane, nameVBox, spacer, priceBox);
        this.setAlignment(Pos.CENTER);
        VBox.setMargin(this, new Insets(20, 20, 0, 20));
        this.setPadding(new Insets(10, 30, 10, 30));
        this.setStyle(
                "-fx-background-color: #EEEEEE;" +
                "-fx-background-radius: 20px;"+
                "-fx-effect: dropshadow(gaussian, #0000006F, 15, 0.3, 0, 0);"
        );
    }


    // Refresh price
    public void refreshPrice() {
        this.priceLabel.setText("Price (RM) : " + GeneralFunction.twoDecimalPlaces(itemChosen * itemPrice));
        this.priceCalculate.setText(itemChosen + " x RM" + GeneralFunction.twoDecimalPlaces(itemPrice));
    }
}
