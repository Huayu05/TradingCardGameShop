package com.tcgshop.tradingcardgameshop;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TCGApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene scene = changeWindowOne();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Scene changeWindowOne() {
        Label label = new Label("'A Login Page'");
        Button button = new Button("Login");
        Scene scene = new Scene(new VBox(label, button), 300, 200);

        button.setOnAction(e -> {
            switchScene(scene, changeWindowTwo());
        });


        return scene;
    }

    public Scene changeWindowTwo() {
        Label label = new Label("Welcome to the Trading Card Game Shop!");
        Button button = new Button("Logout");
        VBox root = new VBox(label, button);
        root.setStyle("-fx-background-color: lightblue;");
        Scene scene = new Scene(root, 300, 200);

        button.setOnAction(e -> {
            switchScene(scene, changeWindowOne());
        });

        return scene;
    }

    private void switchScene(Scene currentScene, Scene newScene) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            primaryStage.setScene(newScene); // Switch scenes
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newScene.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }
}