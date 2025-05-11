package com.example.finalproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.sql.ResultSet;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PatientSearchModule {
    String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc-connector";
    String username = "root";
    String password = "W@2915djkq#";
    Connection conn ;
    public void Connect() {
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public  void showPatientModule() {
        // Create a new stage for the Patient module
        Stage PatientStage = new Stage();
        PatientStage.setTitle("Patient search Module");

        Image backgroundImage = new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\download.jpg");

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        Background backgroundObj = new Background(background);

        Rectangle R=new Rectangle(500,500);
        R.setFill(Color.WHITE);
        R.setStrokeWidth(30);
        R.setStroke(Color.web("#5c8cba"));

        StackPane RR=new StackPane();
        RR.getChildren().addAll(R);
        RR.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(30);
        gridPane.setHgap(20);

        gridPane.add(new Label("ID:"), 0, 0);
        TextField idTextField = new TextField();
        gridPane.add(idTextField, 1, 0);

        Button searchButton = new Button("search");
        Button exitButton=new Button("Exit");
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(searchButton,exitButton);
        gridPane.add(hBox, 1, 4);
        gridPane.setAlignment(Pos.CENTER);
        exitButton.setOnAction(e->{PatientStage.close();});
        HospitalManagementLogin h=new HospitalManagementLogin();
        MenuModule m=new MenuModule();
        searchButton.setOnAction(event -> {
            String id = idTextField.getText().trim();


            if (id.isEmpty()) {
                // Show an alert if any of the fields are empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter id.");
                alert.showAndWait();
            } else {
                try {
                    Connect();
                    String sql = "SELECT * FROM patient WHERE idpatient= ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, id);
                    ResultSet resultSet = statement.executeQuery();
                    m.concatenateRows("user_actions.csv",h.getValidUsername(),"Search a Patient");
                    if (resultSet.next()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Patient Found");
                        alert.setHeaderText(null);
                        String PatientName = resultSet.getString("idpatient") + " " + resultSet.getString("firstname")+" "+resultSet.getString("fathername")+" "+resultSet.getString("lastname");
                        alert.setContentText("Patient Found:"+PatientName);
                        alert.showAndWait();
                        idTextField.clear();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Patient not found");
                        alert.setHeaderText(null);
                        alert.setContentText("No matching patient found .");
                        alert.showAndWait();
                        idTextField.clear();

                    }

                    // Close the statement and result set
                    statement.close();
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle any SQL exceptions here
                }
            }
        });

        StackPane S=new StackPane();
        S.getChildren().addAll(RR,gridPane);

        S.setBackground(backgroundObj);

        Scene scene = new Scene(S, 300, 200);
        PatientStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        PatientStage.setWidth(mediumWidth);
        PatientStage.setHeight(mediumHeight);
        PatientStage.show();
    }
}
