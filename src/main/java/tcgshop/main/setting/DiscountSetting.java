package tcgshop.main.setting;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import tcgshop.TCGApplication;
import tcgshop.utils.GeneralFunction;

import java.util.function.UnaryOperator;

public class DiscountSetting extends StackPane {
    // Dynamic nodes
    private TextField taxPercentField;
    private TextField discountPercentField;
    public DiscountSetting(TCGApplication tcgApplication) {
        super();

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty()) {
                return change;
            }
            try {
                int value = Integer.parseInt(newText);
                if (value >= 0 && value <= 100) {
                    return change;
                }
            }
            catch (NumberFormatException e) {
                //
            }
            return null;
        };

        Label title = new Label("Change Tax and Discount Percentage Here ");
        title.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );
        title.setAlignment(Pos.CENTER);
        GridPane.setMargin(title, new Insets(10));

        Label taxPercentLabel = new Label("Tax Percentage (%): ");
        taxPercentLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );

        taxPercentField = new TextField();
        taxPercentField.setPromptText("0 to 100 only");
        taxPercentField.setTextFormatter(new TextFormatter<>(filter));

        Label discountPercentLabel = new Label("Discount Percentage (%): ");
        discountPercentLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );

        discountPercentField = new TextField();
        discountPercentField.setPromptText("0 to 100 only");
        discountPercentField.setTextFormatter(new TextFormatter<>(filter));

        Button submit = new Button("Change Tax / Discount");
        submit.setOnAction(e -> {
            GeneralFunction.saveTax(Integer.parseInt(taxPercentField.getText()));
            GeneralFunction.saveDiscount(Integer.parseInt(discountPercentField.getText()));
        });
        GridPane.setHalignment(submit, HPos.CENTER);
        GridPane.setValignment(submit, VPos.CENTER);
        GridPane.setMargin(submit, new Insets(10));


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(title, 0, 0, 2, 1);
        grid.add(taxPercentLabel, 0, 1);
        grid.add(taxPercentField, 1, 1);
        grid.add(discountPercentLabel, 0, 2);
        grid.add(discountPercentField, 1, 2);
        grid.add(submit, 0, 3, 2, 1);

        this.getChildren().addAll(grid);
        StackPane.setAlignment(grid, Pos.CENTER);
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}
