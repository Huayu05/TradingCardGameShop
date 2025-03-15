package com.tcgshop.tradingcardgameshop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TCGApplication extends Application {
    @Override
    public void start(Stage stage) {
        HelloPane grid = new HelloPane();
        Scene scene = new Scene(grid);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}