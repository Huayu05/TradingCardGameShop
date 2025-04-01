package tcgshop.authentication;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import tcgshop.utils.GeneralFunction;

public class MovingPane extends VBox {
    // Class variable
    private boolean nowLeft = true;

    // Dynamic nodes
    private Label title;
    private Label subtitle;
    private Button changeSide;

    public MovingPane(LoginScene scene) {
        // Big title change depends on which side
        title = new Label("Welcome Back");
        VBox.setMargin(title, new Insets(0, 0, 15, 0));
        title.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 28;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #000000D0;" +
                "-fx-effect: dropshadow(gaussian, #0000004F, 3, 0, 1, 1)"
        );

        // Subtitle change depends on which side
        subtitle = new Label("Have an account already?");
        VBox.setMargin(subtitle, new Insets(0, 0, 30, 0));
        subtitle.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-text-fill: #000000D0;" +
                "-fx-effect: dropshadow(gaussian, #0000004F, 3, 0, 1, 1)"
        );

        // A switch side button
        changeSide = new Button("Login");
        changeSide.setMinWidth(100);
        changeSide.setOnAction(_ -> changeSide(scene));
        changeSide.setStyle(
                "-fx-font-family: Verdana;" +
                "-fx-font-size: 16;" +
                "-fx-background-color: linear-gradient(to bottom, #3d8d7a, #3D8D7AB8);" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10px 20px;" +
                "-fx-background-radius: 25px;" +
                "-fx-border-radius: 25px;" +
                "-fx-effect: dropshadow(gaussian, #0000009F, 20, 0.5, 0, 0);"
        );

        // Moving VBox config
        this.getChildren().addAll(title, subtitle, changeSide);
        this.setPadding(new Insets(100, 0, 100, 0));
        this.setAlignment(Pos.CENTER);
        this.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #B3D8A8, #d0efc8);" +
                "-fx-background-radius: 20px;" +
                "-fx-effect: dropshadow(gaussian, #0000007F, 10, 0.3, 0, 0);"
        );
    }


    // Method switch side of the VBox
    public void changeSide(LoginScene scene) {
        // Reset position now and both page input
        nowLeft = !nowLeft;
        scene.getLogInPane().reset();
        scene.getSignUpPane().reset();

        // VBox moving animation
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), this);
        double distance = scene.getLoginMain().getWidth() - this.getWidth() - scene.getLoginMain().getPadding().getLeft() * 2;
        changeSide.setDisable(true);
        transition.setByX(nowLeft ? -distance : distance);
        transition.setOnFinished(_ -> {
            StackPane.setAlignment(this, nowLeft ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
            this.setTranslateX(0);
            changeSide.setDisable(false);
        });
        transition.play();

        // Show only related pane
        if (nowLeft) {
            GeneralFunction.disappearPane(scene.getLogInPane());
            GeneralFunction.displayPane(scene.getSignUpPane());
        }
        else {
            GeneralFunction.disappearPane(scene.getSignUpPane());
            GeneralFunction.displayPane(scene.getLogInPane());
        }

        // Parallel start all transition
        ParallelTransition parallelTransition = new ParallelTransition(
            fadeTextChange(title, nowLeft ? "Welcome Back" : "Welcome Aboard"),
            fadeTextChange(subtitle, nowLeft ? "Have an account already?" : "Doesn't have an account?"),
            fadeTextChange(changeSide, nowLeft ? "Log In" : "Sign Up")
        );
        parallelTransition.play();
    }


    // Text fade changing animation
    private FadeTransition fadeTextChange(Labeled node, String newText) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), node);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(_ -> {
            node.setText(newText);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), node);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        return fadeOut;
    }
}