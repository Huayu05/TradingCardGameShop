package tcgshop.main.setting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
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
        ListView<String> adminListView = new ListView<>(adminList);
        Button setUser = new Button("Set to User");
        VBox adminVBox = new VBox(10, adminListView, setUser);
        adminVBox.setAlignment(Pos.CENTER_LEFT);

        ObservableList<String> userList = FXCollections.observableArrayList();
        userList.addAll(allAccList.getFirst());
        ListView<String> userListView = new ListView<>(userList);
        Button setAdmin = new Button("Set to Admin");
        VBox userVBox = new VBox(10, userListView, setAdmin);
        userVBox.setAlignment(Pos.CENTER_RIGHT);

        setUser.setOnAction(e -> {
            tcgApplication.getSQLConnector().changeUserType(false, adminListView.getSelectionModel().getSelectedItem());
            tcgApplication.getShopScene().getSettingPane().getSettingBar().settingChosen(5);
        });

        setAdmin.setOnAction(e -> {
            tcgApplication.getSQLConnector().changeUserType(true, userListView.getSelectionModel().getSelectedItem());
            tcgApplication.getShopScene().getSettingPane().getSettingBar().settingChosen(5);
        });

        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(userVBox, adminVBox);
        StackPane.setMargin(this, new Insets(10));
    }
}
