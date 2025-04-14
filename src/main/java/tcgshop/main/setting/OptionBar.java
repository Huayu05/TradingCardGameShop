package tcgshop.main.setting;

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
import tcgshop.TCGApplication;

public class OptionBar extends VBox {
    // Dynamic nodes
    private ListView<String> listView;

    public OptionBar(TCGApplication tcgApplication) {
        // Call constructor from parent
        super();

        // Options title label
        Label title = new Label("Setting");
        title.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 30px;" +
                        "-fx-text-fill: #00ADB5;"
        );
        this.getChildren().add(title);

        // Scrollable list building for all setting option
        ObservableList<String> optionList = FXCollections.observableArrayList("Purchase History", "Change Password", "Feedback");
        listView = new ListView<>(optionList);
        listView.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-background-color: #393E46;"
        );

        if (tcgApplication.isAdmin()) {
            optionList.addAll("Add Items", "Edit Items", "Sales Overview", "Admin Authorize", "Show Feedback");
        }


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
                        }
                        listView.getSelectionModel().select(getIndex());
                    });
                }
            }
        });

        // Option bar config
        GridPane.setMargin(this, new Insets(40, 30, 40, 30));
        GridPane.setVgrow(this, Priority.ALWAYS);
        GridPane.setHgrow(this, Priority.ALWAYS);
        this.setMaxWidth(350);
        this.setPadding(new Insets(20));
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle(
                "-fx-background-color: #393E46;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-effect: dropshadow(gaussian, #0000006F, 20, 0.5, 0, 0);"
        );
        this.getChildren().add(listView);
    }

}
