package tcgshop.main.setting;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import tcgshop.TCGApplication;
import tcgshop.utils.GeneralFunction;

import java.util.ArrayList;

public class SalesSetting extends VBox {
    // Dynamic nodes
    private TCGApplication tcgApplication;
    private StackPane showPane;
    private HBox topBar;
    private ArrayList<Label> optionList = new ArrayList<>();
    private ComboBox<String> comboBox1 = null;
    private ComboBox<String> comboBox2 = null;
    private ComboBox<String> comboBox3 = null;

    public SalesSetting(TCGApplication tcgApplication) {
        super();

        this.tcgApplication = tcgApplication;

        Label option0 = new Label("Items Sales Count");
        option0.setOnMouseClicked(e -> {
            setItemSalesCount("All");
            resetStyle();
            option0.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 16;"
            );
        });

        Separator separator0 = new Separator(Orientation.VERTICAL);
        HBox.setHgrow(separator0, Priority.ALWAYS);
        Label option1 = new Label("Item Sales Amount");
        option1.setOnMouseClicked(e -> {
            setItemSalesAmount("All");
            resetStyle();
            option1.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 16;"
            );
        });
        Separator separator1 = new Separator(Orientation.VERTICAL);
        separator1.setMinHeight(30);
        HBox.setHgrow(separator1, Priority.ALWAYS);
        Label option2 = new Label("Category Sales Count");
        option2.setOnMouseClicked(e -> {
            setCategorySalesCount();
            resetStyle();
            option2.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 16;"
            );
        });
        Separator separator2 = new Separator(Orientation.VERTICAL);
        HBox.setHgrow(separator2, Priority.ALWAYS);
        Label option3 = new Label("Category Sales Amount");
        option3.setOnMouseClicked(e -> {
            setCategorySalesAmount();
            resetStyle();
            option3.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 16;"
            );
        });
        Separator separator3 = new Separator(Orientation.VERTICAL);
        HBox.setHgrow(separator3, Priority.ALWAYS);
        Label option4 = new Label("All Sales Details");
        option4.setOnMouseClicked(e -> {
            setSalesDetails(true);
            resetStyle();
            option4.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 16;"
            );
        });
        Separator separator4 = new Separator(Orientation.VERTICAL);
        HBox.setHgrow(separator4, Priority.ALWAYS);
        Label option5 = new Label("User Amount");
        option5.setOnMouseClicked(e -> {
            setUserAmount();
            resetStyle();
            option5.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 16;"
            );
        });

        optionList.add(option0);
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);
        optionList.add(option4);
        optionList.add(option5);

        resetStyle();
        option0.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 16;"
        );

        HBox optionBar = new HBox(10, option0, separator0, option1, separator1, option2, separator2, option3, separator3, option4, separator4, option5);
        optionBar.setAlignment(Pos.CENTER);
        optionBar.setPadding(new Insets(0, 0, 20, 0));
        optionBar.setStyle("-fx-background-color: #FFFFFF;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(optionBar);
        scrollPane.setStyle("-fx-background-color: #FFFFFF;");
        topBar = new HBox(10, scrollPane);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #FFFFFF;");
        topBar.setMinHeight(50);
        topBar.setMaxHeight(50);
        VBox.setVgrow(topBar, Priority.ALWAYS);

        Separator separatorMain = new Separator();
        separatorMain.setStyle("-fx-border-width: 5");
        VBox.setVgrow(separatorMain, Priority.ALWAYS);
        showPane = new StackPane();
        VBox.setVgrow(showPane, Priority.ALWAYS);

        setItemSalesCount("All");

        this.getChildren().addAll(topBar, separatorMain, showPane);
        this.setStyle("-fx-background-color: #FFFFFF;");
        StackPane.setMargin(this, new Insets(20));
    }

    public void resetStyle() {
        for (Label l : optionList) {
            l.setStyle(
                    "-fx-font-family: verdana;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-size: 16;"
            );
        }
    }

    public void setItemSalesCount(String categoryStr) {
        topBar.getChildren().remove(comboBox2);
        topBar.getChildren().remove(comboBox3);
        comboBox2 = null;
        comboBox3 = null;
        if (comboBox1 == null) {
            comboBox1 = new ComboBox<>();
            comboBox1.setMinWidth(100);
            comboBox1.getItems().add("All");
            for (String category : tcgApplication.getSQLConnector().getCategory()) {
                comboBox1.getItems().add(category);
            }
            comboBox1.setValue("All");
            comboBox1.setOnAction(_ -> setItemSalesCount(comboBox1.getValue()));
            topBar.getChildren().add(comboBox1);
        }

        BarChart<String, Number> barChart = createBarChart("Item Name", "Item(s) Sold", 1, "Item(s) Sold by Item", tcgApplication.getSQLConnector().getItemSales(categoryStr), 0, 1);
        showPane.getChildren().clear();
        showPane.getChildren().add(barChart);


    }


    public void setItemSalesAmount(String categoryStr) {
        topBar.getChildren().remove(comboBox1);
        topBar.getChildren().remove(comboBox3);
        comboBox1 = null;
        comboBox3 = null;
        if (comboBox2 == null) {
            comboBox2 = new ComboBox<>();
            comboBox2.setMinWidth(100);
            comboBox2.getItems().add("All");
            for (String category : tcgApplication.getSQLConnector().getCategory()) {
                comboBox2.getItems().add(category);
            }
            comboBox2.setValue("All");
            comboBox2.setOnAction(_ -> setItemSalesAmount(comboBox2.getValue()));
            topBar.getChildren().add(comboBox2);
        }

        BarChart<String, Number> barChart = createBarChart("Item Name", "Sales Amount (RM)", 1, "Sales Amount by Item", tcgApplication.getSQLConnector().getItemSales(categoryStr), 0, 2);
        showPane.getChildren().clear();
        showPane.getChildren().add(barChart);
    }


    public void setCategorySalesCount() {
        topBar.getChildren().remove(comboBox1);
        topBar.getChildren().remove(comboBox2);
        topBar.getChildren().remove(comboBox3);
        comboBox1 = null;
        comboBox2 = null;
        comboBox3 = null;

        BarChart<String, Number> barChart = createBarChart("Category Name", "Item(s) Sold", 1, "Item(s) Sold by Category", tcgApplication.getSQLConnector().getCategorySales(), 0, 1);
        showPane.getChildren().clear();
        showPane.getChildren().add(barChart);
    }

    public void setCategorySalesAmount() {
        topBar.getChildren().remove(comboBox1);
        topBar.getChildren().remove(comboBox2);
        topBar.getChildren().remove(comboBox3);
        comboBox1 = null;
        comboBox2 = null;
        comboBox3 = null;

        BarChart<String, Number> barChart = createBarChart("Category Name", "Sales Amount (RM)", 10, "Sales Amount by Category", tcgApplication.getSQLConnector().getCategorySales(), 0, 2);
        showPane.getChildren().clear();
        showPane.getChildren().add(barChart);
    }

    public void setSalesDetails(boolean isItem) {
        topBar.getChildren().remove(comboBox1);
        topBar.getChildren().remove(comboBox2);
        comboBox1 = null;
        comboBox2 = null;

        if (comboBox3 == null) {
            comboBox3 = new ComboBox<>();
            comboBox3.setMinWidth(100);
            comboBox3.getItems().addAll("By Item", "By Category");
            comboBox3.setValue("By Item");
            comboBox3.setOnAction(_ -> setSalesDetails(comboBox3.getValue().equals("By Item")));
            topBar.getChildren().add(comboBox3);
        }

        TableView<SalesDetails> tableView = new TableView<>();
        TableColumn<SalesDetails, String> nameCol = new TableColumn<>(isItem ? "Item Name" : "Category");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        TableColumn<SalesDetails, Integer> countCol = new TableColumn<>("Count");
        countCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAmount()).asObject());
        TableColumn<SalesDetails, String> priceCol = new TableColumn<>("Price (RM)");
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(GeneralFunction.twoDecimalPlaces(data.getValue().getPrice())));

        ObservableList<SalesDetails> items = FXCollections.observableArrayList();
        ArrayList<ArrayList<Object>> data = isItem ? tcgApplication.getSQLConnector().getItemSales("All") : tcgApplication.getSQLConnector().getCategorySales();
        for (ArrayList<Object> item : data) {
            SalesDetails salesDetails = new SalesDetails(item.getFirst().toString(), (Integer) item.get(1), (Double) item.get(2));
            items.add(salesDetails);
        }
        tableView.setItems(items);
        tableView.getColumns().addAll(nameCol, countCol, priceCol);
        tableView.getColumns().forEach(col -> col.setReorderable(false));

        StackPane.setMargin(tableView, new Insets(20));
        showPane.getChildren().clear();
        showPane.getChildren().add(tableView);
    }

    public void setUserAmount() {
        topBar.getChildren().remove(comboBox1);
        topBar.getChildren().remove(comboBox2);
        topBar.getChildren().remove(comboBox3);
        comboBox1 = null;
        comboBox2 = null;
        comboBox3 = null;
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

    public BarChart<String, Number> createBarChart(String xAxisStr, String yAxisStr, int yTick, String title, ArrayList<ArrayList<Object>> datas, int firstIdx, int secondIdx) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(xAxisStr);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisStr);
        yAxis.setTickUnit(yTick);
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return (object.doubleValue() % yTick == 0) ? String.valueOf(object.intValue()) : "";
            }
        });
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("");

        for (ArrayList<Object> data : datas) {
            String name = (String) data.get(firstIdx);
            if (secondIdx == 1) {
                int amount = (Integer) data.get(secondIdx);
                series.getData().add(new XYChart.Data<>(name, amount));
            }
            else {
                double amount = (Double) data.get(secondIdx);
                series.getData().add(new XYChart.Data<>(name, amount));
            }

        }
        barChart.getData().addAll(series);
        barChart.setLegendVisible(false);
        return barChart;
    }
}
