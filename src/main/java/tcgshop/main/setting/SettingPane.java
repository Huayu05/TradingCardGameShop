package tcgshop.main.setting;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import tcgshop.TCGApplication;
import tcgshop.main.MainScene;

public class SettingPane extends GridPane {

    public SettingPane(TCGApplication tcgApplication, MainScene mainScene) {
        // Call constructor from parent
        super();

        // Option list initialize
        OptionBar optionBar = new OptionBar(tcgApplication);
        this.add(optionBar, 0, 0);

        // Setting contents
        SettingBar settingBar = new SettingBar(tcgApplication);
        this.add(settingBar, 1, 0);

        // Column constraint assign
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);

        // Grid pane config
        this.getColumnConstraints().addAll(col1, col2);
    }
}

