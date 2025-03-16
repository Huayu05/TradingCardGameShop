package com.tcgshop.tradingcardgameshop;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TCGApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root container
        Pane root = new Pane();

        // Login Pane (Left)
        StackPane loginPane = new StackPane();
        loginPane.setStyle("-fx-background-color: lightgray;");
        loginPane.setPrefSize(250, 300);
        loginPane.setLayoutX(50);
        loginPane.setLayoutY(50);
        Text loginText = new Text("Login");
        loginPane.getChildren().add(loginText);

        // Sign Up Pane (Right, will slide left)
        StackPane signupPane = new StackPane();
        signupPane.setStyle("-fx-background-color: lightblue;");
        signupPane.setPrefSize(250, 300);
        signupPane.setLayoutX(350);
        signupPane.setLayoutY(50);
        Text signupText = new Text("Sign Up");
        signupPane.getChildren().add(signupText);

        // Button to trigger animation
        Button signUpButton = new Button("Sign Up");
        signUpButton.setLayoutX(150);
        signUpButton.setLayoutY(400);

        // Animation when clicking "Sign Up"
        signUpButton.setOnAction(e -> {
            // Move signupPane left
            TranslateTransition movePane = new TranslateTransition(Duration.seconds(1), signupPane);
            movePane.setByX(-300);
            movePane.play();

            // Fade out login text
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), loginText);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();

            // Fade in signup text
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), signupText);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        // Add elements to root
        root.getChildren().addAll(loginPane, signupPane, signUpButton);

        // Set up scene and stage
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setTitle("Login to Sign Up Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
