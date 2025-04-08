package tcgshop.main.shop;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import tcgshop.TCGApplication;

public class ItemCategoryBar extends VBox {
    public ItemCategoryBar(TCGApplication tcgApplication, ShopPane shopPane) {
        // Call constructor from parent
        super();

        // Category label
        Label title = new Label("Category");
        title.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-font-size: 30px;" +
                "-fx-text-fill: #00ADB5;"
        );
        this.getChildren().add(title);

        // Scrollable list building for category ( Maximum 15 char )
        ObservableList<String> categoryList = FXCollections.observableArrayList("All");
        ListView<String> listView = new ListView<>(categoryList);
        listView.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-background-color: #393E46;"
        );

        // Add categories into list
        categoryList.addAll(tcgApplication.getSQLConnector().getCategory());

        // Cell color setting
        listView.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                // Set color for all cells
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: #393E46;");
                }
                else {
                    setText(item);
                    setStyle("-fx-background-color: #393E46; -fx-text-fill: white;");

                    // Cell method
                    setOnMouseClicked((MouseEvent _) -> {
                        if (!isSelected()) {
                            setStyle("-fx-background-color: #393E46; -fx-text-fill: white;");
                        }
                        else {
                            setStyle(
                                    "-fx-background-color: #393E46;" +
                                    "-fx-text-fill: #00ADB5;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-padding: 0 0 0 20px;"
                            );
                            String selectedItem = getItem();
                            shopPane.resetItemBar(selectedItem);
                        }
                        listView.getSelectionModel().select(getIndex());
                    });
                }
            }
        });

        // Initialize category to All
        listView.getSelectionModel().select(0);
        Platform.runLater(() -> {
            Node node = listView.lookup(".list-cell");
            if (node instanceof ListCell<?>) {
                @SuppressWarnings("unchecked")
                ListCell<String> firstCell = (ListCell<String>) node;
                firstCell.setStyle(
                        "-fx-background-color: #393E46;" +
                                "-fx-text-fill: #00ADB5;" +
                                "-fx-font-weight: bold;" +
                                "-fx-padding: 0 0 0 20px;"
                );
            }
        });

        // Category bar config
        GridPane.setMargin(this, new Insets(40, 30, 40, 30));
        GridPane.setVgrow(this, Priority.ALWAYS);
        GridPane.setHgrow(this, Priority.ALWAYS);
        this.setMaxWidth(350);
        this.setPadding(new Insets(20));
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle(
                "-fx-background-color: #393E46;" +
                "-fx-background-radius: 20px;"
        );
        this.getChildren().add(listView);
    }
}
