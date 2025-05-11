package com.example.finalproject;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HospitalManagementLogin extends Application {
    private static  String VALID_USERNAME;
    TextField TF1 = new TextField();
    PasswordField TF2 = new PasswordField();
    String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc-connector";
    String username = "root";
    String password = "";
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
        Image backgroundImage = new Image("C:\\Users\\hassa\\OneDrive\\Desktop\\finalproject\\download2.png");

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
        L1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
        L1.setTextFill(Color.web("#08605F"));


        Label L2 = new Label("Don't have an account ?");
        L2.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        L2.setTextFill(Color.GREY);

        Hyperlink HL = new Hyperlink("Create an account");
        HL.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        HL.setTextFill(Color.BLUE);
        HL.setStyle("-fx-border-color: transparent; -fx-underline: true;");


        HL.setOnAction(e -> {
            CreateAccount createAccount = new CreateAccount(primaryStage.getWidth(), primaryStage.getHeight());
            Stage createAccountStage = new Stage();
            createAccount.start(createAccountStage);
        });




        TF1.setPromptText("Email");
        TF2.setPromptText("Password");
        TF1.setStyle(
                "-fx-background-color: #F5F5F5;" +
                        "-fx-font-family: 'Times New Roman';" +
                        "-fx-font-size: 16px;" +
                        "-fx-text-fill: #333333;" +
                        "-fx-prompt-text-fill: #999999;" +
                        "-fx-border-color: #CCCCCC;" +
                        "-fx-border-radius: 6px;" +
                        "-fx-padding: 6px 10px;" +
                        "-fx-max-width: 250px;"

        );

        TF2.setStyle(
                "-fx-background-color: #F5F5F5;" +
                        "-fx-font-family: 'Times New Roman';" +
                        "-fx-font-size: 16px;" +
                        "-fx-text-fill: #333333;" +
                        "-fx-prompt-text-fill: #999999;" +
                        "-fx-border-color: #CCCCCC;" +
                        "-fx-border-radius: 6px;" +
                        "-fx-padding: 6px 10px;" +
                        "-fx-max-width: 250px;"

        );


        TF1.setEditable(true);
        TF2.setEditable(true);
        TF1.setMaxWidth(200);
        TF2.setMaxWidth(200);


        Button B = new Button("Sign in");
        B.setTextFill(Color.GREEN);

        B.setStyle(
                "-fx-background-color: #08605F; " +
                        "-fx-text-fill: WHITE; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 8px 16px; " +
                        "-fx-background-radius: 5px;" +
                        "-fx-min-width: 200px; "
        );
        B.setOnAction(e -> handleLogin(primaryStage, TF1.getText(), TF2.getText()));

        TF1.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                handleLogin(primaryStage, TF1.getText(), TF2.getText());
            }
        });

        TF2.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                handleLogin(primaryStage, TF1.getText(), TF2.getText());
            }
        });


        HBox H1 = new HBox(30);
        H1.getChildren().addAll(B);
        H1.setAlignment(Pos.CENTER);

        VBox V1 = new VBox(20);
        V1.getChildren().addAll(L1, L2, HL, TF1, TF2, B);
        V1.setPadding(new Insets(15, 15, 15, 15));
        V1.setAlignment(Pos.CENTER);


        StackPane S2 = new StackPane();
        S2.getChildren().addAll(R1, V1);


        Rectangle R2 = new Rectangle(600, 600);
        R2.setFill(Color.web("#08605F"));

        Label L6 = new Label("     GREEN CARE\nMEDICAL CENTER");
        L6.setAlignment(Pos.CENTER);
        L6.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
        L6.setTextFill(Color.WHITE);

        Label L7 = new Label("Your Health Our Priority");
        L7.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        L7.setTextFill(Color.WHITE);

        StackPane S5 = new StackPane();
        S5.getChildren().addAll(L7);
        S5.setAlignment(Pos.BOTTOM_CENTER);
        S5.setPadding(new Insets(0, 0, 115, 0));

        StackPane S3 = new StackPane();
        S3.getChildren().addAll(R2, L6, S5);

        HBox H = new HBox(0);
        H.getChildren().addAll(S3, S2);
        H.setAlignment(Pos.CENTER);


        StackPane S4 = new StackPane();
        S4.getChildren().addAll(S1, H);

        Scene scene = new Scene(S4);
        primaryStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9;
        double mediumHeight = screenHeight * 0.9;
        primaryStage.setWidth(mediumWidth);
        primaryStage.setHeight(mediumHeight);
        primaryStage.setTitle("Hospital MIS ");
        primaryStage.show();
    }

    private void handleLogin(Stage primaryStage, String username, String password) {
        try {
            validateCredentials(username, password); // now does proper hash check
            MenuModule m = new MenuModule();
            m.showMenu(primaryStage);
            saveUserAction(username, "Login");
            VALID_USERNAME = username;
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
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void validateCredentials(String email, String enteredPassword) throws InvalidCredentialsException {
        Connect(); // make sure you connect before this
        String sql = "SELECT password FROM user WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");
                    if (CreateAccount.verifyPassword(enteredPassword, hashedPassword)) {
                        System.out.println("Login successful.");
                    } else {
                        throw new InvalidCredentialsException("Invalid password.");
                    }
                } else {
                    throw new InvalidCredentialsException("Invalid email.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidCredentialsException("Database error.");
        }
    }

    private static class InvalidCredentialsException extends Exception {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
