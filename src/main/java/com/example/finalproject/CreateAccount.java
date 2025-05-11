package com.example.finalproject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;


public class CreateAccount {

    private TableView<Account> table = new TableView<Account>();
    private final ObservableList<Account> data = FXCollections.observableArrayList();
    private double width;
    private double height;
    String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc-connector";
    String username = "root";
    String password = "";
    Connection conn ;
    TextField TF1=new TextField();
    TextField TF2=new TextField();
    TextField TF3=new TextField();
    TextField TF4=new TextField();
    TextField TF5=new TextField();
    TextField TF6=new TextField();
    TextField TF7=new TextField();
    TextField TF8=new TextField();

    ToggleGroup Gender=new ToggleGroup();
        RadioButton RB1=new RadioButton("Male");
        RadioButton RB2=new RadioButton("Female");
        RadioButton RB3=new RadioButton("Prefer not to say");
    public static boolean isValidEmail(String email) {
        String regex = "^(.+)@(gmail\\.com|hotmail\\.com)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean containsonlydigit(String phone_number) {
        for(int i=0;i<phone_number.length();i++) {
            char ch=phone_number.charAt(i);
            if(!Character.isDigit(ch))
                return false;
        }
        return true;
    }
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }



    public void Connect() {
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public boolean Register(String firstname,  String lastname,String email,String address,String gender, String country ,String phonenumber,String password) {
        boolean output = false;

        try {
            String query = "INSERT INTO user (firstname, lastname, email,address,gender,country,phonenumber,password) VALUES (?, ?, ?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement2 = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement2.setString(1, firstname);
                preparedStatement2.setString(2, lastname);
                preparedStatement2.setString(3,email);
                preparedStatement2.setString(4,address );
                preparedStatement2.setString(5, gender);
                preparedStatement2.setString(6, country );
                preparedStatement2.setString(7,phonenumber );
                preparedStatement2.setString(8, hashPassword(password));
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
    public CreateAccount(double width, double height) {
        this.width = width;
        this.height = height;
    }
    public void loadDataFromDatabase() {
        Connect();
        try {
            String query = "SELECT * FROM user";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String gender = rs.getString("gender");
                String country = rs.getString("country");
                String phoneNumber = rs.getString("phonenumber");
                String password = rs.getString("password");

                data.add(new Account(firstName, lastName, email, address, gender, country, phoneNumber, hashPassword(password)));
            }

            table.setItems(data);

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error fetching data from database: " + e.getMessage());
        }
    }

    public void start(Stage createAccountStage) {

        loadDataFromDatabase();
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
        Rectangle R1 = new Rectangle(700, 670);
        R1.setFill(Color.WHITE);

        Label L1=new Label("Create An Account");
        L1.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.REGULAR,30));
        L1.setTextFill(Color.web("#08605F"));


        String TextFieldStyle =
                "-fx-background-color: #F5F5F5;" +
                        "-fx-font-family: 'Times New Roman';" +
                        "-fx-font-size: 16px;" +
                        "-fx-text-fill: #333333;" +
                        "-fx-prompt-text-fill: #999999;" +
                        "-fx-border-color: #CCCCCC;" +
                        "-fx-border-radius: 6px;" +
                        "-fx-padding: 6px 10px;" +         // Comfortable padding
                        "-fx-max-width: 250px;"             // Slightly wider for user comfort
                ;

        TF1.setStyle(TextFieldStyle);
        TF2.setStyle(TextFieldStyle);
        TF3.setStyle(TextFieldStyle);
        TF4.setStyle(TextFieldStyle);
        TF8.setStyle(TextFieldStyle);


        TF5.setStyle(
                "-fx-background-color: #F5F5F5;" +
                        "-fx-font-family: 'Times New Roman';" +
                        "-fx-font-size: 16px;" +
                        "-fx-text-fill: #333333;" +
                        "-fx-prompt-text-fill: #999999;" +
                        "-fx-border-color: #CCCCCC;" +
                        "-fx-border-radius: 6px;" +
                        "-fx-padding: 6px 10px;" +
                "-fx-max-width: 150px;"
        );

        TF6.setStyle( "-fx-background-color: #F5F5F5;" +
        "-fx-font-family: 'Times New Roman';" +
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #333333;" +
                "-fx-prompt-text-fill: #999999;" +
                "-fx-border-color: #CCCCCC;" +
                "-fx-border-radius: 6px;" +
                "-fx-padding: 6px 10px;" +
                "-fx-max-width: 70px;"
        );
        TF7.setStyle( "-fx-background-color: #F5F5F5;" +
                        "-fx-font-family: 'Times New Roman';" +
                        "-fx-font-size: 16px;" +
                        "-fx-text-fill: #333333;" +
                        "-fx-prompt-text-fill: #999999;" +
                        "-fx-border-color: #CCCCCC;" +
                        "-fx-border-radius: 6px;" +
                        "-fx-padding: 6px 10px;" +
                "-fx-max-width: 150px;"
        );


        TF1.setEditable(true);
        TF2.setEditable(true);
        TF3.setEditable(true);
        TF4.setEditable(true);
        TF5.setEditable(false);
        TF6.setEditable(false);
        TF7.setEditable(true);
        TF8.setEditable(true);

        TF1.setPromptText("First Name");
        TF2.setPromptText("Last Name");
        TF3.setPromptText("Email Address");
        TF4.setPromptText("Address");
        TF5.setPromptText("Country");
        TF6.setPromptText("Code");
        TF7.setPromptText("Phone");
        TF8.setPromptText("Password");


        ComboBox<String> CB=new ComboBox<>();
        CB.setValue("Country");
        CB.getItems().addAll("Iraq","Syria","Lebanon","UAE","Jordan");
        CB.setPrefWidth(50);


        ComboBox<String> CB2=new ComboBox<>();
        CB2.setValue("Country");
        CB2.getItems().addAll("Iraq","Syria","Lebanon","UAE","Jordan");
        CB2.setPrefWidth(50);

        Map<String, String> countryCodes = new HashMap<>();
        countryCodes.put("Iraq", "+964");
        countryCodes.put("Syria", "+963");
        countryCodes.put("Lebanon", "+961");
        countryCodes.put("UAE", "+971");
        countryCodes.put("Jordan", "+962");

        CB.setOnAction(event -> {
            TF5.setText(CB.getValue());
            TF5.setStyle(
                    "-fx-font-family: 'Times New Roman'; " +
                            "-fx-font-size: 12px; " +
                            "-fx-text-fill: BLACK; " +
                            "-fx-max-width: 150px;"

            );
            String selectedCountry = CB.getValue();
            String countryCode = countryCodes.get(selectedCountry);
            TF6.setText(countryCode);
            TF6.setStyle(
                    "-fx-font-family: 'Times New Roman'; " +
                            "-fx-font-size: 12px; " +
                            "-fx-text-fill: BLACK; " +
                            "-fx-max-width: 50px;"
            );
        });



        String ButtonStyle =
                "-fx-background-color: #08605F; " +
                        "-fx-text-fill: WHITE; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 8px 16px; " +
                        "-fx-background-radius: 5px;"+
                        "-fx-min-width: 50px; ";

        Button B=new Button("Exit");
B.setOnAction(e->{createAccountStage.close();});
        Button B2=new Button("Sign in");

        B.setStyle(ButtonStyle);
        B2.setStyle(ButtonStyle);

        RB1.setToggleGroup(Gender);
        RB2.setToggleGroup(Gender);
        RB3.setToggleGroup(Gender);

        HBox H2=new HBox(15);
        H2.getChildren().addAll(RB1,RB2,RB3);
        H2.setAlignment(Pos.CENTER);
        B2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    String selectedGender = "undefined";
                    if (Gender.getSelectedToggle() != null) {
                        selectedGender = ((RadioButton) Gender.getSelectedToggle()).getText();
                    }
                    if (TF1.getText().length() != 0 && TF2.getText().length() != 0 && TF3.getText().length() != 0 && TF4.getText().length()!=0 && TF5.getText().length()!=0 && TF7.getText().length()!=0 && TF8.getText().length()!=0 && selectedGender.length()!=0) {
                        if(containsonlydigit(TF7.getText()) && TF7.getText().length()==8) {
                            if (isValidEmail(TF3.getText())) {
                                Connect();
                                boolean registrationSuccessful = Register(
                                        TF1.getText(),
                                        TF2.getText(),
                                        TF3.getText(),
                                        TF4.getText(),
                                        selectedGender,
                                        TF5.getText(),
                                        TF7.getText(),
                                        TF8.getText()
                                );

                                if (registrationSuccessful) {
                                    System.out.println("Registration successful.");
                                    data.add(new Account(
                                            TF1.getText(),
                                            TF2.getText(),
                                            TF3.getText(), TF4.getText(), selectedGender, TF5.getText(), TF7.getText(), TF8.getText()));
                                    table.setItems(null);
                                    table.setItems(data);
                                    TF1.clear();
                                    TF2.clear();
                                    TF3.clear();
                                    TF4.clear();
                                    TF5.clear();
                                    TF6.clear();
                                    TF7.clear();
                                    TF8.clear();
                                    Gender.selectToggle(null);
                                } else {
                                    System.out.println("Registration failed.");
                                }
                            }
                            else{
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("ADD account Failed");
                                alert.setHeaderText(null);
                                alert.setContentText("Invalid Email!.");
                                alert.showAndWait();
                            }
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("ADD account Failed");
                            alert.setHeaderText(null);
                            alert.setContentText("Phone number should contain only 8 digits!.");
                            alert.showAndWait();
                        }

                    }else{
                        System.out.println("Please fill in all fields and select a gender.");
                    }
                }
        });

        HBox H1=new HBox(30);
        H1.getChildren().addAll(B, B2);
        H1.setAlignment(Pos.CENTER);

        HBox H3=new HBox(1);
        H3.getChildren().addAll(TF5,CB);
        H3.setAlignment(Pos.CENTER);

        HBox H4=new HBox(1);
        H4.getChildren().addAll(TF6,TF7);
        H4.setAlignment(Pos.CENTER);
        table.setEditable(true);
        TableColumn<Account, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setMinWidth(66);
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());

        TableColumn<Account, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(66);
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        TableColumn<Account, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(90);
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        TableColumn<Account, String> addressCol = new TableColumn<>("Address");
        addressCol.setMinWidth(66);
        addressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

        TableColumn<Account, String> GenderCol = new TableColumn<>("Gender: ");
        GenderCol.setMinWidth(66);
        GenderCol.setCellValueFactory(cellData -> cellData.getValue().genderProperty());

        TableColumn<Account, String> countryCol = new TableColumn<>("Country");
        countryCol.setMinWidth(90);
        countryCol.setCellValueFactory(cellData -> cellData.getValue().countryProperty());

        TableColumn<Account, String> phonenumberCol = new TableColumn<>("Phone Number");
        phonenumberCol.setMinWidth(90);
        phonenumberCol.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());

        TableColumn<Account, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setMinWidth(66);
        passwordCol.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol,emailCol,addressCol,GenderCol,countryCol,phonenumberCol,passwordCol);
        table.setMaxWidth(630);
        table.setMaxHeight(140);


        VBox V1=new VBox(20);
        V1.getChildren().addAll(L1,TF1,TF2,TF3,TF4,H2,H3,H4,TF8,H1,table);
        V1.setPadding(new Insets(15,15,15,15));
        V1.setAlignment(Pos.TOP_CENTER);


        StackPane S2=new StackPane();
        S2.getChildren().addAll(R1,V1);

        ///////////////////////////////////////

        Rectangle R2 = new Rectangle(600, 670);
        R2.setFill(Color.web("#08605F"));

        Label L6=new Label("     GREEN CARE\nMEDICAL CENTER");
        L6.setAlignment(Pos.CENTER);
        L6.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.REGULAR,30));
        L6.setTextFill(Color.WHITE);

        Label L7=new Label("Your Health Our Priority");
        L7.setFont(Font.font("Times New Roman",FontWeight.NORMAL,FontPosture.REGULAR,20));
        L7.setTextFill(Color.WHITE);

        StackPane S5 = new StackPane();
        S5.getChildren().addAll(L7);
        S5.setAlignment(Pos.BOTTOM_CENTER);
        S5.setPadding(new Insets(0,0,115,0));

        StackPane S3=new StackPane();
        S3.getChildren().addAll(R2,L6,S5);

        ///////////////////////////////////////
        HBox H=new HBox(0);
        H.getChildren().addAll(S3,S2);
        H.setAlignment(Pos.CENTER);



        StackPane S4 = new StackPane();
        S4.getChildren().addAll(S1,H);




        Scene scene = new Scene(S4,width,height);
        createAccountStage.setTitle("Create Account");
        createAccountStage.setScene(scene);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double mediumWidth = screenWidth * 0.9; // 60% of screen width
        double mediumHeight = screenHeight * 0.9; // 60% of screen height
        createAccountStage.setWidth(mediumWidth);
        createAccountStage.setHeight(mediumHeight);
        createAccountStage.show();
    }
    class Account {
            private SimpleStringProperty firstName;
            private SimpleStringProperty lastName;
            private SimpleStringProperty address;
            private SimpleStringProperty email;
            private SimpleStringProperty gender;
            private SimpleStringProperty country;
            private SimpleStringProperty phoneNumber;
            private SimpleStringProperty password;

            public Account(String fisrtName, String lastName, String email, String address, String gender, String country, String phoneNumber, String password) {
                this.firstName = new SimpleStringProperty(fisrtName);
                this.lastName = new SimpleStringProperty(lastName);
                this.email = new SimpleStringProperty(email);
                this.address = new SimpleStringProperty(address);
                this.gender = new SimpleStringProperty(gender);
                this.country = new SimpleStringProperty(country);
                this.phoneNumber = new SimpleStringProperty(phoneNumber);
                this.password = new SimpleStringProperty(password);
            }
        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String firstName) {
            this.firstName.set(firstName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String lastName) {
            this.lastName.set(lastName);
        }

        public String getAddress() {
            return address.get();
        }

        public void setAddress(String address) {
            this.address.set(address);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String email) {
            this.email.set(email);
        }

        public String getGender() {
            return gender.get();
        }

        public void setGender(String gender) {
            this.gender.set(gender);
        }

        public String getCountry() {
            return country.get();
        }

        public void setCountry(String country) {
            this.country.set(country);
        }

        public String getPhoneNumber() {
            return phoneNumber.get();
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber.set(phoneNumber);
        }

        public String getPassword() {
            return password.get();
        }

        public void setPassword(String password) {
            this.password.set(password);
        }
        public SimpleStringProperty firstNameProperty() {
            return firstName;
        }
        public SimpleStringProperty lastNameProperty() {
            return lastName;
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }

        public SimpleStringProperty addressProperty() {
            return address;
        }

        public SimpleStringProperty genderProperty() {
            return gender;
        }

        public SimpleStringProperty countryProperty() {
            return country;
        }

        public SimpleStringProperty phoneNumberProperty() {
            return phoneNumber;
        }

        public SimpleStringProperty passwordProperty() {
            return password;
        }
    }


        }

