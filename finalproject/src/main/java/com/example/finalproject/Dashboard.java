package com.example.finalproject;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Dashboard {

    public static void showDashboard() {
        // Create a new stage for the dashboard

        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Hospital Management Dashboard");

        Image backgroundImage = new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\download.jpg");

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        Background backgroundObj = new Background(background);


        // Create a menu bar with a sample menu
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        fileMenu.setStyle("-fx-font-size: 35;  -fx-text-fill: black;");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> dashboardStage.close());
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        menuBar.setStyle("-fx-background-color: white; -fx-text-fill: white;");
        menuBar.setPrefWidth(800);
        menuBar.setPrefHeight(100);

        // Create a dropdown menu for different modules
        Menu modulesMenu = new Menu("Modules");
        modulesMenu.setStyle("-fx-font-size: 35; -fx-text-fill: black;");


        // Submenu for Doctor
        MenuItem doctorMenuItem = new MenuItem("Doctor");
        doctorMenuItem.setOnAction(e -> {
                DoctorModule d=new DoctorModule();
                d.showDoctorModule();});

        // Submenu for Patient (You can add other modules similarly)
        MenuItem patientMenuItem = new MenuItem("Patient");
        patientMenuItem.setOnAction(e -> {

            handleMenuItemClick("Patient");
            PatientModule p=new PatientModule();
            p.showPatientModule();
        });
        MenuItem workerMenuItem = new MenuItem("Worker");
        workerMenuItem.setOnAction(e -> {

            handleMenuItemClick("Worker");
            WorkerModule w=new WorkerModule();
            w.showWorkerModule();
        });

        MenuItem patientfileMenuItem = new MenuItem("Patient File");
        patientfileMenuItem.setOnAction(e -> {

            handleMenuItemClick("Patient File");
           PatientFileModule patientFileModule=new PatientFileModule();
           patientFileModule.showPatientFileModule();
        });

        exitMenuItem.setStyle("-fx-font-size: 25;");
        doctorMenuItem.setStyle("-fx-font-size: 25;");
        patientMenuItem.setStyle("-fx-font-size: 25;");
        workerMenuItem.setStyle("-fx-font-size: 25;");
        patientfileMenuItem.setStyle("-fx-font-size: 25;");

        modulesMenu.getItems().addAll(doctorMenuItem, patientMenuItem,workerMenuItem,patientfileMenuItem);

        // Add the dropdown menu to the menu bar
        menuBar.getMenus().add(modulesMenu);

        // Create the layout
        VBox vbox = new VBox(menuBar);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setBackground(backgroundObj);

        Scene scene = new Scene(vbox, 400, 300);

        dashboardStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        dashboardStage.setWidth(mediumWidth);
        dashboardStage.setHeight(mediumHeight);
        dashboardStage.show();
    }

    private static void handleMenuItemClick(String module) {
        System.out.println("Opening module: " + module);
        // Add your logic to open the corresponding module
    }
}
