package com.example.finalproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Screen;
import java.io.*;
import java.util.Scanner;
public class MenuModule {
    public  void showMenu(Stage primaryStage) {
        Stage menuStage = new Stage();
        menuStage.setTitle("MENU");

        StackPane S=new StackPane();
        Label header=new Label("Menu");
        header.setFont(Font.font("Arial",FontWeight.BOLD,FontPosture.REGULAR,50));
        header.setTextFill(Color.web("#5c8cba"));
        S.getChildren().add(header);

        Image backgroundImage = new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\download.jpg");

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        Background backgroundObj = new Background(background);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(90);
        grid.setVgap(35);

        // Define logos
        String[] logoPaths = {
                "C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Search-removebg-preview.png",
                "C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\edit-removebg-preview.png",
                "C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Delete-removebg-preview.png",
                "C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Display-removebg-preview.png",
                "C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Fill-removebg-preview.png",
                "C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Appointment-removebg-preview.png",
                "C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\fillLab-removebg-preview.png",
                "C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\ssave.png",
                "C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\exit-removebg-preview.png"
        };
        String[] labelNames = {
                "Search",
                "Edit",
                "Delete",
                "Display",
                "Register a new Patient",
                "Appointment",
                "Fill Lab File",
                "Save",
                "Exit"
        };



        // Populate the grid with logos and labels
        for (int i = 0; i < logoPaths.length; i++) {
            StackPane logoContainer = createLogoContainer(logoPaths[i], labelNames[i]);
            int row = i / 3;
            int col = i % 3;
            grid.add(logoContainer, col, row);
            setLogoAction(logoContainer, i,menuStage);
        }

        VBox V=new VBox(50);
        V.getChildren().addAll(S,grid);
        V.setPadding(new Insets(50,50,50,50));
        Scene scene = new Scene(V, 400, 400);
        V.setBackground(backgroundObj);

        menuStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        menuStage.setWidth(mediumWidth);
        menuStage.setHeight(mediumHeight);
        menuStage.show();
    }

    private static StackPane createLogoContainer(String imageUrl, String labelText) {
        Rectangle rectangle = new Rectangle(180, 140);
        rectangle.setFill(Color.web("#5c8cba")); // Set background color to blue
        rectangle.setArcWidth(20); // Set border radius
        rectangle.setArcHeight(20);

        Image image = new Image(imageUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: black;-fx-font-size: 16px;"); // Set label text color to white

        StackPane logoContainer = new StackPane(rectangle, imageView, label);
        StackPane.setAlignment(label, Pos.BOTTOM_CENTER); // Align label to the bottom center

        return logoContainer;
    }

    public void setLogoAction(StackPane logoContainer, int index,Stage menuStage) {

        logoContainer.setOnMouseClicked(e -> {
            String action="";
            HospitalManagementLogin h=new HospitalManagementLogin();
            switch (index) {
                case 0:
                    // Logic for the first logo (Search)
                    action="Clicked Search Logo";
                    concatenateRows("user_actions.csv",h.getValidUsername(),action);
                    SearchModule.showDashboard();
                    System.out.println("Clicked Search Logo");
                    break;
                case 1:
                    // Logic for the second logo (Edit)
                    action="Clicked Edit Logo";
                    concatenateRows("user_actions.csv",h.getValidUsername(),action);
                    PatientEditModule p=new PatientEditModule();
                    p.showPatientModule();
                    System.out.println("Clicked Edit Logo");
                    break;
                case 2:
                    // Logic for the third logo (Delete)
                    action="Clicked Delete Logo";
                    concatenateRows("user_actions.csv",h.getValidUsername(),action);
                    Delete.showDashboard();
                    System.out.println("Clicked Delete Logo");
                    break;
                case 3:
                    // Logic for the fourth logo (Display)
                    action="Clicked Display Logo";
                    concatenateRows("user_actions.csv",h.getValidUsername(),action);
                    System.out.println("Clicked Display Logo");
                    break;
                case 4:
                    action="Clicked Fill Logo";
                    PatientFileModule patient=new PatientFileModule();
                    patient.showPatientFileModule();
                    concatenateRows("user_actions.csv",h.getValidUsername(),action);
                    System.out.println("Clicked Fill Logo");
                    break;
                case 5:
                    action="Clicked Appointment Logo";
                    concatenateRows("user_actions.csv",h.getValidUsername(),action);
                    AppointmentModule a=new AppointmentModule();
                   a.showAppointmentModule();
                    System.out.println("Clicked Appointment Logo");
                    break;
                case 6:
                    action="Clicked Fill Lab Logo";
                    concatenateRows("user_actions.csv",h.getValidUsername(),action);
                    Dashboard.showDashboard();
                    System.out.println("Clicked Fill Lab Logo");
                    break;
                case 7:
                    action="Clicked Save Logo";
                    concatenateRows("user_actions.csv",h.getValidUsername(),action);
                    // Logic for the eighth logo (Save)
                    System.out.println("Clicked Save Logo");
                    break;
                case 8:
                    action="Clicked Exit Logo";
                    concatenateRows("user_actions.csv",h.getValidUsername(),action);
                    // Logic for the ninth logo (Exit)
                    System.out.println("Clicked Exit Logo");
                    menuStage.close();
                    break;
                default:
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
                    StringBuilder modifiedLine = new StringBuilder(line);
                    modifiedLine.append(",").append(word); // Add the word to the end of the line
                    writer.println(modifiedLine);
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
            // Handle the exception or rethrow it if necessary
        }

    }
}


