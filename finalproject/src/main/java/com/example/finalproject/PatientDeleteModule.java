package com.example.finalproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PatientDeleteModule {
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
    public void deletePatient(String id,String firstName, String fatherName, String lastName) {
        try {
            String query = "DELETE FROM patient WHERE idpatient=? AND firstname = ? AND fathername = ? AND lastname = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, fatherName);
            preparedStatement.setString(4, lastName);

            int deletedRows = preparedStatement.executeUpdate();

            if (deletedRows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Patient Deleted");
                alert.setHeaderText(null);
                alert.setContentText("Patient deleted successfully.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete Failed");
                alert.setHeaderText(null);
                alert.setContentText("No matching patient found to delete.");
                alert.showAndWait();
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void showPatientModule() {
        // Create a new stage for the Patient module
        Stage patientStage = new Stage();
        patientStage.setTitle("Patient Module");

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
        gridPane.add(new Label("ID:"),0,0);
        TextField idTextField=new TextField();
        gridPane.add(idTextField,1,0);

        gridPane.add(new Label("First Name:"), 0, 1);
        TextField firstNameTextField = new TextField();
        gridPane.add(firstNameTextField, 1, 1);

        gridPane.add(new Label("Father's Name:"), 0, 2);
        TextField fatherNameTextField = new TextField();
        gridPane.add(fatherNameTextField, 1, 2);

        gridPane.add(new Label("Last Name:"), 0, 3);
        TextField lastNameTextField = new TextField();
        gridPane.add(lastNameTextField, 1, 3);
        Button deleteButton = new Button("delete");
        Button exitButton=new Button("Exit");
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(deleteButton,exitButton);
        gridPane.add(hBox, 1, 4);
        gridPane.setAlignment(Pos.CENTER);
        exitButton.setOnAction(e->{patientStage.close();});
        HospitalManagementLogin h=new HospitalManagementLogin();
        MenuModule m=new MenuModule();
        deleteButton.setOnAction(event -> {
            String id=idTextField.getText();
            String firstName = firstNameTextField.getText();
            String fatherName = fatherNameTextField.getText();
            String lastName = lastNameTextField.getText();

            if (!firstName.isEmpty() && !fatherName.isEmpty() && !lastName.isEmpty() && !id.isEmpty()) {
                PatientDeleteModule patientDeleteModule = new PatientDeleteModule();
                patientDeleteModule.Connect();
                patientDeleteModule.deletePatient(id,firstName, fatherName, lastName);
                m.concatenateRows("user_actions.csv",h.getValidUsername(),"delete a Patient");
                firstNameTextField.clear();
                lastNameTextField.clear();
                fatherNameTextField.clear();
                idTextField.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all fields.");
                alert.showAndWait();
            }
        });

        StackPane S=new StackPane();
        S.getChildren().addAll(RR,gridPane);

        S.setBackground(backgroundObj);

        Scene scene = new Scene(S, 300, 200);
        patientStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        patientStage.setWidth(mediumWidth);
        patientStage.setHeight(mediumHeight);
        patientStage.show();
    }
}

