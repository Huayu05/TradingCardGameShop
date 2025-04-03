package tcgshop.main.shop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CategoryBar extends VBox {
    public CategoryBar() {
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
        ObservableList<String> categoryList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8");
        ListView<String> listView = new ListView<>(categoryList);
        listView.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-background-color: #393E46;"
        );

        // Cell color setting
        listView.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: #393E46;");
                }
                else {
                    setText(item);
                    setStyle("-fx-background-color: #393E46; -fx-text-fill: white;");

                    // Cell method
                    setOnMouseClicked((MouseEvent _) -> {
                        if (!isEmpty()) {
                            setStyle(
                                    "-fx-background-color: #393E46;" +
                                    "-fx-text-fill: #00ADB5;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-padding: 0 0 0 20px;");
                            String selectedItem = getItem();
                            System.out.println("Item clicked: " + selectedItem);
                        }
                    });
                }
            }
        });

        // Category bar config
        GridPane.setMargin(this, new Insets(30));
        GridPane.setVgrow(this, Priority.ALWAYS);
        GridPane.setHgrow(this, Priority.ALWAYS);
        this.setPadding(new Insets(20));
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setStyle(
                "-fx-background-color: #393E46;" +
                "-fx-background-radius: 20px;"
        );
        this.getChildren().add(listView);
    }

}
