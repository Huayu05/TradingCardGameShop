package com.tcgshop.tradingcardgameshop;

import java.util.Random;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class HelloPane extends GridPane {
    Label outputText = new Label("......");
    Button gayButton = new Button("Are You Gay?");

    public HelloPane() {
        this.add(outputText, 0, 0);
        this.add(gayButton, 0, 1);
        gayButton.setOnAction(e -> buttonClick());
    }

    public void buttonClick() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(2); // Generates 0 or 1

        if (randomNumber == 0) {
            outputText.setText("Yes");
        }
        else {
            outputText.setText("No");
        }
    }


}