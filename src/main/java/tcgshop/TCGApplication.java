package tcgshop;

import javafx.application.Application;
import javafx.stage.Stage;

public class TCGApplication extends Application {
    // User information
    private static String username;
    private static String password;
    private static boolean isAdmin;

    @Override
    public void start(Stage primaryStage) {
        // SQL initialize
        SQLConnector sql = new SQLConnector();
        System.out.println(sql.addUser("root", "root", "admin"));

        // Login Page Initialize
        LoginPage loginPage = new LoginPage();
        loginPage.getSignInPane().getSignInButton().setOnAction(event -> {
            String[] data = loginPage.getSignInPane().getInput();
            if(sql.addUser(data[0], data[1], data[2])) {
                loginPage.getSignInPane().getErrorRespond().setText("");
            }
            else {
                loginPage.getSignInPane().getErrorRespond().setText("Username exists");
            }
        });
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
