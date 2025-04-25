package tcgshop.main.setting;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tcgshop.TCGApplication;
import tcgshop.main.shop.ItemBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;

public class ItemDetail {
    // Class attribute
    private TCGApplication tcgApplication;
    private String picturePath = System.getProperty("user.dir") + "\\item_pictures\\";
    private ArrayList<Object> item;
    private ComboBox<String> comboBox;
    private String name;
    private double price;
    private int quantity;
    private String category;

    public ItemDetail(TCGApplication tcgApplication, ArrayList<Object> item) {
        this.tcgApplication = tcgApplication;
        this.item = item;
        this.name = (String) item.get(1);
        picturePath = picturePath + name;
        this.price = (double) item.get(2);
        this.quantity = (int) item.get(3);
        this.category = (String) item.get(4);
    }

    public void editDetail() {
        ItemBox itemBox = new ItemBox(tcgApplication.getShopScene().getShopPane(), item);
        Label editLabel = new Label("Edit Item's Details");
        editLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );
        Label nameLabel = new Label("Name :");
        nameLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        TextField itemName = new TextField(name);
        itemName.setPromptText("Maximum 20 Characters");
        Label priceLabel = new Label("Price :");
        priceLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        TextField itemPrice = new TextField(String.valueOf(price));
        itemPrice.setPromptText("Maximum RM10000.00");
        Label quantityLabel = new Label("Quantity :");
        quantityLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 12;"
        );
        TextField itemQuantity = new TextField(String.valueOf(quantity));
        itemQuantity.setPromptText("Maximum 1000 pcs");
        Label categoryLabel = new Label("Category :");
        comboBox = new ComboBox<>();
        for (String category : tcgApplication.getSQLConnector().getCategory()) {
            comboBox.getItems().addAll(category);
        }
        comboBox.setEditable(true);
        comboBox.setValue(category);
        Button editButton = new Button("Edit");
        Button cancelButton = new Button("Cancel");

        HBox hbox = new HBox(editButton, cancelButton);
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

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(vBox);
        StackPane.setMargin(vBox, new Insets(20));

        Scene editScene = new Scene(stackPane, 300, 200);
        Stage editStage = new Stage();
        editStage.setScene(editScene);
        editStage.setResizable(false);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.setTitle("Edit Item");

        cancelButton.setOnAction(event -> editStage.close());
        editButton.setOnAction(event -> {
            boolean status = tcgApplication.getSQLConnector().editItem(itemName.getText(), itemPrice.getText(), itemQuantity.getText(), comboBox.getValue(), item);
            popupWindow(status);
            if (status) {
                editStage.close();
                File dir = new File(System.getProperty("user.dir")+"\\item_pictures\\");
                for (File file : Objects.requireNonNull(dir.listFiles())) {
                    if (file.getName().contains(name)) {
                        String newName = file.getName().replace(name, itemName.getText()); // simple rename
                        File newFile = new File(dir, newName);
                        file.renameTo(newFile);
                    }
                }
                tcgApplication.getShopScene().getSettingPane().getSettingBar().settingChosen(3);
            }
        });

        editStage.showAndWait();
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
        Label label = valid ? new Label("Details Edit Successful!") : new Label("Invalid Details!");
        label.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 14;"
        );
        Button button = new Button(valid ? "Back to Setting" : "Back to Editing");
        VBox vBox = new VBox(10, label, button);
        vBox.setAlignment(Pos.CENTER);
        popUpBox.getChildren().addAll(imageView, vBox);
        Scene popUpScene = new Scene(popUpBox);
        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setScene(popUpScene);
        popUp.setResizable(false);
        popUp.setTitle(valid ? "Edit Successful" : "Edit Failed");
        button.setOnAction(_ -> popUp.close());
        popUp.showAndWait();
    }

    public void uploadPicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Photo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png")
        );

        File file = fileChooser.showOpenDialog(tcgApplication.getShopScene().getWindow());
        if (file != null) {
            String fileName = file.getName();
            String extension = "";
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                extension = fileName.substring(dotIndex); // includes the dot, e.g., ".jpg"
            }

            try {
                Files.copy(file.toPath(), new File(picturePath + extension).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void showPreview() {
        VBox vbox = new VBox();
        ItemBox itemBox = new ItemBox(tcgApplication.getShopScene().getShopPane(), item);
        VBox.setMargin(itemBox, new Insets(10));
        Button button = new Button("Done");
        VBox.setMargin(button, new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(itemBox, button);
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Preview");
        button.setOnAction(_ -> stage.close());
        stage.showAndWait();
    }

    public int getID() {
        return (Integer) item.getFirst();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
