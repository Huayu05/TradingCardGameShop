package tcgshop.main.setting;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tcgshop.TCGApplication;

import java.util.ArrayList;

public class SalesSetting extends VBox {
    // Dyanamic nodes
    private TCGApplication tcgApplication;
    private StackPane showPane;
    private HBox optionBar;
    private ComboBox<String> comboBox = null;

    public SalesSetting(TCGApplication tcgApplication) {
        super();

        this.tcgApplication = tcgApplication;

        Label option1 = new Label("Items Sales Amount");
        option1.setOnMouseClicked(e -> setItemSales("All"));
        Separator separator1 = new Separator(Orientation.VERTICAL);
        HBox.setHgrow(separator1, Priority.ALWAYS);
        Label option2 = new Label("Category Sales Amount");
        option2.setOnMouseClicked(e -> setCategorySales());
        Separator separator2 = new Separator(Orientation.VERTICAL);
        HBox.setHgrow(separator2, Priority.ALWAYS);
        Label option3 = new Label("User Amount");
        option3.setOnMouseClicked(e -> setUserAmount());
        Separator separator3 = new Separator(Orientation.VERTICAL);
        HBox.setHgrow(separator3, Priority.ALWAYS);

        optionBar = new HBox(10, option1, separator1, option2, separator2, option3, separator3);

        Separator separatorMain = new Separator();
        VBox.setVgrow(separatorMain, Priority.ALWAYS);
        showPane = new StackPane();
        VBox.setVgrow(showPane, Priority.ALWAYS);

        this.getChildren().addAll(optionBar, separatorMain, showPane);
        StackPane.setMargin(this, new Insets(20));
    }

    public void setItemSales(String categoryStr) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Item Name");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Sales Amount");
        yAxis.setTickUnit(1);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Items Sales Amount");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Item Count");
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Item Sales (RM)");

        barChart.setAnimated(false);

        for (ArrayList<Object> item : tcgApplication.getSQLConnector().getItemSales(categoryStr)) {
            int amount = (Integer) item.get(1);
            double sales = (Double) item.get(2);
            series1.getData().add(new XYChart.Data<>(item.getFirst().toString(), amount));
            series2.getData().add(new XYChart.Data<>(item.getFirst().toString(), sales));
        }



        barChart.getData().addAll(series1);
        barChart.getData().addAll(series2);

        if (comboBox == null) {
            comboBox = new ComboBox<>();
            comboBox.getItems().add("All");
            for (String category : tcgApplication.getSQLConnector().getCategory()) {
                comboBox.getItems().add(category);
            }
            comboBox.setValue("All");
            comboBox.setOnAction(_ -> setItemSales(comboBox.getValue()));
            optionBar.getChildren().add(comboBox);
        }

        StackPane itemSales = new StackPane();
        itemSales.getChildren().add(barChart);
        showPane.getChildren().clear();
        showPane.getChildren().add(itemSales);
    }


    public void setCategorySales() {
        optionBar.getChildren().remove(comboBox);
        comboBox = null;
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category Name");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Sales Amount");
        yAxis.setTickUnit(1);
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return (object.doubleValue() % 1 == 0) ? String.valueOf(object.intValue()) : "";
            }
        });

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Category Sales Amount");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("");

        for (ArrayList<Object> item : tcgApplication.getSQLConnector().getCategorySales()) {
            int amount = (Integer) item.get(1);
            series1.getData().add(new XYChart.Data<>(item.getFirst().toString(), amount));
        }
        barChart.getData().addAll(series1);
        barChart.setLegendVisible(false);

        StackPane categorySales = new StackPane();
        categorySales.getChildren().add(barChart);
        showPane.getChildren().clear();
        showPane.getChildren().add(categorySales);
    }

    public void setUserAmount() {
        optionBar.getChildren().remove(comboBox);
        comboBox = null;
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("User Amount");
        yAxis.setTickUnit(1);
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return (object.doubleValue() % 1 == 0) ? String.valueOf(object.intValue()) : "";
            }
        });

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Total User Amount");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("");

        int userAmount = 0;
        for (ArrayList<Object> date : tcgApplication.getSQLConnector().getUserDate()) {
            userAmount += (Integer) date.get(1);
            series1.getData().add(new XYChart.Data<>(date.getFirst().toString(), userAmount));
        }
        lineChart.getData().addAll(series1);
        lineChart.setLegendVisible(false);

        StackPane userTotal = new StackPane();
        userTotal.getChildren().add(lineChart);
        showPane.getChildren().clear();
        showPane.getChildren().add(userTotal);
    }
}
