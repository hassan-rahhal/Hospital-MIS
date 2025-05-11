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
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.sql.*;

public class DoctorModule {
    private TableView<Doctor> table = new TableView<Doctor>();
    private final ObservableList<Doctor> data = FXCollections.observableArrayList();
    String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc-connector";
    String username = "root";
    String password = "";
    Connection conn ;
    TextField firstNameTextField = new TextField();
    TextField lastNameTextField = new TextField();
    ComboBox<String> genderComboBox = new ComboBox<>();
    TextField specializationTextField = new TextField();
    public void Connect() {
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public boolean Register(String firstname,  String lastname,String gender, String specialization) {
        boolean output = false;

        try {
            String query = "INSERT INTO doctor (firstname, lastname, gender,specialization) VALUES (?, ?, ?,?)";
            try (PreparedStatement preparedStatement2 = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement2.setString(1, firstname);
                preparedStatement2.setString(2, lastname);
                preparedStatement2.setString(3,gender);
                preparedStatement2.setString(4,specialization );
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
            String query = "SELECT * FROM doctor";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String gender = rs.getString("gender");
                String specialization = rs.getString("specialization");


                data.add(new Doctor(firstName, lastName, gender, specialization));
            }

            table.setItems(data);

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error fetching data from database: " + e.getMessage());
        }
    }
    public  void showDoctorModule() {
        loadDataFromDatabase();
        // Create a new stage for the Doctor module

        Image backgroundImage = new Image("C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\back.png");

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
        R.setStroke(Color.web("#FFFFFF"));

        StackPane RR=new StackPane();
        RR.getChildren().addAll(R);
        RR.setAlignment(Pos.CENTER);



        Stage doctorStage = new Stage();
        doctorStage.setTitle("Doctor Module");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(40);
        gridPane.setHgap(20);

        gridPane.add(new Label("First Name:"), 0, 0);
        gridPane.add(firstNameTextField, 1, 0);

        gridPane.add(new Label("Last Name:"), 0, 1);
        gridPane.add(lastNameTextField, 1, 1);

        gridPane.add(new Label("Gender:"), 0, 2);
        genderComboBox.getItems().addAll("Male", "Female");
        gridPane.add(genderComboBox, 1, 2);

        gridPane.add(new Label("Specialization:"), 0, 3);
        gridPane.add(specializationTextField, 1, 3);

        // Add a button to save doctor information
        HospitalManagementLogin h=new HospitalManagementLogin();
        MenuModule m=new MenuModule();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (firstNameTextField.getText().length() != 0 && lastNameTextField.getText().length() != 0 && genderComboBox.getValue().length() != 0 && specializationTextField.getText().length() != 0) {
                    Connect();
                    boolean registrationSuccessful = Register(
                            firstNameTextField.getText(),
                            lastNameTextField.getText(),
                            genderComboBox.getValue(),
                            specializationTextField.getText()
                    );

                    if (registrationSuccessful) {
                        System.out.println("Registration successful.");
                        m.concatenateRows("user_actions.csv",h.getValidUsername(),"add a new Doctor");
                        data.add(new Doctor(
                                firstNameTextField.getText(),
                                lastNameTextField.getText(), genderComboBox.getValue(),specializationTextField.getText()));
                        firstNameTextField.clear();
                        lastNameTextField.clear();
                        genderComboBox.getSelectionModel().clearSelection();
                        specializationTextField.clear();
                    } else {
                        System.out.println("Registration failed.");
                    }

                }else{
                    System.out.println("Please fill in all fields and select a gender.");
                }
            }
            // Add logic to save doctor information
//            String firstName = firstNameTextField.getText();
//            String lastName = lastNameTextField.getText();
//            String gender = genderComboBox.getValue();
//            String specialization = specializationTextField.getText();
//
//            // Add your logic to save or process the information
//            System.out.println("Doctor Information Saved: " + firstName + " " + lastName +
//                    ", Gender: " + gender + ", Specialization: " + specialization);
//
//            // Close the module stage after saving
//            doctorStage.close();
        });
        Button exitBtn=new Button("Exit");
        exitBtn.setOnAction(e->{doctorStage.close();});
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(saveButton,exitBtn);
        gridPane.add(hBox, 1, 4);
        gridPane.setAlignment(Pos.CENTER);
        table.setEditable(true);
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(90);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Doctor, String>("firstName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(90);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Doctor, String>("lastName"));
        TableColumn GenderCol = new TableColumn("Gender ");
        GenderCol.setMinWidth(90);
        GenderCol.setCellValueFactory(
                new PropertyValueFactory<Doctor, String>("gender"));
        TableColumn SPCcol = new TableColumn("Specialization");
        SPCcol.setMinWidth(100);
        SPCcol.setCellValueFactory(
                new PropertyValueFactory<Doctor, String>("specialization"));
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol,GenderCol,SPCcol);
        VBox vBox=new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setSpacing(10);
        table.setMaxWidth(370);
        table.setMaxHeight(90);
        vBox.getChildren().addAll(gridPane,table);
        StackPane S=new StackPane();
        S.getChildren().addAll(RR,vBox);

        S.setBackground(backgroundObj);

        Scene scene = new Scene(S, 300, 200);
        doctorStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        doctorStage.setWidth(mediumWidth);
        doctorStage.setHeight(mediumHeight);
        doctorStage.show();
    }

    public  class Doctor{
        private SimpleStringProperty firstName;
        private  SimpleStringProperty lastName;
        private  SimpleStringProperty gender;
        private SimpleStringProperty specialization;
        public Doctor(String firstName, String lastName,String gender,String specialization){
            this.firstName=new SimpleStringProperty(firstName);
            this.lastName=new SimpleStringProperty(lastName);
            this.gender=new SimpleStringProperty(gender);
            this.specialization=new SimpleStringProperty(specialization);
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
        public String getGender() {
            return gender.get();
        }
        public void setGender(String g) {
            gender.set(g);
        }
        public String getSpecialization() {
            return specialization.get();
        }
        public void setSpecialization(String s) {
            specialization.set(s);
        }
    }
    }
