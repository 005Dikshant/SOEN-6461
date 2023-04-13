package com.example.sdm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AfterLogin {

    @FXML
    TextField username;
    @FXML TextField userId;
    @FXML TextField userAge;
    @FXML TextField userEmail;
    @FXML
    ComboBox<String> idComboBox;
    @FXML
    ComboBox<String> ticketsComboBox;
    @FXML
    RadioButton oneWayType;
    @FXML RadioButton twoWayType;
    @FXML Button payButton;
    @FXML
    private Label wrongLabel;



    public int ticketUserAge = 0;
    public double ticketUserAmount = 0.0;
    public String ticketUserName = "";
    public String ticketUserType = "";
    public int numOfTickets = 0;

    private boolean checkTravelType = false;

    public ArrayList<customer> customerContainer = new ArrayList<>();
    ToggleGroup toggleGroup;

    @FXML TableView<customer> ticketTable;
    @FXML TableColumn<customer, String> columnName;
    @FXML TableColumn<customer, String> columnAge;
    @FXML TableColumn<customer, String> columnAmount;
    @FXML TableColumn<customer, String> columnEndDate;
    @FXML TableColumn<customer, String> columnType;

    @FXML private Label welcomeMessageText;
    @FXML private Label currentBalanceText;

    @FXML private Label responseMessageText;

    @FXML private TextField addMoneyText;

    public double currentUserBalance = 0.99;

    DecimalFormat decimalFormat = new DecimalFormat("#.##");


    //HomeController homeController = new HomeController();

    public void initialize() {
        occupancySelectionType();
        welcomeMessageText.setText("Welcome, " + (HomeController.tempUserName).toUpperCase());
        currentBalanceText.setText(decimalFormat.format(currentUserBalance) + " $");
    }

    private void occupancySelectionType() {
        toggleGroup = new ToggleGroup();
        twoWayType.setToggleGroup(toggleGroup);
        twoWayType.setUserData("twoWay");
        oneWayType.setToggleGroup(toggleGroup);
        oneWayType.setUserData("oneWay");

        addChangeListener();
    }

    private void addChangeListener() {
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            String selection = (String) newToggle.getUserData();
            if (selection.equals("twoWay")) {
                checkTravelType = true;
            } else {
                checkTravelType = false;
            }
        });
    }

    public void userLogOut(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Home.fxml");
    }

    public boolean doValidations(){

        if(userId.getText().toString().isEmpty()){
            wrongLabel.setText("Empty User Id");
            return true;
        }else if(username.getText().toString().isEmpty()){
            wrongLabel.setText("Empty UserName");
            return true;
        }else if(userEmail.getText().toString().isEmpty()){
            wrongLabel.setText("Empty Email");
            return true;
        }else if(userAge.getText().toString().isEmpty()){
            wrongLabel.setText("Age cannot be empty");
        }
        String stringAge = userAge.getText().toString();
        for(char ch : stringAge.toCharArray()){
            if(ch > '9' || ch < '0'){
                wrongLabel.setText("Invalid Age");
                return true;
            }
        }


        int intAge = Integer.parseInt(stringAge);

        if(intAge < 2 || intAge > 60){
            wrongLabel.setText("Age must be between 2 and 60");
            return true;
        }

        String tickets = ticketsComboBox.getValue();
        if(tickets == null){
            wrongLabel.setText("Please select the number of tickets");
            return true;
        }
        numOfTickets = Integer.parseInt(tickets);
        ticketUserAge = intAge;
        return false;

    }

    public double doTransaction(){
        double payableAmount = 0.0;

        if(ticketUserAge >= 2 && ticketUserAge <= 15){
            payableAmount = numOfTickets * 2.67;
        }else if(ticketUserAge >= 16 && ticketUserAge <= 45){
            payableAmount = numOfTickets * 4.78;
        }else {
            payableAmount = numOfTickets * 3.14;
        }

        if(checkTravelType == true){
            payableAmount = 1.15 * payableAmount;
        }
        return payableAmount;
    }

    public Date getEndDate(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_YEAR,1);
        Date nextDate = calendar.getTime();
        return nextDate;
    }

    public void loadData(double amount){
        ticketUserName = username.getText().toString();
        ticketUserAmount = amount;
        ticketUserType = (checkTravelType == true ? " 2 Way" : " 1 Way");
        Date ticketEndDate = getEndDate();

        customer customer = new customer(ticketUserName,ticketUserType,ticketUserAge,ticketUserAmount,ticketEndDate);
        customerContainer.add(customer);

        ObservableList<customer> data = FXCollections.observableArrayList(customerContainer);
        ticketTable.setItems(data);

        columnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        columnAmount.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getAmount())));
        columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketType()));
        columnEndDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        columnAge.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getAge())));

    }

    public void createDialog(double amount){

        String message;
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if(amount  != -1){

            message = "You have been charged " + decimalFormat.format(amount) + "$ for " + (checkTravelType == true ? " 2 Way" : " 1 Way");

            alert.setTitle("Success Dialog");
            alert.setHeaderText(message);
            alert.setContentText("Thanks for choosing us.");

            Image tickImage = new Image(getClass().getResourceAsStream("tick.png"));
            ImageView tickMark = new ImageView(tickImage);
            tickMark.setFitHeight(30);
            tickMark.setFitWidth(30);
            alert.setGraphic(tickMark);


        }else{
            message = "Not sufficient balance, please recharge the account.";
            alert.setTitle("Error Dialog");
            alert.setHeaderText(message);
            alert.setContentText("Contact Us, for further assistance, email at iGoHelpPayment@Stm");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // user clicked OK
        } else {
            // user clicked Cancel or closed the dialog
        }

    }

    public void userPay(ActionEvent event) throws IOException {

        oneWayType.setToggleGroup(toggleGroup);
        twoWayType.setToggleGroup(toggleGroup);

        if(doValidations()){
            return;
        }
        wrongLabel.setText("");

        double amount = doTransaction();
        if(amount > currentUserBalance){
            amount = -1;

        }
        createDialog(amount);
        if(amount != -1){
            currentUserBalance -= amount;

            loadData(amount);
        }

    }

    public void setupIdComboBox() {
        idComboBox.getItems().addAll(
                "Birth Certificate ID",
                "Health Insurance ID",
                "Passport",
                "Social Identity Number"
        );
        idComboBox.setValue("Driver Licence ID");
    }

    public void setupTicketComboBox() {
        ticketsComboBox.getItems().addAll(
                "2","3","4","5","6","7","8","9","10"
        );
        ticketsComboBox.setValue("1");
    }

    public boolean validateAddedMoney(String money){

        Pattern amount_pattern = Pattern.compile("^-?(?:0|[1-9]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d{1,2})?$");
        Matcher matcher = amount_pattern.matcher(money);
        return matcher.matches();
    }

    public void showAmountStatus(){

        currentBalanceText.setText(decimalFormat.format(currentUserBalance )+ " $");
        responseMessageText.setText("");
        addMoneyText.setText("");
    }

    public void addMoney(){
        String money = addMoneyText.getText().toString();
        if(validateAddedMoney(money)){
            currentUserBalance += Double.parseDouble(money);
            currentBalanceText.setText(decimalFormat.format(currentUserBalance ) + " $");
            responseMessageText.setText("Account has been successfully credited !");
            responseMessageText.setTextFill(Color.GREEN);
        }else{
            responseMessageText.setText("Invalid amount entered !!");
            responseMessageText.setTextFill(Color.RED);
        }
    }

}
