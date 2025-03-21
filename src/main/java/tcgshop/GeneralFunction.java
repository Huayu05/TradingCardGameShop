package tcgshop;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class GeneralFunction {
    public static void displayPane(GridPane gridPane) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(_ -> {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), gridPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        pause.play();
    }

    public static void disappearPane(GridPane gridPane) {
        gridPane.toBack();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), gridPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.play();
    }
}
