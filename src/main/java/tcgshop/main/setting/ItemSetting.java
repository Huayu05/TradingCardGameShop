package tcgshop.main.setting;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import tcgshop.TCGApplication;
import tcgshop.utils.GeneralFunction;

import java.util.ArrayList;

public class ItemSetting extends VBox {
    // Dynamic nodes
    private TCGApplication tcgApplication;

    public ItemSetting(TCGApplication tcgApplication) {
        super();

        this.tcgApplication = tcgApplication;

        Label title = new Label("Item Setting");
        title.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );
        HBox hbox = new HBox(title);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.prefWidthProperty().bind(this.widthProperty());
        hbox.setSpacing(10);
        this.getChildren().add(hbox);

        TableView<ItemDetail> itemTable = new TableView<>();

        TableColumn<ItemDetail, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<ItemDetail, String> priceCol = new TableColumn<>("Price (RM)");
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(GeneralFunction.twoDecimalPlaces(data.getValue().getPrice())));

        TableColumn<ItemDetail, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        TableColumn<ItemDetail, String> categoryNameCol = new TableColumn<>("Category Name");
        categoryNameCol.setCellValueFactory((data -> new SimpleStringProperty(data.getValue().getCategory())));

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


        itemTable.getColumns().addAll(nameCol, categoryNameCol, priceCol, quantityCol, actionCol);
        itemTable.getColumns().forEach(col -> col.setReorderable(false));

        ObservableList<ItemDetail> data = FXCollections.observableArrayList();
        ArrayList<ArrayList<Object>> items = tcgApplication.getSQLConnector().getItems("All");
        for (ArrayList<Object> row : items) {
            ItemDetail newItem = new ItemDetail(tcgApplication, row);
            data.add(newItem);
        }
        itemTable.setItems(data);
        this.getChildren().add(itemTable);

        Button addNewItem = new Button("Add New Item");
        addNewItem.setOnAction(_ -> addNewItem());
        this.getChildren().add(addNewItem);

        Button editCategory = new Button("Edit Category");
        editCategory.setOnAction(e -> editCategory());
        this.getChildren().add(editCategory);

        this.setAlignment(Pos.CENTER_RIGHT);
        this.setSpacing(10);
        StackPane.setMargin(this, new Insets(20));
        itemTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    public void removeConfirm(ObservableList<ItemDetail> items, ItemDetail item) {
        HBox popUpBox = new HBox(20);
        popUpBox.setStyle("-fx-background-color: #EEEEEE;");
        popUpBox.setPrefSize(300, 100);
        popUpBox.setAlignment(Pos.CENTER);
        Image image = new Image("file:src/main/resources/tcgshop/images/blue_question.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        Label label = new Label("Confirm to Remove?");
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

    public void addNewItem() {
        Label editLabel = new Label("Enter New Item's Details Here");
        editLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );
        Label nameLabel = new Label("Name :");
        nameLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        TextField itemName = new TextField();
        itemName.setPromptText("Maximum 20 Characters");
        Label priceLabel = new Label("Price :");
        priceLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        TextField itemPrice = new TextField();
        itemPrice.setPromptText("Maximum RM10000.00");
        Label quantityLabel = new Label("Quantity :");
        quantityLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        TextField itemQuantity = new TextField();
        itemQuantity.setPromptText("Maximum 1000 pcs");
        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");
        Label categoryLabel = new Label("Category :");
        ComboBox<String> comboBox = new ComboBox<>();
        for (String category : tcgApplication.getSQLConnector().getCategory()) {
            comboBox.getItems().addAll(category);
        }
        comboBox.setEditable(true);

        HBox hbox = new HBox(addButton, cancelButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(nameLabel, 0, 0);
        grid.add(itemName, 1, 0);
        grid.add(priceLabel, 0, 1);
        grid.add(itemPrice, 1, 1);
        grid.add(quantityLabel, 0, 2);
        grid.add(itemQuantity, 1, 2);
        grid.add(categoryLabel, 0, 3);
        grid.add(comboBox, 1, 3);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(editLabel, grid, hbox);

        StackPane stackPane = new StackPane(vBox);
        StackPane.setMargin(vBox, new Insets(20));

        Scene addScene = new Scene(stackPane);
        Stage addStage = new Stage();
        addStage.setScene(addScene);
        addStage.setResizable(false);
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.setTitle("Add Item");

        cancelButton.setOnAction(event -> addStage.close());
        addButton.setOnAction(event -> {
            if (tcgApplication.getSQLConnector().addItem(itemName.getText(), itemPrice.getText(), itemQuantity.getText(), GeneralFunction.toCapital(comboBox.getEditor().getText()))) {
                tcgApplication.getShopScene().getSettingPane().getSettingBar().settingChosen(3);
                popupWindow(true);
                addStage.close();
            }
            else {
                popupWindow(false);
            }
        });

        addStage.showAndWait();
    }

    public void popupWindow(boolean valid) {
        HBox popUpBox = new HBox(20);
        popUpBox.setStyle("-fx-background-color: #EEEEEE;");
        popUpBox.setPrefSize(300, 100);
        popUpBox.setAlignment(Pos.CENTER);
        Image image = valid ? new Image("file:src/main/resources/tcgshop/images/green_tick.png") : new Image("file:src/main/resources/tcgshop/images/red_cross.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        Label label = valid ? new Label("Item Add Successful!") : new Label("Invalid Details!");
        label.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 14;"
        );
        Button button = new Button(valid ? "Back to Setting" : "Back to Adding Item");
        VBox vBox = new VBox(10, label, button);
        vBox.setAlignment(Pos.CENTER);
        popUpBox.getChildren().addAll(imageView, vBox);
        Scene popUpScene = new Scene(popUpBox);
        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setScene(popUpScene);
        popUp.setResizable(false);
        popUp.setTitle(valid ? "Item Add Successful" : "Item Add Failed");
        button.setOnAction(_ -> popUp.close());
        popUp.showAndWait();
    }

    public void editCategory() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label("Choose Category to Edit");
        label.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );
        ObservableList<String> category = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>(category);
        category.addAll(tcgApplication.getSQLConnector().getCategory());

        Label editLabel = new Label("Change Name:");
        editLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        TextField editField = new TextField();
        editField.setDisable(true);

        HBox hbox = new HBox(5, editLabel, editField);
        hbox.setAlignment(Pos.CENTER);

        Button editButton = new Button("Edit");
        editButton.setDisable(true);
        Button removeButton = new Button("Remove");
        removeButton.setDisable(true);
        Button cancelButton = new Button("Exit");
        HBox hbox2 = new HBox(10, editButton, removeButton, cancelButton);
        hbox2.setAlignment(Pos.CENTER);

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            editField.setText(newVal);
            editField.setDisable(false);
            editButton.setDisable(false);
            removeButton.setDisable(false);
        });

        vBox.setPadding(new Insets(0, 20, 0, 20));
        vBox.getChildren().addAll(label, listView, hbox, hbox2);

        Scene editCategoryScene = new Scene(vBox, 400, 600);
        Stage editCategoryStage = new Stage();
        editCategoryStage.setScene(editCategoryScene);
        editCategoryStage.setResizable(false);
        editCategoryStage.initModality(Modality.APPLICATION_MODAL);
        editCategoryStage.setTitle("Edit Category");

        editButton.setOnAction(e -> {
            tcgApplication.getSQLConnector().deleteCategory(category.get(listView.getSelectionModel().getSelectedIndex()), editField.getText());
            editCategoryStage.close();
        });
        removeButton.setOnAction(e -> {
            tcgApplication.getSQLConnector().deleteCategory(category.get(listView.getSelectionModel().getSelectedIndex()), "");
            editCategoryStage.close();
        });
        cancelButton.setOnAction(_ -> editCategoryStage.close());

        editCategoryStage.showAndWait();
    }
}
