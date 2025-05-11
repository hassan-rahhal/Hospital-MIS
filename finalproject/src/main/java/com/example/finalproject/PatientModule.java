package com.example.finalproject;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class PatientModule {
    private TableView<Patient> table = new TableView<Patient>();
    private final ObservableList<Patient> data = FXCollections.observableArrayList();
    String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc-connector";
    String username = "root";
    String password = "W@2915djkq#";
    Connection conn ;
    TextField firstNameTextField = new TextField();
    TextField fatherNameTextField = new TextField();
    TextField lastNameTextField = new TextField();
    ComboBox<String> genderComboBox = new ComboBox<>();
    TextField DOBTextField = new TextField();
    TextField idTextField = new TextField();
    public void Connect() {
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public boolean Register(String idpatient,String firstname,String fathername,  String lastname,String gender,String dateofbirth) {
        boolean output = false;

        try {
            String query = "INSERT INTO patient (idpatient,firstname,fathername ,lastname,gender,dateofbirth) VALUES (?, ?, ?,?,?,?)";
            try (PreparedStatement preparedStatement2 = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement2.setString(1, idpatient);
                preparedStatement2.setString(2,firstname);
                preparedStatement2.setString(3,fathername);
                preparedStatement2.setString(4,lastname );
                preparedStatement2.setString(5, gender);
                preparedStatement2.setString(6, dateofbirth);
                int rowsAffected = preparedStatement2.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Success.");
                    output = true;
                } else {
                    System.out.println("Failed.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return output;
    }
    public void loadDataFromDatabase() {
        Connect();
        try {
            String query = "SELECT * FROM patient";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String idpatient = rs.getString("idpatient");
                String firstName = rs.getString("firstname");
                String fatherName = rs.getString("fathername");
                String  lastName = rs.getString("lastname");
                String gender = rs.getString("gender");
                String  dateOfBirth = rs.getString("dateofbirth");

                data.add(new Patient(firstName , fatherName, lastName, gender, dateOfBirth,idpatient));
            }

            table.setItems(data);

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error fetching data from database: " + e.getMessage());
        }
    }

    public  void showPatientModule() {
        loadDataFromDatabase();
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

        Rectangle R=new Rectangle(550,590);
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

        gridPane.add(new Label("First Name:"), 0, 0);
        gridPane.add(firstNameTextField, 1, 0);

        gridPane.add(new Label("Father's Name:"), 0, 1);
        gridPane.add(fatherNameTextField, 1, 1);

        gridPane.add(new Label("Last Name:"), 0, 2);
        gridPane.add(lastNameTextField, 1, 2);

        gridPane.add(new Label("Gender:"), 0, 3);
        genderComboBox.getItems().addAll("Male", "Female");
        gridPane.add(genderComboBox, 1, 3);

        gridPane.add(new Label("Date of Birth:"), 0, 4);
        gridPane.add(DOBTextField, 1, 4);

        gridPane.add(new Label("ID:"), 0, 5);
        gridPane.add(idTextField, 1, 5);

        // Add a button to save patient information
        HospitalManagementLogin h=new HospitalManagementLogin();
        MenuModule m=new MenuModule();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (firstNameTextField.getText().length() != 0 && lastNameTextField.getText().length() != 0 && fatherNameTextField.getText().length() != 0 && genderComboBox.getValue().length()!=0 && DOBTextField.getText().length()!=0 && idTextField.getText().length()!=0) {
                    Connect();
                    boolean registrationSuccessful = Register(
                            idTextField.getText(),
                            firstNameTextField.getText(),
                            fatherNameTextField.getText(),
                            lastNameTextField.getText(),
                            genderComboBox.getValue(),
                            DOBTextField.getText()
                    );

                    if (registrationSuccessful) {
                        System.out.println("Registration successful.");
                        m.concatenateRows("user_actions.csv",h.getValidUsername(),"add new Patient");
                        data.add(new Patient(
                                firstNameTextField.getText(),
                                fatherNameTextField.getText(),
                                lastNameTextField.getText(),genderComboBox.getValue(),DOBTextField.getText(),idTextField.getText()));
                        idTextField.clear();
                        firstNameTextField.clear();
                        fatherNameTextField.clear();
                        lastNameTextField.clear();
                        genderComboBox.getSelectionModel().clearSelection();
                        DOBTextField.clear();
                    } else {
                        System.out.println("Registration failed.");
                    }

                }else{
                    System.out.println("Please fill in all fields and select a gender.");
                }
            }
            // Add logic to save doctor information
//            String firstName = firstNameTextField.getText();
//            String fatherName = fatherNameTextField.getText();
//            String lastName = lastNameTextField.getText();
//            String gender = genderComboBox.getValue();
//            String DOB = DOBTextField.getText();
//            String id = idTextField.getText();
//
//
//            // Add your logic to save or process the information
//
//            System.out.println("Patient Information Saved: " + firstName + " " + fatherName + " " +
//                    lastName + " , Gender: " + gender + ", Date of Birth: " + DOB + ", ID: " + id);
//
//            // Close the module stage after saving
//            patientStage.close();
        });
        Button exitBtn=new Button("Exit");
        exitBtn.setOnAction(e->{patientStage.close();});
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(saveButton,exitBtn);
        gridPane.add(hBox, 1, 6);
        gridPane.setAlignment(Pos.CENTER);
        table.setEditable(true);
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(66);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Patient, String>("firstName"));
        TableColumn fatherNameCol = new TableColumn("Father's name");
        fatherNameCol.setMinWidth(90);
        fatherNameCol.setCellValueFactory(
                new PropertyValueFactory<Patient, String>("fatherName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(66);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Patient, String>("lastName"));
        TableColumn GenderCol = new TableColumn("Gender ");
        GenderCol.setMinWidth(66);
        GenderCol.setCellValueFactory(
                new PropertyValueFactory<Patient, String>("gender"));
        TableColumn DOBcol = new TableColumn("Date Of Birth");
        DOBcol.setMinWidth(90);
        DOBcol.setCellValueFactory(
                new PropertyValueFactory<Patient, String>("dateOfBirth"));
        TableColumn IDCol=new TableColumn("ID");
        IDCol.setMinWidth(66);
        IDCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("ID"));
        table.setItems(data);
        table.getColumns().addAll(firstNameCol,fatherNameCol, lastNameCol,GenderCol,DOBcol,IDCol);
        VBox vBox=new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setSpacing(10);
        table.setMaxWidth(500);
        table.setMaxHeight(90);
        vBox.getChildren().addAll(gridPane,table);
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

    public  class Patient{
        private SimpleStringProperty firstName;
        private SimpleStringProperty fatherName;
        private SimpleStringProperty lastName;
         private SimpleStringProperty gender;
         private  SimpleStringProperty dateOfBirth;
         private  SimpleStringProperty ID;
        public Patient(String firstName,String fatherName,String lastName,String gender,String dateOfBirth,String ID ){
            this.firstName=new SimpleStringProperty(firstName);
            this.fatherName=new SimpleStringProperty(fatherName);
            this.lastName=new SimpleStringProperty(lastName);
            this.gender=new SimpleStringProperty(gender);
            this.dateOfBirth=new SimpleStringProperty(dateOfBirth);
            this.ID=new SimpleStringProperty(ID);
        }
        public String getFirstName() {
            return firstName.get();
        }
        public void setFirstName(String fName) {
            firstName.set(fName);
        }
        public String getLastName() {
            return lastName.get();
        }
        public void setLastName(String lName) {
            lastName.set(lName);
        }
        public String getFatherName() {
            return fatherName.get();
        }
        public void setFatherName(String FName) {
            fatherName.set(FName);
        }
        public String getGender() {
            return gender.get();
        }
        public void setGender(String g) {
            gender.set(g);
        }
        public String getDateOfBirth() {
            return dateOfBirth.get();
        }
        public void setDateOfBirth(String date) {
            dateOfBirth.set(date);
        }
        public String getID() {
            return ID.get();
        }
        public void SetID(String id) {
            ID.set(id);
        }
    }

}
