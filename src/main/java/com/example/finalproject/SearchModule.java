package com.example.finalproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SearchModule {
    public static void showDashboard() {
        // Initialize stage
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Hospital Management Search");

        // Load background image from resources
        Image backgroundImage = new Image("C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\back.png");
        BackgroundSize bgSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bgImg = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, bgSize);
        Background backgroundObj = new Background(bgImg);

        // Title bar overlay
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10));
        titleBox.setBackground(new Background(new BackgroundFill(Color.web("#ffffffaa"), new CornerRadii(10), Insets.EMPTY)));
        Label title = new Label("Search");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: #333;");
        titleBox.getChildren().add(title);

        // Create buttons for search options
        Button doctorMenuItem = new Button("Doctor Search");
        doctorMenuItem.setMinSize(180, 60);
        doctorMenuItem.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-background-color: white; " +
                        "-fx-text-fill: #08605F; " +
                        "-fx-background-radius: 8; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 1, 1);");
        doctorMenuItem.setOnMouseEntered(e -> doctorMenuItem.setOpacity(0.85));
        doctorMenuItem.setOnMouseExited(e -> doctorMenuItem.setOpacity(1.0));
        doctorMenuItem.setOnAction(e -> new DoctorSearchModule().showDoctorModule());

        Button appointmentMenuItem = new Button("Appointment Search");
        appointmentMenuItem.setMinSize(180, 60);
        appointmentMenuItem.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-background-color: white; " +
                        "-fx-text-fill: #08605F; " +
                        "-fx-background-radius: 8; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 1, 1);");
        appointmentMenuItem.setOnMouseEntered(e -> appointmentMenuItem.setOpacity(0.85));
        appointmentMenuItem.setOnMouseExited(e -> appointmentMenuItem.setOpacity(1.0));
        appointmentMenuItem.setOnAction(e -> new AppointmentSearchModule().showAppointmentModule());

        // Arrange buttons in a grid
        GridPane vbox = new GridPane();
        vbox.setAlignment(Pos.CENTER);
        vbox.setHgap(20);
        vbox.setVgap(20);
        vbox.setPadding(new Insets(20));
        vbox.add(titleBox, 0, 0, 2, 1);
        vbox.add(doctorMenuItem, 0, 1);
        vbox.add(appointmentMenuItem, 1, 1);
        vbox.setBackground(backgroundObj);

        // Scene and shortcuts
        Scene scene = new Scene(vbox);
        scene.setOnKeyPressed(evt -> {
            switch (evt.getCode()) {
                case D -> doctorMenuItem.fire();
                case A -> appointmentMenuItem.fire();
                case ESCAPE -> dashboardStage.close();
                default -> {}
            }
        });

        dashboardStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth() * 0.8;
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight() * 0.8;
        dashboardStage.setWidth(screenWidth);
        dashboardStage.setHeight(screenHeight);
        dashboardStage.centerOnScreen();
        dashboardStage.show();
    }
}
