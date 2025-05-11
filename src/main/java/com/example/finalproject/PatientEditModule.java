package com.example.finalproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientEditModule {
        String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc-connector";
        String username = "root";
        String password = "";
        Connection conn ;
         TextField idTextField=new TextField();
         TextField firstNameTextField = new TextField();
         TextField fatherNameTextField = new TextField();
         TextField lastNameTextField = new TextField();
         ComboBox<String> genderComboBox = new ComboBox<>();
         TextField DOBTextField=new TextField();
        public  void Connect() {
            try {
                conn = DriverManager.getConnection(jdbcUrl, username, password);
                System.out.println("Connected to the database!");
            } catch (SQLException e) {
                System.err.println("Error connecting to the database: " + e.getMessage());
            }
        }
    public void clearFields() {
        idTextField.clear();
        firstNameTextField.clear();
        fatherNameTextField.clear();
        lastNameTextField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        DOBTextField.clear();
    }
    public  void editPatient(String id, String firstName, String fatherName, String lastName, String gender, String dob) {
        try {
            String query = "UPDATE patient SET firstname=?, fathername=?, lastname=?, gender=?, dateofbirth=? WHERE idpatient=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, fatherName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, dob);
            preparedStatement.setString(6, id);

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Patient information updated successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update patient information! Invalid ID!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating patient information!");
            alert.showAndWait();
        }
    }
        public  void showPatientModule() {
            PatientEditModule patientEditModule = new PatientEditModule();
            // Create a new stage for the Patient module
            Stage patientStage = new Stage();
            patientStage.setTitle("Patient Module");

            Image backgroundImage = new Image("C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\back.png");

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

            StackPane RR=new StackPane();
            RR.getChildren().addAll(R);
            RR.setAlignment(Pos.CENTER);

            Label Patientlabel=new Label("Patient:");
            Patientlabel.setTextFill(Color.web("#FFFFFF"));
            Patientlabel.setAlignment(Pos.CENTER);
            Patientlabel.setFont(Font.font(50));

            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setVgap(30);
            gridPane.setHgap(20);

            gridPane.add(new Label("ID:"),0,0);
            gridPane.add(idTextField,1,0);

            gridPane.add(new Label("First Name:"), 0, 1);
            gridPane.add(firstNameTextField, 1, 1);

            gridPane.add(new Label("Father's Name:"), 0, 2);
            gridPane.add(fatherNameTextField, 1, 2);

            gridPane.add(new Label("Last Name:"), 0, 3);
            gridPane.add(lastNameTextField, 1, 3);

            gridPane.add(new Label("Gender:"),0,4);
            genderComboBox.getItems().addAll("Male", "Female");
            gridPane.add(genderComboBox, 1, 4);

            gridPane.add(new Label("Date of Birth:"), 0, 5);
            gridPane.add(DOBTextField, 1, 5);

            Button editButton = new Button("Edit");
            Button exitButton=new Button("Exit");
            exitButton.setOnAction(e->patientStage.close());
            HBox hBox=new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(10);
            hBox.getChildren().addAll(editButton,exitButton);
            gridPane.add(hBox, 1, 6);
            gridPane.setAlignment(Pos.CENTER);
            HospitalManagementLogin h=new HospitalManagementLogin();
            MenuModule m=new MenuModule();
            editButton.setOnAction(event -> {
                patientEditModule.Connect();
                String id = idTextField.getText();
                String firstName = firstNameTextField.getText();
                String fatherName = fatherNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String gender = genderComboBox.getValue();
                String dob = DOBTextField.getText();
                 if(id.isEmpty() || firstName.isEmpty()|| fatherName.isEmpty() || lastName.isEmpty()||gender.isEmpty()||dob.isEmpty()) {
                     Alert alert = new Alert(Alert.AlertType.ERROR);
                     alert.setTitle("Error");
                     alert.setHeaderText(null);
                     alert.setContentText("All Field are required!");
                     alert.showAndWait();
                 }else {
                     patientEditModule.editPatient(id, firstName, fatherName, lastName, gender, dob);
                     m.concatenateRows("user_actions.csv", h.getValidUsername(), "Edit a Patient");
                     clearFields();
                 }
            });
            VBox vBox=new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);
            vBox.getChildren().addAll(Patientlabel,gridPane);
            StackPane S=new StackPane();
            S.getChildren().addAll(RR,vBox);

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



