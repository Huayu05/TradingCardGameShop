package tcgshop.main.shop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tcgshop.TCGApplication;
import tcgshop.main.MainScene;
import tcgshop.utils.GeneralFunction;

import java.util.ArrayList;

public class CartTotal extends VBox {
    // Dynamic nodes
    private TCGApplication tcgApplication;
    private MainScene mainScene;
    private Label subtotal;
    private Label taxLabel;
    private Label tax;
    private Label discountLabel;
    private Label discount;
    private Label total;

    public CartTotal(TCGApplication tcgApplication, MainScene mainScene) {
        // Call constructor from parent
        super();

        // Initialize dynamic nodes
        this.mainScene = mainScene;
        this.tcgApplication = tcgApplication;

        // Title
        Label title = new Label("Bill Summary");
        title.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 26px;" +
                "-fx-text-fill: #00ADB5;"
        );
        this.getChildren().add(title);

        // Subtotal spacer setup
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        // Subtotal row
        Label subtotalLabel = new Label("Subtotal (RM) :");
        subtotalLabel.setStyle("-fx-text-fill: #FFFFFF;-fx-font-size: 14px;");
        subtotal = new Label("12345678.00");
        subtotal.setStyle("-fx-text-fill: #FFFFFF;-fx-font-size: 14px;");
        HBox subtotalBox = new HBox(subtotalLabel, spacer1, subtotal);
        VBox.setMargin(subtotalBox, new Insets(20, 0, 0, 0));
        this.getChildren().add(subtotalBox);

        // Discount spacer
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        // Discount row
        discountLabel = new Label("Discount :");
        discountLabel.setStyle("-fx-text-fill: #FFFFFF;-fx-font-size: 14px;");
        discount = new Label("-12345678.00");
        discount.setStyle("-fx-text-fill: #FFFFFF;-fx-font-size: 14px;");
        HBox discountBox = new HBox(discountLabel, spacer2, discount);
        HBox.setHgrow(discountBox, Priority.ALWAYS);
        this.getChildren().add(discountBox);

        // Tax spacer setup
        Region spacer3 = new Region();
        HBox.setHgrow(spacer3, Priority.ALWAYS);

        // Tax row
        taxLabel = new Label("Tax (6%) :");
        taxLabel.setStyle("-fx-text-fill: #FFFFFF;-fx-font-size: 14px;");
        tax = new Label("12345678.00");
        tax.setStyle("-fx-text-fill: #FFFFFF;-fx-font-size: 14px;");
        HBox taxBox = new HBox(taxLabel, spacer3, tax);
        this.getChildren().add(taxBox);

        // Seperator and spacer
        Region spacer4 = new Region();
        VBox.setVgrow(spacer4, Priority.ALWAYS);
        this.getChildren().add(spacer4);
        Separator separator = new Separator();
        VBox.setVgrow(separator, Priority.ALWAYS);
        this.getChildren().add(separator);

        // Total spacer setup
        Region spacer5 = new Region();
        HBox.setHgrow(spacer5, Priority.ALWAYS);

        // Total row
        Label totalLabel = new Label("Total :");
        totalLabel.setStyle("-fx-text-fill: #FFFFFF;-fx-font-size: 14px;");
        total = new Label("12345678.00");
        total.setStyle("-fx-text-fill: #FFFFFF;-fx-font-size: 14px;");
        HBox totalBox = new HBox(totalLabel, spacer5, total);
        this.getChildren().add(totalBox);

        // Pay button
        Button pay = new Button("Pay Now");
        pay.setOnAction(e -> payment());
        this.getChildren().add(pay);
        pay.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: #EEEEEE;" +
                "-fx-text-fill: #222831;" +
                "-fx-background-radius: 8px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.5, 0, 0);"
        );
        pay.setOnMouseEntered(_ -> pay.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: #DDDDDD;" +
                "-fx-text-fill: #222831;" +
                "-fx-background-radius: 8px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.45), 10, 0.6, 0, 0);"
        ));
        pay.setOnMouseExited(_ -> pay.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: #DDDDDD;" +
                "-fx-text-fill: #222831;" +
                "-fx-background-radius: 8px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.5, 0, 0);"
        ));

        // Return button
        Button goBack = new Button("Back to Shop");
        goBack.setOnAction(e -> {
           mainScene.getShopPane().setVisible(true);
           mainScene.getCartPane().setVisible(false);
        });
        this.getChildren().add(goBack);
        goBack.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: #EEEEEE;" +
                "-fx-text-fill: #222831;" +
                "-fx-background-radius: 8px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.5, 0, 0);"
        );
        goBack.setOnMouseEntered(_ -> goBack.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: #DDDDDD;" +
                "-fx-text-fill: #222831;" +
                "-fx-background-radius: 8px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.45), 10, 0.6, 0, 0);"
        ));
        goBack.setOnMouseExited(_ -> goBack.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: #DDDDDD;" +
                "-fx-text-fill: #222831;" +
                "-fx-background-radius: 8px;" +
                "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.5, 0, 0);"
        ));

        // Category bar config
        GridPane.setMargin(this, new Insets(40, 30, 40, 30));
        GridPane.setVgrow(this, Priority.ALWAYS);
        GridPane.setHgrow(this, Priority.ALWAYS);
        this.setMaxWidth(350);
        this.setPadding(new Insets(20));
        VBox.setMargin(pay, new Insets(20, 0, 0, 0));
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle(
                "-fx-background-color: #393E46;" +
                "-fx-background-radius: 20px;" +
                "-fx-effect: dropshadow(gaussian, #0000006F, 20, 0.5, 0, 0);"
        );
    }


    // Refresh all amount
    public void refreshAmount() {
        double subtotal = mainScene.getShopPane().refreshCart();
    }


    // Pop out window
    public void payment() {
        ArrayList<ItemBox> itemPurchase = new ArrayList<>();
        for (ItemBox item : mainScene.getShopPane().getItems()) {
            if (item.getItemChosen() > 0) {
                itemPurchase.add(item);
            }
        }
        if (itemPurchase.isEmpty()) {
            popupWindow(false);
            return;
        }
        int billID = tcgApplication.getSQLConnector().createBill(tcgApplication.getUsername());
        for (ItemBox item : itemPurchase) {
            tcgApplication.getSQLConnector().deductItem(item, billID);
        }

        popupWindow(true);

        mainScene.getShopPane().setVisible(true);
        mainScene.getCartPane().setVisible(false);
        mainScene.getShopPane().clearItem();
        mainScene.resetAll();
        mainScene.getShopPane().getCatBar().refocusAll();
    }


    // Popup method
    public void popupWindow(boolean valid) {
        HBox popUpBox = new HBox(20);
        popUpBox.setStyle("-fx-background-color: #EEEEEE;");
        popUpBox.setPrefSize(300, 100);
        popUpBox.setAlignment(Pos.CENTER);
        Image image = valid ? new Image("file:src/main/resources/tcgshop/images/green_tick.png") : new Image("file:src/main/resources/tcgshop/images/red_cross.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        Label label = valid ? new Label("Payment Successful!") : new Label("Payment Failed!");
        label.setStyle(
                "-fx-font-family: verdana;" +
                "-fx-text-fill: #000000;" +
                "-fx-font-size: 14;"
        );
        Button button = new Button(valid ? "Back to Home" : "Back to Payment");
        VBox vBox = new VBox(10, label, button);
        vBox.setAlignment(Pos.CENTER);
        popUpBox.getChildren().addAll(imageView, vBox);
        Scene popUpScene = new Scene(popUpBox);
        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setScene(popUpScene);
        popUp.setResizable(false);
        popUp.setTitle(valid ? "Payment Successful" : "Payment Failed");
        button.setOnAction(_ -> popUp.close());
        popUp.showAndWait();
    }


    // Setter method ( Subtotal )
    public void setSubtotal(double subtotal) {
        this.subtotal.setText(GeneralFunction.twoDecimalPlaces(subtotal));
        int discountPercent = GeneralFunction.loadDiscount();
        this.discountLabel.setText("Discount (" + discountPercent + "%) :");
        this.discount.setText(GeneralFunction.twoDecimalPlaces(-1 * subtotal * GeneralFunction.loadDiscount() / 100));
        int taxPercent = GeneralFunction.loadTax();
        this.taxLabel.setText("Tax (" + taxPercent + "%) :");
        double taxAfterDisc = (subtotal - (subtotal * discountPercent / 100)) * taxPercent / 100;
        this.tax.setText(GeneralFunction.twoDecimalPlaces(taxAfterDisc));
        this.total.setText(GeneralFunction.twoDecimalPlaces(subtotal - (subtotal * GeneralFunction.loadDiscount() / 100) + taxAfterDisc));
    }
}
