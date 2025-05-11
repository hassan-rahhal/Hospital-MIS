package com.example.finalproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.*;

public class PatientFileModule {
    String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc-connector";
    String username = "root";
    String password = "W@2915djkq#";
    Connection conn ;

    RadioButton rb1=new RadioButton("A+");
    RadioButton rb2=new RadioButton("A-");
    RadioButton rb3=new RadioButton("B+");
    RadioButton rb4=new RadioButton("B-");
    RadioButton rb5=new RadioButton("O+");
    RadioButton rb6=new RadioButton("O-");
    RadioButton rb7=new RadioButton("AB+");
    RadioButton rb8=new RadioButton("AB-");
    ToggleGroup BloodType = new ToggleGroup();
    RadioButton Rb1 = new RadioButton("Diabetes");
    RadioButton Rb2 = new RadioButton("Asthma");
    RadioButton Rb3 = new RadioButton("Heart Disease");
    RadioButton Rb4 = new RadioButton("Genetic Disorder");
    RadioButton Rb5 = new RadioButton("Cancer");
    TextField registerIdTextField=new TextField();
    TextField patientNameTextField=new TextField();
    TextField doctorNameTextField=new TextField();
    TextField nurseNameTextField=new TextField();
    TextField labtechNameTextField=new TextField();
    TextField radtechNameTextField=new TextField();
    ComboBox <String> Height=new ComboBox<>();

    ComboBox <String> Weight=new ComboBox<>();
    ComboBox <String> Gender=new ComboBox<>();
    ComboBox<String> CB = new ComboBox<>();
    RadioButton RB1=new RadioButton("Penicillin");
    RadioButton RB2=new RadioButton("Sulfonamides");
    RadioButton RB3=new RadioButton("Anticonvulsants");
    RadioButton RB4=new RadioButton("Aspirin");
    RadioButton RB5=new RadioButton("Ibuprofen");
    public void Connect() {
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public boolean Register(String registerID,String PatientName,String DoctorName,  String gender,String height,String weight,String nurseName,String labTechName,String RadTechName) {
        boolean output = false;

        try {
            String query = "INSERT INTO register (registerID,PatientName,DoctorName ,gender,height,weight,nurseName,labTechName,RadTechName) VALUES (?, ?, ?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement2 = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                int id=Integer.valueOf(registerID);
                preparedStatement2.setInt(1, id);
                preparedStatement2.setString(2,PatientName);
                preparedStatement2.setString(3,DoctorName);
                preparedStatement2.setString(4,gender );
                preparedStatement2.setString(5, height);
                preparedStatement2.setString(6, weight);
                preparedStatement2.setString(7, nurseName);
                preparedStatement2.setString(8, labTechName);
                preparedStatement2.setString(9, RadTechName);

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
    public  void showPatientFileModule() {
        // Create a new stage for the file module
        Stage patientfileStage = new Stage();
        patientfileStage.setTitle("Patient File Module");

        Image backgroundImage = new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\download.jpg");

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        Background backgroundObj = new Background(background);

        VBox VMain = new VBox(40);


        VBox V1 = new VBox();
        StackPane s=new StackPane();
        VBox V= new VBox(10);


        Text t=new Text("Allergies");
        Rectangle r=new Rectangle(600,30);





        HBox H=new HBox(30);
        H.getChildren().addAll(RB1,RB2,RB3,RB4,RB5);
        H.setAlignment(Pos.CENTER_LEFT);
        H.setPadding(new Insets(0,0,0,10));

        s.setAlignment(Pos.TOP_LEFT);
        s.getChildren().addAll(r,H);


        r.setFill(Color.LIGHTBLUE);
        r.setStrokeWidth(1);
        r.setStroke(Color.BLACK);
        V.getChildren().addAll(t,s);
        V1.getChildren().add(V);




        Text t2=new Text("Blood Type");
        Rectangle r2=new Rectangle(600,30);


        rb1.setToggleGroup(BloodType);
        rb2.setToggleGroup(BloodType);
        rb3.setToggleGroup(BloodType);
        rb4.setToggleGroup(BloodType);
        rb5.setToggleGroup(BloodType);
        rb6.setToggleGroup(BloodType);
        rb7.setToggleGroup(BloodType);
        rb8.setToggleGroup(BloodType);


        HBox H2=new HBox(30);
        H2.getChildren().addAll(rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8);
        H2.setAlignment(Pos.CENTER_LEFT);
        H2.setPadding(new Insets(0,0,0,10));

        StackPane s2=new StackPane();
        s2.setAlignment(Pos.TOP_LEFT);
        s2.getChildren().addAll(r2,H2);
        r2.setFill(Color.LIGHTBLUE);
        r2.setStrokeWidth(1);
        r2.setStroke(Color.BLACK);
        VBox V2= new VBox(10);
        V2.getChildren().addAll(t2,s2);


        Rectangle r3 = new Rectangle(600, 30);
        Text t3 = new Text("Inherited Diseases");

        HBox H3 = new HBox(30);
        H3.getChildren().addAll(Rb1, Rb2, Rb3, Rb4, Rb5);
        H3.setAlignment(Pos.CENTER_LEFT);
        H3.setPadding(new Insets(0, 0, 0, 10));

        StackPane s3 = new StackPane();
        s3.setAlignment(Pos.TOP_LEFT);
        s3.getChildren().addAll(r3, H3);

        r3.setFill(Color.LIGHTBLUE);
        r3.setStrokeWidth(1);
        r3.setStroke(Color.BLACK);
        VBox V3 = new VBox(10);
        V3.getChildren().addAll(t3, s3);





        Image I=new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Xray.jpg");
        Image I2=new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Xray1.jpg");
        Image I3=new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Xray3.jpg");
        Image I4=new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Xray4.jpg");

        ImageView IV=new ImageView(I);
        IV.setFitWidth(40);
        IV.setFitHeight(40);

        ImageView IV2=new ImageView(I2);
        IV2.setFitWidth(40);
        IV2.setFitHeight(40);

        ImageView IV3=new ImageView(I3);
        IV3.setFitWidth(40);
        IV3.setFitHeight(40);

        ImageView IV4=new ImageView(I4);
        IV4.setFitWidth(40);
        IV4.setFitHeight(40);

        String[] imageTitles = {"XRAY 1", "XRAY 2", "XRAY 3", "XRAY 4" };

        CB.setPromptText("Image Folder");
        CB.getItems().addAll(imageTitles);

        ImageView Imageview=new ImageView();
        Imageview.setFitWidth(200);
        Imageview.setFitHeight(200);

        CB.setOnAction(e -> {
            String selectedTitle = CB.getValue();
            Image selectedImage = getImageByTitle(selectedTitle);
            Imageview.setImage(selectedImage);
        });


        Text t4=new Text("Doctor's Notes: ");
        TextArea T1=new TextArea();
        T1.setMaxWidth(350);
        T1.setMaxHeight(200);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(T1);
        scroll.setMaxWidth(200);



        VMain.setAlignment(Pos.TOP_LEFT);
        VMain.setPadding(new Insets(30,30,30,30));
        VMain.getChildren().addAll(V1,V2,V3,T1,scroll,CB,Imageview);




        Label L1=new Label("Register ID:");
        Label L2=new Label("Patient's Full Name");
        Label L3=new Label("Doctor's Full Name");
        Label L4=new Label("Nurse's Full Name");
        Label L5=new Label("Laboratory Technician's Full Name");
        Label L6=new Label("Radiology Technician's Full Name");

        for (int i = 1; i <= 250; i++) {
            Height.getItems().add(String.valueOf(i));
        }
        Height.setValue("Height");
        for (int i = 1; i <= 250; i++) {
            Weight.getItems().add(String.valueOf(i));
        }
        Weight.setValue("Weight");
        Gender.getItems().addAll("Female","Male");
        Gender.setValue("Gender");



        Button save=new Button("Save Data");

        Button saveButton = new Button("Save");
        HospitalManagementLogin h=new HospitalManagementLogin();
        MenuModule m=new MenuModule();
        save.setOnAction(e -> {
            // Add logic to save doctor information
            String registerId=registerIdTextField.getText();
            String patientName = patientNameTextField.getText();
            String gender = Gender.getValue();
            String height= Height.getValue();
            String weight= Weight.getValue();
            String doctorName = doctorNameTextField.getText();
            String nurseName = nurseNameTextField.getText();
            String labtechName = labtechNameTextField.getText();
            String radtechName = radtechNameTextField.getText();
            if(registerId.length()!=0 && patientName.length()!=0 && !gender.equals("Gender") && !height.equals("Height") && !weight.equals("Weight") && doctorName.length()!=0 && nurseName.length()!=0 && labtechName.length()!=0 && radtechName.length()!=0) {
  Connect();
                boolean registrationSuccessful = Register(registerId, patientName, doctorName, gender, height, weight, nurseName, labtechName, radtechName);

                // Check if registration was successful
                if (registrationSuccessful) {
                    // Inform the user about successful registration
                    System.out.println("Patient file information successfully saved.");
                    m.concatenateRows("user_actions.csv", h.getValidUsername(), "save new Patient File");

                    // Close the module stage after saving
                    patientfileStage.close();
                } else {
                    // Inform the user about failed registration
                    System.out.println("Failed to save patient file information. Please try again.");
                }
            } else {
                // Inform the user to fill all the required fields
                System.out.println("Please fill all the required fields.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ADD Patient file Failed");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the required fields!.");
                alert.showAndWait();
            }
        });

        VBox VV1=new VBox(40);
        VBox VV2=new VBox(30);

        VV1.getChildren().addAll(L1,L2,L3,L4,L5,L6,Gender,Height,Weight,save);
        VV2.getChildren().addAll(registerIdTextField,patientNameTextField,doctorNameTextField,nurseNameTextField,labtechNameTextField,radtechNameTextField);


        HBox HH= new HBox(10);
        HH.getChildren().addAll(VV1,VV2);
        HH.setAlignment(Pos.CENTER);

        VBox VMain2=new VBox(40);
        VMain2.getChildren().addAll(HH);
        VMain2.setAlignment(Pos.CENTER);



        HBox HMain =new HBox(100);
        HMain.getChildren().addAll(VMain,VMain2);
        HMain.setAlignment(Pos.CENTER);

        HMain.setBackground(backgroundObj);



        Scene scene = new Scene(HMain, 400, 250);
        patientfileStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        patientfileStage.setWidth(mediumWidth);
        patientfileStage.setHeight(mediumHeight);
        patientfileStage.show();

    }

    private static Image getImageByTitle(String title) {
        switch (title) {
            case "XRAY 1":
                return new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Xray.jpg");
            case "XRAY 2":
                return new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Xray1.jpg");
            case "XRAY 3":
                return new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Xray3.jpg");
            case "XRAY 4":
                return new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Xray4.jpg");
            default:
                return null;
        }
    }
}


