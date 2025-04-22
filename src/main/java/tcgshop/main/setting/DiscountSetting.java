package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

        Label title = new Label("Tax and Discount Setting");
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
        taxPercentField.setMaxWidth(200);
        taxPercentField.setPromptText("0 to 100 only");
        taxPercentField.setTextFormatter(new TextFormatter<>(filter));

        Label discountPercentLabel = new Label("Discount Percentage (%): ");
        discountPercentLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );

        discountPercentField = new TextField();
        discountPercentField.setMaxWidth(200);
        discountPercentField.setPromptText("0 to 100 only");
        discountPercentField.setTextFormatter(new TextFormatter<>(filter));

        Button submit = new Button("Change Tax / Discount");
        Label errorLabel = new Label("");

        submit.setOnAction(e -> {
            if (!(taxPercentField.getText().isEmpty() || discountPercentField.getText().isEmpty())) {
                GeneralFunction.saveTax(Integer.parseInt(taxPercentField.getText()));
                GeneralFunction.saveDiscount(Integer.parseInt(discountPercentField.getText()));
                errorLabel.setText("Tax and Discount Setting changed!");
                errorLabel.setStyle(
                        "-fx-font-family: verdana;" +
                                "-fx-text-fill: green;" +
                                "-fx-font-size: 10;"
                );
            }
            else {
                errorLabel.setText("Invalid input!");
                errorLabel.setStyle(
                        "-fx-font-family: verdana;" +
                                "-fx-text-fill: red;" +
                                "-fx-font-size: 10;"
                );
            }
        });



        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(taxPercentLabel, 0, 0);
        grid.add(taxPercentField, 1, 0);
        grid.add(discountPercentLabel, 0, 1);
        grid.add(discountPercentField, 1, 1);

        VBox mainBox = new VBox(20, title, grid, submit, errorLabel);
        mainBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(mainBox);
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}
