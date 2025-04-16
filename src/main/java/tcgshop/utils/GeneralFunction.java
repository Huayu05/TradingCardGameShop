package tcgshop.utils;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;

public class GeneralFunction {
    // Fade in transition for a GridPane
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


    // Fade out transition for a GridPane and sent to back
    public static void disappearPane(GridPane gridPane) {
        gridPane.toBack();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), gridPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.play();
    }


    // Method return tax in %
    public static int loadTax() {
        int tax = 0;
        try {
            var resource = (GeneralFunction.class.getResource("/tcgshop/data.txt"));
            if (resource != null) {
                List<String> lines = Files.readAllLines(Paths.get(resource.toURI()));
                for (String line : lines) {
                    if (line.startsWith("Tax")) {
                        String[] parts = line.split("=");
                        if (parts.length == 2) {
                            tax = Integer.parseInt(parts[1].trim());
                            break;
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("ERROR: Cannot get tax");
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return tax;
    }


    // Method return discount in %
    public static <Path> int loadDiscount() {
        int discount = 0;
        try {
            var resource = (GeneralFunction.class.getResource("/tcgshop/data.txt"));
            if (resource != null) {
                List<String> lines = Files.readAllLines(Paths.get(resource.toURI()));
                for (String line : lines) {
                    if (line.startsWith("Discount")) {
                        String[] parts = line.split("=");
                        if (parts.length == 2) {
                            discount = Integer.parseInt(parts[1].trim());
                            break;
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("ERROR: Cannot get discount");
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return discount;
    }


    // Two decimal converter
    public static String twoDecimalPlaces(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }


    // Return capital
    public static String toCapital(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
