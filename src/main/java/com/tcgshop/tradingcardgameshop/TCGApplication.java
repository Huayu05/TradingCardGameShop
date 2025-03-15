package com.tcgshop.tradingcardgameshop;

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
        changeWindowOne();
    }

    public static void main(String[] args) {
        launch();
    }

    public void changeWindowOne() {
        Label label = new Label("'A Login Page'");
        Button button = new Button("Login");
        button.setOnAction(e -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                changeWindowTwo();
            });
            pause.play();
        });

        Scene scene = new Scene(new VBox(label, button), 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void changeWindowTwo() {
        Label label = new Label("Welcome to the Trading Card Game Shop!");
        Button button = new Button("Logout");
        button.setOnAction(e -> {
            changeWindowOne();
        });

        VBox root = new VBox(label, button);
        root.setStyle("-fx-background-color: lightblue;");
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}