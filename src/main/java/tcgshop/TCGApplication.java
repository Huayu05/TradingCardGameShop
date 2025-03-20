package tcgshop;

import javafx.application.Application;
import javafx.stage.Stage;

public class TCGApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginPage loginPage = new LoginPage();
        primaryStage.setScene(loginPage);
        primaryStage.setTitle("TCG Shop");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(800);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
