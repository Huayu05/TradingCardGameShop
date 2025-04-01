package tcgshop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tcgshop.authentication.LoginScene;
import tcgshop.main.MainScene;
import tcgshop.utils.SQLConnector;

public class TCGApplication extends Application {
    // Parents in the class
    private LoginScene loginScene;
    private MainScene mainScene;
    private SQLConnector sqlConnector;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        // Set reference to primary stage
        this.primaryStage = primaryStage;

        // SQL initialize
        sqlConnector = new SQLConnector();

        // Shop Page Initialize
        mainScene = new MainScene(this);

        // Login Page Initialize
        loginScene = new LoginScene(this);

        // Stage config
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Trading Card Game Shop");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(800);
        primaryStage.show();
    }

    // Main Method
    public static void main(String[] args) {
        launch(args);
    }

    // Getter method ( Login Scene )
    public LoginScene getLoginScene() {
        return loginScene;
    }

    // Getter method ( Shop Scene )
    public MainScene getShopScene() {
        return mainScene;
    }

    // Getter method ( SQL Connector )
    public SQLConnector getSQLConnector() {
        return sqlConnector;
    }

    // Setter method ( Primary Stage )
    public void setPrimaryStage(Scene scene) {
        primaryStage.setScene(scene);
    }

}
