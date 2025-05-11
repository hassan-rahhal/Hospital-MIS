package com.example.finalproject;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.Scanner;

public class MenuModule {

    String[] logoPaths = {
            "C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\Search-removebg-preview.png",
            "C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\edit-removebg-preview.png",
            "C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\Delete-removebg-preview.png",
            "C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\Display-removebg-preview.png",
            "C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\Fill-removebg-preview.png",
            "C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\Appointment-removebg-preview.png",
            "C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\fillLab-removebg-preview.png",
            "C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\ssave.png",
            "C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\exit-removebg-preview.png"
    };

    String[] labelNames = {
            "Search",
            "Edit Patient",
            "Delete",
            "Display",
            "New Patient File",
            "Appointment",
            "Register",
            "Save",
            "Exit"
    };

    public void showMenu(Stage primaryStage) {
        Stage menuStage = new Stage();
        // Swap "Display" (index 3) and "Fill lab" (index 6)
        String tempPath = logoPaths[3];
        logoPaths[3] = logoPaths[6];
        logoPaths[6] = tempPath;

        String tempLabel = labelNames[3];
        labelNames[3] = labelNames[6];
        labelNames[6] = tempLabel;

        menuStage.setTitle("MENU");

        Image backgroundImage = new Image("C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\download2.png");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        Background backgroundObj = new Background(background);

        // Full-width navbar
        HBox navbar = new HBox();
        navbar.setPadding(new Insets(10));
        navbar.setStyle("-fx-background-color: #08605F;");
        navbar.setAlignment(Pos.CENTER); // Center the child container

        // Half-width button container
        HBox buttonContainer = new HBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setMaxWidth(Region.USE_PREF_SIZE); // Ensures tight layout
        buttonContainer.setPrefWidth(800); // Set to half screen size or desired width

        for (int i = 0; i < logoPaths.length; i++) {
            StackPane logo = createLogoContainer(logoPaths[i], labelNames[i]);
            int finalI = i;
            setLogoAction(logo, finalI, menuStage);
            buttonContainer.getChildren().add(logo);
        }

        navbar.getChildren().add(buttonContainer); // Add only the button container to navbar

        Label header = new Label("Menu");
        header.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 50));
        header.setTextFill(Color.web("#08605F"));
        StackPane headerContainer = new StackPane(header);
        headerContainer.setPadding(new Insets(30));

        // Outer container with 20% margins (left and right)
        HBox outerContainer = new HBox();
        outerContainer.setAlignment(Pos.CENTER);
        outerContainer.setPadding(new Insets(30));

// Inner green box styled professionally
        StackPane centerBox = new StackPane();
        centerBox.setStyle("-fx-background-color: #08605F; -fx-background-radius: 20;");
        centerBox.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.6);
        centerBox.setPrefHeight(300);
        centerBox.setEffect(new javafx.scene.effect.DropShadow(15, Color.GRAY));
        centerBox.setPadding(new Insets(30));  // Padding inside the box

// Text content
        VBox centerTextContainer = new VBox(10);
        centerTextContainer.setAlignment(Pos.CENTER);

        Label mainTitle = new Label("GREEN CARE MEDICAL CENTER");
        mainTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
        mainTitle.setTextFill(Color.WHITE);

        Label subTitle = new Label("Your Health Our Priority");
        subTitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        subTitle.setTextFill(Color.WHITE);

        centerTextContainer.getChildren().addAll(mainTitle, subTitle);
        centerBox.getChildren().add(centerTextContainer);
        outerContainer.getChildren().add(centerBox);

// Add to the main layout
        VBox layout = new VBox(20, headerContainer, navbar, outerContainer);
        layout.setBackground(backgroundObj);




        Scene scene = new Scene(layout, 400, 400);
        menuStage.setScene(scene);

        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        menuStage.setWidth(screenWidth * 0.9);
        menuStage.setHeight(screenHeight * 0.9);
        menuStage.show();
    }


    private StackPane createLogoContainer(String imageUrl, String labelText) {
        Rectangle rectangle = new Rectangle(80, 80);
        rectangle.setFill(Color.WHITE);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);

        Image image = new Image(imageUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: black; -fx-font-size: 11px;");

        VBox content = new VBox(3, imageView, label);
        content.setAlignment(Pos.CENTER);

        StackPane container = new StackPane(rectangle, content);
        container.setCursor(javafx.scene.Cursor.HAND);

        // Animate only the content (icon + label)
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(300), content);
        scaleUp.setToX(1.12);
        scaleUp.setToY(1.12);
        scaleUp.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(300), content);
        scaleDown.setToX(1);
        scaleDown.setToY(1);
        scaleDown.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);

        container.setOnMouseEntered(e -> {
            content.setEffect(new javafx.scene.effect.DropShadow(10, Color.web("#08605F")));
            scaleUp.playFromStart();
        });

        container.setOnMouseExited(e -> {
            content.setEffect(null);
            scaleDown.playFromStart();
        });

        return container;
    }

    public void setLogoAction(StackPane logoContainer, int index, Stage menuStage) {
        logoContainer.setOnMouseClicked(e -> {
            String action = "";
            HospitalManagementLogin h = new HospitalManagementLogin();
            switch (index) {
                case 0:
                    action = "Clicked Search Logo";
                    System.out.println(action);
                    concatenateRows("user_actions.csv", h.getValidUsername(), action);
                    SearchModule.showDashboard();
                    break;
                case 1:
                    action = "Clicked Edit Logo";
                    System.out.println(action);
                    concatenateRows("user_actions.csv", h.getValidUsername(), action);
                    new PatientEditModule().showPatientModule();
                    break;
                case 2:
                    action = "Clicked Delete Logo";
                    System.out.println(action);
                    concatenateRows("user_actions.csv", h.getValidUsername(), action);
                    break;
                case 3:
                    action = "Clicked Fill Lab Logo";  // Swapped
                    System.out.println(action);
                    concatenateRows("user_actions.csv", h.getValidUsername(), action);
                    Dashboard.showDashboard();
                    break;
                case 4:
                    action = "Clicked Register Patient Logo";
                    System.out.println(action);
                    concatenateRows("user_actions.csv", h.getValidUsername(), action);
                    new PatientFileModule().showPatientFileModule();
                    break;
                case 5:
                    action = "Clicked Appointment Logo";
                    System.out.println(action);
                    concatenateRows("user_actions.csv", h.getValidUsername(), action);
                    new AppointmentModule().showAppointmentModule();
                    break;
                case 6:
                    action = "Clicked Display Logo";  // Swapped
                    System.out.println(action);
                    concatenateRows("user_actions.csv", h.getValidUsername(), action);
                    break;
                case 7:
                    action = "Clicked Save Logo";
                    System.out.println(action);
                    concatenateRows("user_actions.csv", h.getValidUsername(), action);
                    break;
                case 8:
                    action = "Clicked Exit Logo";
                    System.out.println(action);
                    concatenateRows("user_actions.csv", h.getValidUsername(), action);
                    menuStage.close();
                    break;
            }
        });
    }


    public void concatenateRows(String filename, String username, String word) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new FileNotFoundException("File '" + filename + "' not found.");
            }

            File tempFile = new File("temp.csv");
            Scanner scanner = new Scanner(file);
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

            boolean found = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    found = true;
                    writer.println(line + "," + word);
                } else {
                    writer.println(line);
                }
            }

            scanner.close();
            writer.close();

            if (!found) {
                throw new IllegalArgumentException("Username not found in the CSV file.");
            }

            if (!file.delete()) {
                throw new IOException("Failed to delete the original file.");
            }

            if (!tempFile.renameTo(file)) {
                throw new IOException("Failed to rename temporary file to original file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
