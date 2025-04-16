package tcgshop.main.setting;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import tcgshop.TCGApplication;

import java.util.ArrayList;

public class ItemSetting extends StackPane {
    // Dynamic nodes
    private TCGApplication tcgApplication;

    public ItemSetting(TCGApplication tcgApplication) {
        super();

        this.tcgApplication = tcgApplication;

        TableView<ItemDetail> itemTable = new TableView<>();

        TableColumn<ItemDetail, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<ItemDetail, Double> priceCol = new TableColumn<>("Price (RM)");
        priceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        TableColumn<ItemDetail, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        TableColumn<ItemDetail, Void> actionCol = new TableColumn<>("");
        Callback<TableColumn<ItemDetail, Void>, TableCell<ItemDetail, Void>> cellFactory = param -> new TableCell<>() {
            private final Button previewBtn = new Button("Preview");
            private final Button editBtn = new Button("Edit");
            private final Button uploadBtn = new Button("Add Picture");
            private final Button removeBtn = new Button("Remove");

            private final HBox hbox = new HBox(10, previewBtn, editBtn, uploadBtn, removeBtn);

            {
                editBtn.setOnAction(event -> {
                    ItemDetail itemDetail = getTableView().getItems().get(getIndex());
                    itemDetail.editDetail();
                });

                uploadBtn.setOnAction(e -> {
                    ItemDetail itemDetail = getTableView().getItems().get(getIndex());
                    itemDetail.uploadPicture();
                });

                removeBtn.setOnAction(event -> {
                    ItemDetail item = getTableView().getItems().get(getIndex());
                    removeConfirm(getTableView().getItems(), item);
                });

                previewBtn.setOnAction(event -> {
                    ItemDetail item = getTableView().getItems().get(getIndex());
                    item.showPreview();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }
        };
        actionCol.setCellFactory(cellFactory);
        actionCol.setPrefWidth(Double.MAX_VALUE);

        itemTable.getColumns().addAll(nameCol, priceCol, quantityCol, actionCol);
        itemTable.getColumns().forEach(col -> col.setReorderable(false));

        ObservableList<ItemDetail> data = FXCollections.observableArrayList();
        ArrayList<ArrayList<Object>> items = tcgApplication.getSQLConnector().getItems("All");
        for (ArrayList<Object> row : items) {
            ItemDetail newItem = new ItemDetail(tcgApplication, row);
            data.add(newItem);
        }

        itemTable.setItems(data);
        this.getChildren().add(itemTable);
        StackPane.setMargin(this, new Insets(20));
        itemTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    public void removeConfirm(ObservableList<ItemDetail> items, ItemDetail item) {
        HBox popUpBox = new HBox(20);
        popUpBox.setStyle("-fx-background-color: #EEEEEE;");
        popUpBox.setPrefSize(300, 100);
        popUpBox.setAlignment(Pos.CENTER);
        Image image = new Image("file:src/main/resources/tcgshop/images/green_tick.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        Label label = new Label("Comfirm to Remove?");
        label.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 14;"
        );
        Button confirm = new Button("Remove");
        Button cancel = new Button("Cancel");
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(confirm, cancel);
        VBox vBox = new VBox(10, label, hbox);
        vBox.setAlignment(Pos.CENTER);
        popUpBox.getChildren().addAll(imageView, vBox);
        Scene popUpScene = new Scene(popUpBox);
        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setScene(popUpScene);
        popUp.setResizable(false);
        popUp.setTitle("Remove Confirmation");
        confirm.setOnAction(_ -> {
            popUp.close();
            tcgApplication.getSQLConnector().deleteItem(item.getID());
            items.remove(item);
        });
        cancel.setOnAction(_ -> popUp.close());
        popUp.showAndWait();
    }


}
