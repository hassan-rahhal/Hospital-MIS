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

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentModule {
    private TableView<Appointment> table = new TableView<Appointment>();
    private final ObservableList<Appointment> data = FXCollections.observableArrayList();
    String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc-connector";
    String username = "root";
    String password = "";
    Connection conn ;
    TextField appIDTextField = new TextField();
    TextField appDateTextField = new TextField();
    TextField appTimeTextField = new TextField();
    TextField patientIDTextField = new TextField();
    TextField doctorNameTextField = new TextField();
    public void Connect() {
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public boolean Register(String appid, Date appDate, String apptime, String patientId, String doctorname) {
        boolean output = false;
        try {

            String patientQuery = "SELECT * FROM patient WHERE idpatient= ?";
            try (PreparedStatement patientStmt = conn.prepareStatement(patientQuery)) {
                patientStmt.setString(1, patientId);
                ResultSet patientResult = patientStmt.executeQuery();


                if (!patientResult.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Added Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("No matching patient ID found.");
                    alert.showAndWait();
                    System.out.println("Patient does not exist.");
                    return false;
                }
            }
            String doctorQuery = "SELECT * FROM doctor WHERE CONCAT(firstname, ' ', lastname) = ?";
            try (PreparedStatement doctorStmt = conn.prepareStatement(doctorQuery)) {
                doctorStmt.setString(1, doctorname);
                ResultSet doctorResult = doctorStmt.executeQuery();

                // If doctorname does not exist in the doctor table, exit registration process
                if (!doctorResult.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Added Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("No matching doctor found.");
                    alert.showAndWait();
                    System.out.println("Doctor does not exist.");
                    return false;
                }
            }


            // Proceed with appointment registration if patient and doctor exist
            String query = "INSERT INTO appointment (appId, appDate, apptime, patientID, doctorName) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement2 = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement2.setString(1, appid);

                // Use the correct format for MySQL date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(appDate);
                preparedStatement2.setString(2, formattedDate);

                preparedStatement2.setString(3, apptime);

                preparedStatement2.setString(4, patientId);

                preparedStatement2.setString(5, doctorname);

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
            String query = "SELECT * FROM appointment";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String appId = rs.getString("appId");
                // Format the date retrieved from the database
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date appDate = dateFormat.parse(rs.getString("appDate"));
                // Format the date into "dd/MM/yyyy" if needed
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                String appDateString = outputFormat.format(appDate);
                String appTime = rs.getString("apptime");
                String patientID = rs.getString("patientID");
                String doctorName = rs.getString("doctorName");





                // Add the Worker object to the data list
                data.add(new Appointment(appId,appDateString ,appTime, patientID,doctorName));
            }

            table.setItems(data);

            rs.close();
            stmt.close();
        } catch (SQLException | ParseException e) {
            System.err.println("Error fetching data from database: " + e.getMessage());
        }
    }
    public Date parseStringToDate(String dateString) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public  void showAppointmentModule() {
        loadDataFromDatabase();
        // Create a new stage for the Appointment module
        Stage appointmentStage = new Stage();
        appointmentStage.setTitle(" Appointment Module");

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

        StackPane RR=new StackPane();
        RR.getChildren().addAll(R);
        RR.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(30);
        gridPane.setHgap(20);

        gridPane.add(new Label("Appointment ID:"), 0, 0);
        gridPane.add(appIDTextField, 1, 0);

        gridPane.add(new Label("Appointment date:"), 0, 1);
        appDateTextField.setPromptText("dd/MM/YYYY");
        gridPane.add(appDateTextField, 1, 1);

        gridPane.add(new Label("Appointment time"), 0, 2);
        gridPane.add(appTimeTextField, 1, 2);

        gridPane.add(new Label(" Patient's ID:"), 0, 3);
        gridPane.add(patientIDTextField, 1, 3);

        gridPane.add(new Label("Doctor full name:"), 0, 4);
        gridPane.add(doctorNameTextField, 1, 4);
 HospitalManagementLogin h=new HospitalManagementLogin();
 MenuModule m=new MenuModule();
        // Add a button to save appointment information
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (appIDTextField.getText().length() != 0 && appDateTextField.getText().length() != 0 && appTimeTextField.getText().length() != 0 && patientIDTextField.getText().length()!=0 &&  doctorNameTextField.getText().length()!=0 ) {
                    Connect();
                    Date date = parseStringToDate(appDateTextField.getText());
                    boolean registrationSuccessful = Register(
                            appIDTextField.getText(),
                           date,
                            appTimeTextField.getText(),
                            patientIDTextField.getText(),
                            doctorNameTextField.getText()
                    );

                    if (registrationSuccessful) {
                        System.out.println("Registration successful.");
                        m.concatenateRows("user_actions.csv",h.getValidUsername(),"add new Appointment");
                        data.add(new Appointment(
                                appIDTextField.getText(),
                                appDateTextField.getText(),
                                appTimeTextField.getText(),patientIDTextField.getText(),doctorNameTextField.getText()));
                        appIDTextField.clear();
                        appDateTextField.clear();
                        appTimeTextField.clear();
                        patientIDTextField.clear();
                       doctorNameTextField.clear();
                    } else {
                        System.out.println("Registration failed.");
                    }

                }else{
                    System.out.println("Please fill in all fields and select a gender.");
                }
            }
        });
        Button exitButton=new Button("Exit");
        exitButton.setOnAction(e->appointmentStage.close());
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(saveButton,exitButton);
        gridPane.add(hBox, 1, 5);
        gridPane.setAlignment(Pos.CENTER);
        table.setEditable(true);
        TableColumn IDCol = new TableColumn("ID");
        IDCol.setMinWidth(66);
        IDCol.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("ID"));
        TableColumn DateCol = new TableColumn("Date");
        DateCol.setMinWidth(90);
        DateCol.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("date"));
        TableColumn timecol = new TableColumn("Time");
        timecol.setMinWidth(66);
       timecol.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("time"));
        TableColumn patientCol = new TableColumn("Patient ID ");
        patientCol.setMinWidth(99);
        patientCol.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("patientID"));
        TableColumn doctorCol = new TableColumn("Doctor Name");
        doctorCol.setMinWidth(99);
        doctorCol.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("doctorName"));
        table.setItems(data);
        table.getColumns().addAll(IDCol,DateCol, timecol,patientCol,doctorCol);
        VBox vBox=new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setSpacing(10);
        table.setMaxWidth(450);
        table.setMaxHeight(90);
        vBox.getChildren().addAll(gridPane,table);
        StackPane S=new StackPane();
        S.getChildren().addAll(RR,vBox);

        S.setBackground(backgroundObj);

        Scene scene = new Scene(S, 300, 200);
        appointmentStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        appointmentStage.setWidth(mediumWidth);
        appointmentStage.setHeight(mediumHeight);
        appointmentStage.show();
    }
    public class Appointment{
     private SimpleStringProperty ID;
     private  SimpleStringProperty date;
     private SimpleStringProperty time;
     private SimpleStringProperty patientID;
     private SimpleStringProperty doctorName;
     public Appointment(String ID,String date,String time, String patientID,String doctorName){
         this.ID=new SimpleStringProperty(ID);
         this.date=new SimpleStringProperty(date);
         this.time=new SimpleStringProperty(time);
         this.patientID=new SimpleStringProperty(patientID);
         this.doctorName=new SimpleStringProperty(doctorName);
     }
     public String getID(){
         return ID.get();
     }
   public void SetID(String id){
         ID.set(id);
   }
   public String getDate(){
         return date.get();
   }
   public void Setdate(String d){
            date.set(d);
        }
   public String getTime(){
         return time.get();
   }
    public void  setTime(String t) {
         time.set(t);
    }
    public String getPatientID(){
         return patientID.get();
    }
    public void setPatientID(String pid){
         patientID.set(pid);
    }
        public String getDoctorName(){
            return doctorName.get();
        }
        public void setDoctorName(String dn){
            doctorName.set(dn);
        }

    }
}
