package tcgshop.utils;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;

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
        File file = new File(System.getProperty("user.dir")+"\\data\\data.txt");

        if (!file.exists()) {
            System.out.println("ERROR: data.txt not found");
            return tax;
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
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
        catch (IOException e) {
            System.out.println("ERROR: Cannot read tax file");
            e.printStackTrace();
        }

        return tax;
    }


    public static void saveTax(int tax) {
        File file = new File(System.getProperty("user.dir")+"\\data\\data.txt"); // same path as loadTax()
        List<String> lines = new ArrayList<>();

        try {
            if (file.exists()) {
                lines = Files.readAllLines(file.toPath());

                boolean found = false;
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).startsWith("Tax")) {
                        lines.set(i, "Tax = " + tax);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    lines.add("Tax = " + tax);
                }
            } else {
                lines.add("Tax = " + tax);
            }

            Files.write(file.toPath(), lines);

        }
        catch (IOException e) {
            System.out.println("ERROR: Cannot save tax");
        }
    }


    // Method return discount in %
    public static int loadDiscount() {
        int discount = 0;
        File file = new File(System.getProperty("user.dir")+"\\data\\data.txt");

        if (!file.exists()) {
            System.out.println("ERROR: data.txt not found");
            return discount;
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
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
        catch (IOException e) {
            System.out.println("ERROR: Cannot read discount file");
            e.printStackTrace();
        }

        return discount;
    }


    public static void saveDiscount(int discount) {
        File file = new File(System.getProperty("user.dir")+"\\data\\data.txt");
        List<String> lines = new ArrayList<>();

        try {
            if (file.exists()) {
                lines = Files.readAllLines(file.toPath());

                boolean found = false;
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).startsWith("Discount")) {
                        lines.set(i, "Discount = " + discount);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    lines.add("Discount = " + discount);
                }
            } else {
                lines.add("Discount = " + discount);
            }

            Files.write(file.toPath(), lines);

        }
        catch (IOException e) {
            System.out.println("ERROR: Cannot save discount");
        }
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
