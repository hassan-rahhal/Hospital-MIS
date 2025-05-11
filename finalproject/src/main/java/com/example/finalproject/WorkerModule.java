package com.example.finalproject;
import java.util.Date;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.ParseException;
import javafx.concurrent.Worker;
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

public class WorkerModule {
    private TableView<Worker> table = new TableView<Worker>();
    private final ObservableList<Worker> data = FXCollections.observableArrayList();
    String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc-connector";
    String username = "root";
    String password = "W@2915djkq#";
    Connection conn ;
    TextField firstNameTextField = new TextField();
    TextField fatherNameTextField = new TextField();
    TextField lastNameTextField = new TextField();
    ComboBox<String> genderComboBox = new ComboBox<>();
    TextField DOBTextField = new TextField();
    ComboBox<String> positionComboBox = new ComboBox<>();
    public void Connect() {
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public boolean Register(String firstname, String fathername, String lastname, String gender, Date dateofbirth, String position) {
        boolean output = false;

        try {
            String query = "INSERT INTO worker (firstname, `father's name`, lastname, gender, DateOfBirth, position) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement2 = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement2.setString(1, firstname);
                preparedStatement2.setString(2, fathername);
                preparedStatement2.setString(3, lastname);
                preparedStatement2.setString(4, gender);

                // Use the correct format for MySQL date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(dateofbirth);
                preparedStatement2.setString(5, formattedDate);

                preparedStatement2.setString(6, position);

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
            String query = "SELECT * FROM worker";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String firstName = rs.getString("firstname");
                String fatherName = rs.getString("father's name");
                String lastName = rs.getString("lastname");
                String gender = rs.getString("gender");

                // Format the date retrieved from the database
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = dateFormat.parse(rs.getString("DateOfBirth"));
                String dateOfBirthString = dateFormat.format(dateOfBirth);
                String position = rs.getString("position");


                // Add the Worker object to the data list
                data.add(new Worker(firstName, fatherName, lastName, gender, dateOfBirthString, position));
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
    public  void showWorkerModule() {
        loadDataFromDatabase();
        // Create a new stage for the Worker module
        Stage workerStage = new Stage();
        workerStage.setTitle("Worker Module");

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
        DOBTextField.setPromptText("dd/MM/YYY");
        gridPane.add(DOBTextField, 1, 4);

        gridPane.add(new Label("Position:"), 0, 5);
        positionComboBox.getItems().addAll("Security", "Secretary");
        gridPane.add(positionComboBox, 1, 5);
        HospitalManagementLogin h=new HospitalManagementLogin();
        MenuModule m=new MenuModule();

        // Add a button to save doctor information
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (firstNameTextField.getText().length() != 0 && lastNameTextField.getText().length() != 0 && fatherNameTextField.getText().length() != 0 && genderComboBox.getValue().length()!=0 && DOBTextField.getText().length()!=0 && positionComboBox.getValue().length()!=0) {
                    Connect();
                    Date date = parseStringToDate(DOBTextField.getText());
                    boolean registrationSuccessful = Register(
                            firstNameTextField.getText(),
                            fatherNameTextField.getText(),
                            lastNameTextField.getText(), genderComboBox.getValue(),
                            date,
                            positionComboBox.getValue()
                    );

                    if (registrationSuccessful) {
                        System.out.println("Registration successful.");
                        m.concatenateRows("user_actions.csv",h.getValidUsername(),"add new Worker");
                        data.add(new Worker(
                                firstNameTextField.getText(),
                                fatherNameTextField.getText(),
                                lastNameTextField.getText(),genderComboBox.getValue(),DOBTextField.getText(),positionComboBox.getValue()));
                        firstNameTextField.clear();
                        fatherNameTextField.clear();
                        lastNameTextField.clear();
                        DOBTextField.clear();
                        genderComboBox.getSelectionModel().clearSelection();
                        positionComboBox.getSelectionModel().clearSelection();
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
//            String position = positionComboBox.getValue();


            // Add your logic to save or process the information
//
//            System.out.println("worker Information Saved: " + firstName + " " + fatherName +
//                    " " + lastName + " , Gender: " + gender + ", Date Of Birth: " + DOB +
//                    ", position: " + position);



            // Close the module stage after saving
//            workerStage.close();
        });
        Button exitBtn=new Button("Exit");
        exitBtn.setOnAction(e->{workerStage.close();});
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
                new PropertyValueFactory<Worker, String>("firstName"));
        TableColumn fatherNameCol = new TableColumn("Father's name");
        fatherNameCol.setMinWidth(90);
        fatherNameCol.setCellValueFactory(
                new PropertyValueFactory<Worker, String>("fatherName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(66);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Worker, String>("lastName"));
        TableColumn GenderCol = new TableColumn("Gender ");
        GenderCol.setMinWidth(66);
        GenderCol.setCellValueFactory(
                new PropertyValueFactory<Worker, String>("gender"));
        TableColumn DOBcol = new TableColumn("Date Of Birth");
        DOBcol.setMinWidth(90);
        DOBcol.setCellValueFactory(
                new PropertyValueFactory<Worker, String>("dateOfBirth"));
        TableColumn postionCol=new TableColumn("Position");
        postionCol.setMinWidth(66);
        postionCol.setCellValueFactory(
                new PropertyValueFactory<Worker, String>("position"));
        table.setItems(data);
        table.getColumns().addAll(firstNameCol,fatherNameCol, lastNameCol,GenderCol,DOBcol,postionCol);
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
        workerStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        workerStage.setWidth(mediumWidth);
        workerStage.setHeight(mediumHeight);
        workerStage.show();
    }
    public class Worker{
 private SimpleStringProperty firstName;
 private SimpleStringProperty lastName;
 private  SimpleStringProperty fatherName;
  private  SimpleStringProperty gender;
  private SimpleStringProperty dateOfBirth;
  private SimpleStringProperty position;
   public Worker(String firstName,String fatherName,String lastName,String gender,String dateOfBirth,String position){
       this.firstName=new SimpleStringProperty(firstName);
       this.fatherName=new SimpleStringProperty(fatherName);
       this.lastName=new SimpleStringProperty(lastName);
       this.gender=new SimpleStringProperty(gender);
       this.dateOfBirth=new SimpleStringProperty(dateOfBirth);
       this.position=new SimpleStringProperty(position);
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
        public String getPosition() {return position.get();
        }
        public void setPosition(String p) {
            position.set(p);
        }

    }
}
