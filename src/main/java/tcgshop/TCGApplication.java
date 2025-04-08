package tcgshop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tcgshop.authentication.LoginScene;
import tcgshop.main.MainScene;
import tcgshop.utils.SQLConnector;

public class TCGApplication extends Application {
    // User information
    private String username = "huayu";
    private String password = "123456";
    private boolean isAdmin = true;


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
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(1000);
        primaryStage.show();
    }


    // Main Method
    public static void main(String[] args) {
        launch(args);
    }


    // Logout method
    public void logout() {
        setPrimaryStage(loginScene);
        this.username = null;
        this.password = null;
        this.isAdmin = false;
        resetMainScene();
    }


    // Main scene reset method
    public void resetMainScene() {
        this.mainScene = new MainScene(this);
        mainScene.getTopMenu().setUsernameLabel(username);
    }


    // Getter method ( IsAdmin )
    public boolean isAdmin() {
        return isAdmin;
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
    public void setPrimaryStage(Scene newScene) {
        Scene oldScene = primaryStage.getScene();
        double width = oldScene.getWidth();
        double height = oldScene.getHeight();
        primaryStage.setScene(newScene);
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }


    // Setter method ( account details )
    public void setUserInformation(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
