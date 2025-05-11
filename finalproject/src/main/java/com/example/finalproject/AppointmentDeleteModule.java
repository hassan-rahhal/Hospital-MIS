package com.example.finalproject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class AppointmentDeleteModule {
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
    public void deleteAppointment(String id,Date appdate , String apptime) {
        try {
            String query = "DELETE FROM appointment WHERE appId=? AND appDate = ? AND apptime = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(appdate);
            preparedStatement.setString(2, formattedDate);
            preparedStatement.setString(3, apptime);
            int deletedRows = preparedStatement.executeUpdate();

            if (deletedRows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Deleted");
                alert.setHeaderText(null);
                alert.setContentText("Appointment deleted successfully.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete Failed");
                alert.setHeaderText(null);
                alert.setContentText("No matching appointment found to delete.");
                alert.showAndWait();
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        // Create a new stage for the Patient module
        Stage appointmentStage = new Stage();
        appointmentStage.setTitle("Appointment Module");

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
        gridPane.add(new Label("Appointment ID:"),0,0);
        TextField idTextField=new TextField();
        gridPane.add(idTextField,1,0);

        gridPane.add(new Label("Appointment Date:"), 0, 1);
        TextField appDateTextField = new TextField();
        appDateTextField.setPromptText("dd/MM/YYYY");
        gridPane.add(appDateTextField, 1, 1);

        gridPane.add(new Label("Appointment Time:"), 0, 2);
        TextField appTimeTextField = new TextField();
        gridPane.add(appTimeTextField, 1, 2);

        Button deleteButton = new Button("delete");
        Button exitButton=new Button("exit");
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(deleteButton,exitButton);
        gridPane.add(hBox, 1, 3);
        gridPane.setAlignment(Pos.CENTER);
        exitButton.setOnAction(e->{appointmentStage.close();});
        HospitalManagementLogin h=new HospitalManagementLogin();
        MenuModule m=new MenuModule();
        deleteButton.setOnAction(event -> {
            String id=idTextField.getText();
            String appDate = appDateTextField.getText();
            String appTime = appTimeTextField.getText();
            Date date = parseStringToDate(appDateTextField.getText());
            if (!id.isEmpty() && !appDate.isEmpty() && !appTime.isEmpty()) {
                AppointmentDeleteModule appointmentDeleteModule = new AppointmentDeleteModule();
                appointmentDeleteModule.Connect();
                appointmentDeleteModule.deleteAppointment(id,date, appTime);
                m.concatenateRows("user_actions.csv",h.getValidUsername(),"Delete an appointment");
                idTextField.clear();
                appDateTextField.clear();
                appTimeTextField.clear();
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
        appointmentStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        appointmentStage.setWidth(mediumWidth);
        appointmentStage.setHeight(mediumHeight);
        appointmentStage.show();
    }
}