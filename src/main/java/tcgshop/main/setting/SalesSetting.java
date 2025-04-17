package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tcgshop.TCGApplication;

import java.util.ArrayList;

public class SalesSetting extends VBox {
    // Dyanamic nodes
    private TCGApplication tcgApplication;
    private StackPane showPane;
    private HBox optionBar;
    private StackPane categorySales;
    private StackPane userAmount;

    public SalesSetting(TCGApplication tcgApplication) {
        super();

        this.tcgApplication = tcgApplication;

        Label option1 = new Label("Items Sales Amount");
        option1.setOnMouseClicked(e -> setItemSales());
        Label option2 = new Label("Category Sales Amount");
        Label option3 = new Label("User Amount");

        optionBar = new HBox(10, option1, option2, option3);

        showPane = new StackPane();
        Label title = new Label("Sales");
        showPane.getChildren().add(title);

        this.getChildren().addAll(optionBar, showPane);
        StackPane.setMargin(this, new Insets(20));
    }

    public void setItemSales() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Item Name");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Sales Amount");
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);
        yAxis.setForceZeroInRange(true);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Items Sales Amount");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("");

        for (ArrayList<Object> item : tcgApplication.getSQLConnector().getItemSales()) {
            int amount = (Integer) item.get(1);
            series1.getData().add(new XYChart.Data<>(item.getFirst().toString(), amount));
        }
        barChart.getData().addAll(series1);
        barChart.setLegendVisible(false);

        ComboBox<String> comboBox = new ComboBox<>();
        for (String category : tcgApplication.getSQLConnector().getCategory()) {
            comboBox.getItems().add(category);
        }
        optionBar.getChildren().add(comboBox);

        StackPane itemSales = new StackPane();
        itemSales.getChildren().add(barChart);
        showPane.getChildren().clear();
        showPane.getChildren().add(itemSales);
    }
}
