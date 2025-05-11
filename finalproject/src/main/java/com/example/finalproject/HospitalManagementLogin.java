package com.example.finalproject;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.control.CheckBox;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class HospitalManagementLogin extends Application {
    private static  String VALID_USERNAME ;
    private static  String VALID_PASSWORD ;
    TextField TF1 = new TextField();
    TextField TF2 = new TextField();
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

    public static void main(String[] args) {
        launch(args);
    }


    @Override

    public void start(Stage primaryStage) {
        Image backgroundImage = new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\download.jpg");

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        Background backgroundObj = new Background(background);

        StackPane S1 = new StackPane();
        S1.setAlignment(Pos.CENTER);
        S1.setBackground(backgroundObj);


        ///////////////////////////////////////
        Rectangle R1 = new Rectangle(400, 600);
        R1.setFill(Color.WHITE);

        Label L1 = new Label("Log In");
        L1.setFont(Font.font("Ariel", FontWeight.NORMAL, FontPosture.REGULAR, 30));
        L1.setTextFill(Color.LIGHTSKYBLUE);


        Label L2 = new Label("Don't have an account ?");
        L2.setFont(Font.font("Ariel", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        L2.setTextFill(Color.GREY);

        Hyperlink HL = new Hyperlink("Create an account");
        HL.setFont(Font.font("Ariel", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        HL.setTextFill(Color.BLUE);
        HL.setStyle("-fx-border-color: transparent; -fx-underline: true;");


        HL.setOnAction(e -> {
            CreateAccount createAccount = new CreateAccount(primaryStage.getWidth(), primaryStage.getHeight());
            Stage createAccountStage = new Stage();
            createAccount.start(createAccountStage);
            // You can also close the current stage if needed
        });




        TF1.setPromptText("email");
        TF2.setPromptText("Password");


        TF1.setStyle(
                "-fx-font-family: 'Arial'; " +
                        "-fx-font-size: 12px; " +
                        "-fx-text-fill: BLACK;"
        );
        TF2.setStyle(
                "-fx-font-family: 'Arial'; " +
                        "-fx-font-size: 12px; " +
                        "-fx-text-fill: BLACK;"
        );
        TF1.setEditable(true);
        TF2.setEditable(true);
        TF1.setMaxWidth(200);
        TF2.setMaxWidth(200);


        Button B = new Button("Sign in");
        B.setTextFill(Color.WHITE);

        B.setStyle(
                "-fx-background-color: LIGHTSKYBLUE; " +
                        "-fx-text-fill: WHITE; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 8px 16px; " +
                        "-fx-background-radius: 5px;" +
                        "-fx-min-width: 200px; "
        );
        B.setOnAction(e -> handleLogin(primaryStage, TF1.getText(), TF2.getText()));

        CheckBox CH = new CheckBox("Remember password");
        CH.setFont(Font.font("Ariel", FontWeight.NORMAL, FontPosture.REGULAR, 13));


        HBox H1 = new HBox(30);
        H1.getChildren().addAll(B, CH);
        H1.setAlignment(Pos.CENTER);

        VBox V1 = new VBox(20);
        V1.getChildren().addAll(L1, L2, HL, TF1, TF2, CH, B);
        V1.setPadding(new Insets(15, 15, 15, 15));
        V1.setAlignment(Pos.CENTER);


        StackPane S2 = new StackPane();
        S2.getChildren().addAll(R1, V1);

        ///////////////////////////////////////

        Rectangle R2 = new Rectangle(600, 600);
        R2.setFill(Color.LIGHTSKYBLUE);

        Image I = new Image("C:\\Users\\Ghassan\\IdeaProjects\\finalproject\\Capture-removebg-preview.png");

        ImageView IV = new ImageView(I);
        IV.setFitWidth(450);
        IV.setFitHeight(450);

        Label L6 = new Label("                        MEDCO\nMEDICAL       CENTER");
        L6.setAlignment(Pos.CENTER);
        L6.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
        L6.setTextFill(Color.WHITE);

        Label L7 = new Label("     Healing Starts Here\nYour Partner in Wellness");
        L7.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        L7.setTextFill(Color.WHITE);

        StackPane S5 = new StackPane();
        S5.getChildren().addAll(L7);
        S5.setAlignment(Pos.BOTTOM_CENTER);
        S5.setPadding(new Insets(0, 0, 115, 0));

        StackPane S3 = new StackPane();
        S3.getChildren().addAll(R2, IV, L6, S5);

        ///////////////////////////////////////
        HBox H = new HBox(0);
        H.getChildren().addAll(S3, S2);
        H.setAlignment(Pos.CENTER);


        StackPane S4 = new StackPane();
        S4.getChildren().addAll(S1, H);


        Scene scene = new Scene(S4);
        primaryStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        primaryStage.setWidth(mediumWidth);
        primaryStage.setHeight(mediumHeight);
        primaryStage.show();
    }

    private void handleLogin(Stage primaryStage, String username, String password) {
        try {
            validateCredentials(username, password);
            MenuModule m=new MenuModule();
            m.showMenu(primaryStage);
            saveUserAction(username, "Login");
            VALID_USERNAME=username;
            // Dashboard.showDashboard(primaryStage);
        } catch (InvalidCredentialsException e) {
            showAlert("Invalid Credentials", "Please enter valid email and password.");
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred.");
            e.printStackTrace();
        }
    }
    public String getValidUsername(){
        return VALID_USERNAME;
    }
    private void saveUserAction(String username, String action) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("user_actions.csv", true))) {
            LocalDateTime timestamp = LocalDateTime.now();
            writer.println(username + "," + action + "," + timestamp);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file writing error
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void validateCredentials(String username, String password) throws InvalidCredentialsException {
        Connect();
       String sql="SELECT email,password FROM user WHERE email=? AND password=?";
       try(PreparedStatement statement=conn.prepareStatement(sql)){
           statement.setString(1,TF1.getText());
           statement.setString(2,TF2.getText());
           try(ResultSet resultSet=statement.executeQuery()){
              if(resultSet.next()){
                  System.out.println("valid");
              }
              else throw new InvalidCredentialsException("INVALID");
           }
       }
       catch (SQLException e){
           e.printStackTrace();
       }
    }

    private static class InvalidCredentialsException extends Exception {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
