package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tcgshop.utils.GeneralFunction;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryBox extends VBox {
    public HistoryBox(ArrayList<ArrayList<Object>> bill) {
        super();

        ArrayList<Object> billDetails = bill.getFirst();
        double tempSubtotal = 0;

        Timestamp ts = (Timestamp) billDetails.getFirst();
        LocalDateTime ldt = ts.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM d, h.mma", Locale.ENGLISH);
        String formatted = ldt.format(formatter);

        Label dateTime = new Label(formatted);
        dateTime.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 16;"
        );
        this.getChildren().addAll(dateTime);


        for (int i = 1; i < bill.size(); i++) {
            ArrayList<Object> itemDetails = bill.get(i);
            Label itemName = new Label((String) itemDetails.getFirst());
            itemName.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 12;"
            );
            Label quantity = new Label("(Quantity: " + itemDetails.get(1) + ")");
            quantity.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 12;"
            );
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            Label itemPrice = new Label(GeneralFunction.twoDecimalPlaces((Double) itemDetails.get(2) * (Integer) itemDetails.get(1)));
            itemPrice.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 12;"
            );
            tempSubtotal += (Double) itemDetails.get(2) * (Integer) itemDetails.get(1);
            HBox itemBox = new HBox(5);
            itemBox.getChildren().addAll(itemName,quantity, spacer, itemPrice);
            this.getChildren().add(itemBox);
        }

        Separator separator = new Separator();
        VBox.setVgrow(separator, Priority.ALWAYS);
        this.getChildren().add(separator);

        Label discountLabel = new Label("Discount (" + billDetails.get(1) + "%)");
        discountLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        Region discountSpacer = new Region();
        HBox.setHgrow(discountSpacer, Priority.ALWAYS);
        double discountPercent =  (double) (Integer) billDetails.get(1) / 100;
        Label discountPrice = new Label(GeneralFunction.twoDecimalPlaces(tempSubtotal * discountPercent));
        discountPrice.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        HBox discountBox = new HBox();
        discountBox.getChildren().addAll(discountLabel, discountSpacer, discountPrice);
        this.getChildren().add(discountBox);

        double taxPercent = (double) (Integer) billDetails.get(2) / 100;
        Label taxLabel = new Label("Tax (" + billDetails.get(2) + "%)");
        taxLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        Region taxSpacer = new Region();
        HBox.setHgrow(taxSpacer, Priority.ALWAYS);
        Label taxPrice = new Label(GeneralFunction.twoDecimalPlaces((tempSubtotal - (tempSubtotal * discountPercent)) * taxPercent));
        taxPrice.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        HBox taxBox = new HBox();
        taxBox.getChildren().addAll(taxLabel, taxSpacer, taxPrice);
        this.getChildren().add(taxBox);

        Label totalLabel = new Label("Total Payment");
        totalLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        Region totalSpacer = new Region();
        HBox.setHgrow(totalSpacer, Priority.ALWAYS);
        Label totalPrice = new Label(GeneralFunction.twoDecimalPlaces((tempSubtotal - (tempSubtotal * discountPercent)) * (taxPercent + 1)));
        totalPrice.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 14;"
        );
        HBox totalBox = new HBox();
        totalBox.getChildren().addAll(totalLabel, totalSpacer, totalPrice);
        this.getChildren().add(totalBox);

        VBox.setMargin(this, new Insets(20, 20, 0, 20));
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(5);
        this.setStyle(
                "-fx-background-color: #EEEEEE;" +
                        "-fx-background-radius: 20px;"+
                        "-fx-effect: dropshadow(gaussian, #0000006F, 15, 0.3, 0, 0);"
        );
    }
}
