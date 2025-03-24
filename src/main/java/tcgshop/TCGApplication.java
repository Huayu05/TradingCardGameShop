package tcgshop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
        Label usernameLabel = new Label("Username: " + username);
        Label passwordLabel = new Label("Password: " + password);
        Label adminLabel = new Label("Admin: " + (isAdmin ? "Admin" : "User"));
        Button backButton = new Button("Back");
        backButton.setOnAction(_ -> primaryStage.setScene(loginPage));
        VBox vBox = new VBox(usernameLabel, passwordLabel, adminLabel, backButton);
        Scene testScene = new Scene(vBox, 300, 300);

        // Login Page Initialize
        loginPage = new LoginPage();

        // Sign out button action setup
        loginPage.getSignUpPane().getSignUpButton().setOnAction(_ -> {
            String[] data = loginPage.getSignUpPane().getInput();
            if (data[0].isEmpty() || data[1].isEmpty() || data[2] == null) {
                loginPage.getSignUpPane().getErrorRespond().setText("Please fill all information!");
            }
            else {
                if (sql.addUser(data[0], data[1], data[2])) {
                    loginPage.getSignUpPane().reset();
                    this.username = data[0];
                    this.password = data[1];
                    this.isAdmin = data[2].equals("admin");
                    primaryStage.setScene(testScene);
                }
                else {
                    loginPage.getSignUpPane().getErrorRespond().setText("Username exists");
                }
            }
        });

        // Log in button action setup
        loginPage.getLogInPane().getLogInButton().setOnAction(_ -> {
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
                }
                else {
                    loginPage.getLogInPane().getErrorRespond().setText("Wrong username or password!");
                }
            }
        });

        // Stage config
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
