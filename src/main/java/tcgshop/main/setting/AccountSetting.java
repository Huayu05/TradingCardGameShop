package tcgshop.main.setting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tcgshop.TCGApplication;

import java.util.ArrayList;

public class AccountSetting extends HBox {
    public AccountSetting(TCGApplication tcgApplication) {
        super();

        ArrayList<ArrayList<String>> allAccList = tcgApplication.getSQLConnector().getAllUser();

        ObservableList<String> adminList = FXCollections.observableArrayList();
        adminList.addAll(allAccList.get(1));
        Label adminLabel = new Label("Admin List");
        adminLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );
        HBox adminLabelBox = new HBox(adminLabel);
        adminLabelBox.setAlignment(Pos.CENTER);
        ListView<String> adminListView = new ListView<>(adminList);
        Button setUser = new Button("Set to User");
        setUser.setDisable(true);
        VBox adminVBox = new VBox(10, adminLabelBox, adminListView, setUser);
        adminVBox.setAlignment(Pos.CENTER_RIGHT);

        ObservableList<String> userList = FXCollections.observableArrayList();
        userList.addAll(allAccList.getFirst());
        Label userLabel = new Label("User List");
        userLabel.setStyle(
                "-fx-font-family: verdana;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 24;"
        );
        HBox userLabelBox = new HBox(userLabel);
        userLabelBox.setAlignment(Pos.CENTER);
        ListView<String> userListView = new ListView<>(userList);
        Button setAdmin = new Button("Set to Admin");
        setAdmin.setDisable(true);
        VBox userVBox = new VBox(10, userLabelBox, userListView, setAdmin);
        userVBox.setAlignment(Pos.CENTER_LEFT);

        setUser.setOnAction(e -> {
            tcgApplication.getSQLConnector().changeUserType(false, adminListView.getSelectionModel().getSelectedItem());
            tcgApplication.getShopScene().getSettingPane().getSettingBar().settingChosen(5);
        });

        setAdmin.setOnAction(e -> {
            tcgApplication.getSQLConnector().changeUserType(true, userListView.getSelectionModel().getSelectedItem());
            tcgApplication.getShopScene().getSettingPane().getSettingBar().settingChosen(5);
        });

        adminListView.setOnMouseClicked(event -> {
            userListView.getSelectionModel().clearSelection();
            if (adminListView.getSelectionModel().getSelectedItem() != null) {
                setUser.setDisable(false);
                setAdmin.setDisable(true);
            }
        });

        userListView.setOnMouseClicked(event -> {
            adminListView.getSelectionModel().clearSelection();
            if (userListView.getSelectionModel().getSelectedItem() != null) {
                setUser.setDisable(true);
                setAdmin.setDisable(false);
            }
        });

        Separator separator = new Separator(Orientation.VERTICAL);
        HBox.setHgrow(separator, Priority.ALWAYS);

        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(userVBox, separator, adminVBox);
        StackPane.setMargin(this, new Insets(10));
    }
}
