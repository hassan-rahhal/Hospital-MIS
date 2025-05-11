package com.example.finalproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.*;

public class PatientFileModule {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbc-connector";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private Connection conn;

    RadioButton rb1 = new RadioButton("A+");
    RadioButton rb2 = new RadioButton("A-");
    RadioButton rb3 = new RadioButton("B+");
    RadioButton rb4 = new RadioButton("B-");
    RadioButton rb5 = new RadioButton("O+");
    RadioButton rb6 = new RadioButton("O-");
    RadioButton rb7 = new RadioButton("AB+");
    RadioButton rb8 = new RadioButton("AB-");
    ToggleGroup bloodType = new ToggleGroup();

    RadioButton Rb1 = new RadioButton("Diabetes");
    RadioButton Rb2 = new RadioButton("Asthma");
    RadioButton Rb3 = new RadioButton("Heart Disease");
    RadioButton Rb4 = new RadioButton("Genetic Disorder");
    RadioButton Rb5 = new RadioButton("Cancer");

    TextField registerIdTextField = new TextField();
    TextField patientNameTextField = new TextField();
    TextField doctorNameTextField = new TextField();
    TextField nurseNameTextField = new TextField();
    TextField labtechNameTextField = new TextField();
    TextField radtechNameTextField = new TextField();
    ComboBox<String> height = new ComboBox<>();
    ComboBox<String> weight = new ComboBox<>();
    ComboBox<String> gender = new ComboBox<>();
    ComboBox<String> CB = new ComboBox<>();

    RadioButton RB1 = new RadioButton("Penicillin");
    RadioButton RB2 = new RadioButton("Sulfonamides");
    RadioButton RB3 = new RadioButton("Anticonvulsants");
    RadioButton RB4 = new RadioButton("Aspirin");
    RadioButton RB5 = new RadioButton("Ibuprofen");

    public void connect() {

            try {
                // Replace with your database connection details
                String url = "jdbc:mysql://localhost:3306/jdbc-connector";
                String username = "root";
                String password = "";
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    public boolean register(String regId, String patientName, String doctorName, String gender, String height, String weight,
                            String nurseName, String labtechName, String radtechName) {
        if (conn == null) {
            connect();  // Initialize the connection if it's null
        }

        // Validate input data
        if (regId.isEmpty() || patientName.isEmpty() || doctorName.isEmpty() || gender.isEmpty() || height.isEmpty() || weight.isEmpty()) {
            showError("All fields must be filled out!");
            return false;
        }

        // Check if height and weight are numeric
        try {
            Double.parseDouble(height);
            Double.parseDouble(weight);
        } catch (NumberFormatException e) {
            showError("Height and Weight must be valid numbers!");
            return false;
        }

        try {
            String sql = "INSERT INTO register (registerID, PatientName, DoctorName, gender, height, weight, NurseName, LabTechName, RadTechName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, regId);
            statement.setString(2, patientName);
            statement.setString(3, doctorName);
            statement.setString(4, gender);
            statement.setDouble(5, Double.parseDouble(height));
            statement.setDouble(6, Double.parseDouble(weight));
            statement.setString(7, nurseName);
            statement.setString(8, labtechName);
            statement.setString(9, radtechName);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }



    public void showPatientFileModule() {
        Stage patientFileStage = new Stage();
        patientFileStage.setTitle("Patient File Module");

        Image backgroundImage = new Image("file:C:/Users/hassa/OneDrive/Desktop/finalproject/whiteback.png");

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
        StackPane s = new StackPane();
        VBox V = new VBox(10);

        Text t = new Text("Allergies");
        Rectangle r = new Rectangle(600, 30);

        HBox H = new HBox(30);
        H.getChildren().addAll(RB1, RB2, RB3, RB4, RB5);
        H.setAlignment(Pos.CENTER_LEFT);
        H.setPadding(new Insets(0, 0, 0, 10));

        s.setAlignment(Pos.TOP_LEFT);
        s.getChildren().addAll(r, H);

        r.setFill(Color.web("#FFFFFF"));
        r.setStrokeWidth(1);
        r.setStroke(Color.BLACK);
        V.getChildren().addAll(t, s);
        V1.getChildren().add(V);

        Text t2 = new Text("Blood Type");
        Rectangle r2 = new Rectangle(600, 30);

        rb1.setToggleGroup(bloodType);
        rb2.setToggleGroup(bloodType);
        rb3.setToggleGroup(bloodType);
        rb4.setToggleGroup(bloodType);
        rb5.setToggleGroup(bloodType);
        rb6.setToggleGroup(bloodType);
        rb7.setToggleGroup(bloodType);
        rb8.setToggleGroup(bloodType);

        HBox H2 = new HBox(30);
        H2.getChildren().addAll(rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8);
        H2.setAlignment(Pos.CENTER_LEFT);
        H2.setPadding(new Insets(0, 0, 0, 10));

        StackPane s2 = new StackPane();
        s2.setAlignment(Pos.TOP_LEFT);
        s2.getChildren().addAll(r2, H2);
        r2.setFill(Color.web("#FFFFFF"));
        r2.setStrokeWidth(1);
        r2.setStroke(Color.BLACK);
        VBox V2 = new VBox(10);
        V2.getChildren().addAll(t2, s2);

        Rectangle r3 = new Rectangle(600, 30);
        Text t3 = new Text("Inherited Diseases");

        HBox H3 = new HBox(30);
        H3.getChildren().addAll(Rb1, Rb2, Rb3, Rb4, Rb5);
        H3.setAlignment(Pos.CENTER_LEFT);
        H3.setPadding(new Insets(0, 0, 0, 10));

        StackPane s3 = new StackPane();
        s3.setAlignment(Pos.TOP_LEFT);
        s3.getChildren().addAll(r3, H3);

        r3.setFill(Color.web("#FFFFFF"));
        r3.setStrokeWidth(1);
        r3.setStroke(Color.BLACK);
        VBox V3 = new VBox(10);
        V3.getChildren().addAll(t3, s3);

        Image I = new Image("file:C:/Users/hassa/OneDrive/Desktop/finalproject/Xray.jpg");
        Image I2 = new Image("file:C:/Users/hassa/OneDrive/Desktop/finalproject/Xray1.jpg");
        Image I3 = new Image("file:C:/Users/hassa/OneDrive/Desktop/finalproject/Xray3.jpg");
        Image I4 = new Image("file:C:/Users/hassa/OneDrive/Desktop/finalproject/Xray4.jpg");

        ImageView IV = new ImageView(I);
        IV.setFitWidth(40);
        IV.setFitHeight(40);

        ImageView IV2 = new ImageView(I2);
        IV2.setFitWidth(40);
        IV2.setFitHeight(40);

        ImageView IV3 = new ImageView(I3);
        IV3.setFitWidth(40);
        IV3.setFitHeight(40);

        ImageView IV4 = new ImageView(I4);
        IV4.setFitWidth(40);
        IV4.setFitHeight(40);

        String[] imageTitles = {"XRAY 1", "XRAY 2", "XRAY 3", "XRAY 4"};

        CB.setPromptText("Image Folder");
        CB.getItems().addAll(imageTitles);

        ImageView Imageview = new ImageView();
        Imageview.setFitWidth(200);
        Imageview.setFitHeight(200);

        CB.setOnAction(e -> {
            String selectedTitle = CB.getValue();
            Image selectedImage = getImageByTitle(selectedTitle);
            Imageview.setImage(selectedImage);
        });

        Text t4 = new Text("Doctor's Notes: ");
        TextArea T1 = new TextArea();
        T1.setMaxWidth(350);
        T1.setMaxHeight(200);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(T1);
        scroll.setMaxWidth(200);

        VMain.setAlignment(Pos.TOP_LEFT);
        VMain.setPadding(new Insets(30, 30, 30, 30));
        VMain.getChildren().addAll(V1, V2, V3, T1, scroll, CB, Imageview);

        Label L1 = new Label("Register ID:");
        Label L2 = new Label("Patient's Full Name");
        Label L3 = new Label("Doctor's Full Name");
        Label L4 = new Label("Nurse's Full Name");
        Label L5 = new Label("Laboratory Technician's Full Name");
        Label L6 = new Label("Radiology Technician's Full Name");

        for (int i = 1; i <= 250; i++) {
            height.getItems().add(String.valueOf(i));
        }
        height.setValue("Height");
        for (int i = 1; i <= 250; i++) {
            weight.getItems().add(String.valueOf(i));
        }
        weight.setValue("Weight");
        gender.getItems().addAll("Female", "Male");
        gender.setValue("Gender");

        Button save = new Button("Save Data");

        save.setOnAction(e -> handleSaveAction(patientFileStage));

        VBox VV1 = new VBox(40);
        VBox VV2 = new VBox(30);

        VV1.getChildren().addAll(L1, L2, L3, L4, L5, L6);
        VV2.getChildren().addAll(registerIdTextField, patientNameTextField, doctorNameTextField, nurseNameTextField, labtechNameTextField, radtechNameTextField);

        HBox H2_ = new HBox(10);
        H2_.getChildren().addAll(VV1, VV2);

        HBox H4 = new HBox(10);
        H4.getChildren().addAll(gender, height, weight);
        H4.setAlignment(Pos.CENTER_LEFT);

        VBox VMain1 = new VBox(30);
        VMain1.getChildren().addAll(H2_, H4, save);
        VMain1.setPadding(new Insets(20, 20, 20, 20));

        HBox H1 = new HBox(10);
        H1.getChildren().addAll(VMain1, VMain);
        H1.setAlignment(Pos.CENTER);
        H1.setBackground(backgroundObj);

        Scene scene = new Scene(H1, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        patientFileStage.setScene(scene);
        patientFileStage.show();
    }

    private Image getImageByTitle(String title) {
        switch (title) {
            case "XRAY 1":
                return new Image("file:C:/Users/hassa/OneDrive/Desktop/finalproject/Xray.jpg");
            case "XRAY 2":
                return new Image("file:C:/Users/hassa/OneDrive/Desktop/finalproject/Xray1.jpg");
            case "XRAY 3":
                return new Image("file:C:/Users/hassa/OneDrive/Desktop/finalproject/Xray3.jpg");
            case "XRAY 4":
                return new Image("file:C:/Users/hassa/OneDrive/Desktop/finalproject/Xray4.jpg");
            default:
                return null;
        }
    }

    private void handleSaveAction(Stage stage) {
        if (register(registerIdTextField.getText(), patientNameTextField.getText(), doctorNameTextField.getText(),
                gender.getValue(), height.getValue(), weight.getValue(),
                nurseNameTextField.getText(), labtechNameTextField.getText(), radtechNameTextField.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Saved");
            alert.setHeaderText("Data has been saved successfully.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to save data.");
            alert.showAndWait();
        }
    }
}
