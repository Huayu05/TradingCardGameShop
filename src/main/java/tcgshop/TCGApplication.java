package tcgshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TCGApplication extends Application {
    // User information
    private String username;
    private String password;
    private boolean isAdmin;
    private LoginPage loginPage;

    @Override
    public void start(Stage primaryStage) {
        // SQL initialize
        SQLConnector sql = new SQLConnector();

        // Shop Page Initialize
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            primaryStage.setScene(loginPage);
        });
        Scene testScene = new Scene(backButton, 300, 300);

        // Login Page Initialize
        loginPage = new LoginPage();
        loginPage.getSignInPane().getSignUpButton().setOnAction(event -> {
            String[] data = loginPage.getSignInPane().getInput();
            if (data[0].isEmpty() || data[1].isEmpty() || data[2] == null) {
                loginPage.getSignInPane().getErrorRespond().setText("Please fill all information!");
            }
            else {
                if (sql.addUser(data[0], data[1], data[2])) {
                    loginPage.getSignInPane().reset();
                    this.username = data[0];
                    this.password = data[1];
                    this.isAdmin = data[2].equals("admin");
                    primaryStage.setScene(testScene);
                } else {
                    loginPage.getSignInPane().getErrorRespond().setText("Username exists");
                }
            }
        });
        loginPage.getLogInPane().getLogInButton().setOnAction(event -> {
            String[] data = loginPage.getLogInPane().getInput();
            if (data[0].isEmpty() || data[1].isEmpty()) {
                loginPage.getLogInPane().getErrorRespond().setText("Please fill all information!");
            }
            else {
                String userType = sql.login(data[0], data[1]);
                if (!userType.equals("false")) {
                    loginPage.getLogInPane().reset();
                    this.username = data[0];
                    this.password = data[1];
                    this.isAdmin = userType.equals("admin");
                    primaryStage.setScene(testScene);
                } else {
                    loginPage.getLogInPane().getErrorRespond().setText("Wrong username or password!");
                }
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
