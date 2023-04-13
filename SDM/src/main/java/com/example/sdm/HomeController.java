package com.example.sdm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class HomeController {

    @FXML
    private Button button;

    @FXML
    private Label wrongLogIn;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    ArrayList<Credentials> credentialsArrayList = new ArrayList<>();


    public static String tempUserName = "";
    public static String tempPassword = "";

    public static double amount = 0.00;

    public void userLogIn(ActionEvent event) throws IOException{
        checkLogIn();
    }

    public void checkLogIn() throws IOException{

        credentialsArrayList.add(new Credentials("john", "123456",0.99));
        credentialsArrayList.add(new Credentials("dikshant", "123456",10.12));
        credentialsArrayList.add(new Credentials("zangwen", "123456", 5.49));
        credentialsArrayList.add(new Credentials("yang", "123456", 1.11));
        credentialsArrayList.add(new Credentials("mansa", "123456",4.22));
        credentialsArrayList.add(new Credentials("manaswini", "123456", 6.34));

        Main m = new Main();

        for(Credentials credential : credentialsArrayList){
            tempUserName = credential.username;
            tempPassword = credential.password;
            amount = credential.amount;

            if(username.getText().toString().equals(tempUserName) && password.getText().toString().equals(tempPassword)){
                wrongLogIn.setText("Success!");
                m.changeScene("afterLogin.fxml");
            }else if(username.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                wrongLogIn.setText("Empty Credentials");
            }else{
                wrongLogIn.setText("Wrong username or password");
            }

        }

    }


}
