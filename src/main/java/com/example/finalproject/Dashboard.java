package com.example.finalproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Dashboard {

    public static void showDashboard() {
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Hospital Management Dashboard");

        Image backgroundImage = new Image("C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\back.png");

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        Background backgroundObj = new Background(background);


        // Module Buttons Styled Like Search Tiles
        Label moduleLabel = new Label("Select a Module:");
        moduleLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");
        moduleLabel.setAlignment(Pos.CENTER);

        Button doctorButton = new Button("Doctor");
        doctorButton.setStyle("-fx-font-size: 24px; -fx-background-color: #FFFFFF; -fx-text-fill: #08605F; -fx-font-weight: bold;");
        doctorButton.setPrefWidth(300);
        doctorButton.setOnAction(e -> {
            DoctorModule d = new DoctorModule();
            d.showDoctorModule();
        });

        Button patientButton = new Button("Patient");
        patientButton.setStyle("-fx-font-size: 24px; -fx-background-color: #FFFFFF; -fx-text-fill: #08605F; -fx-font-weight: bold;");
        patientButton.setPrefWidth(300);
        patientButton.setOnAction(e -> {
            handleMenuItemClick("Patient");
            PatientModule p = new PatientModule();
            p.showPatientModule();
        });

        VBox moduleButtons = new VBox(20, moduleLabel, doctorButton, patientButton);
        moduleButtons.setAlignment(Pos.CENTER);
        moduleButtons.setPadding(new Insets(30));

        VBox vbox = new VBox(moduleButtons);
        vbox.setBackground(backgroundObj);

        Scene scene = new Scene(vbox, 400, 300);
        dashboardStage.setScene(scene);

        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        dashboardStage.setWidth(screenWidth * 0.9);
        dashboardStage.setHeight(screenHeight * 0.9);
        dashboardStage.show();
    }

    private static void handleMenuItemClick(String module) {
        System.out.println("Opening module: " + module);
    }
}
