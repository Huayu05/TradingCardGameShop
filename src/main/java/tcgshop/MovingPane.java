package tcgshop;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class MovingPane extends VBox {
    // Class Attribute
    public Label title;
    private Label subtitle;
    public Button changeSide;

    public MovingPane() {
        title = new Label("Welcome Back");

        subtitle = new Label("Have an account already?");

        changeSide = new Button("Login");

        this.getChildren().addAll(title, subtitle, changeSide);
        this.setPadding(new Insets(100, 0, 100, 0));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #B3D8A8;-fx-background-radius: 20px;");
    }

    public void changeSide(double stackPaneWidth, double stackPanePadding, boolean nowLeft) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), this);
        double distance = stackPaneWidth - this.getWidth() - stackPanePadding * 2;
        transition.setByX(nowLeft ? -distance : distance);
        transition.setOnFinished(event -> {
            StackPane.setAlignment(this, nowLeft ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
            this.setTranslateX(0);
        });
        transition.play();

        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
        pause.setOnFinished(e -> {
            FadeTransition fadeOut1 = new FadeTransition(Duration.millis(400), title);
            fadeOut1.setFromValue(1);
            fadeOut1.setToValue(0);
            fadeOut1.setOnFinished(event -> {
                title.setText(nowLeft ? "Welcome Back" : "Nice to meet you");
                FadeTransition fadeIn = new FadeTransition(Duration.millis(400), title);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            });

            FadeTransition fadeOut2 = new FadeTransition(Duration.millis(400), subtitle);
            fadeOut2.setFromValue(1);
            fadeOut2.setToValue(0);
            fadeOut2.setOnFinished(event -> {
                subtitle.setText(nowLeft ? "Have an account already?" : "Doesn't have an account?");
                FadeTransition fadeIn = new FadeTransition(Duration.millis(400), subtitle);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            });

            FadeTransition fadeOut3 = new FadeTransition(Duration.millis(400), changeSide);
            fadeOut3.setFromValue(1);
            fadeOut3.setToValue(0);
            fadeOut3.setOnFinished(event -> {
                changeSide.setText(nowLeft ? "Log In" : "Sign Up");
                FadeTransition fadeIn = new FadeTransition(Duration.millis(400), changeSide);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            });

            ParallelTransition parallelTransition = new ParallelTransition(fadeOut1, fadeOut2, fadeOut3);
            parallelTransition.play();
        });
        pause.play();
    }
}