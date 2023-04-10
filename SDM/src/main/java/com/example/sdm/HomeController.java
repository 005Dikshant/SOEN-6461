package com.example.sdm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button button;

    @FXML
    private Label wrongLogIn;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    public void userLogIn(ActionEvent event) throws IOException{
        checkLogIn();
    }

    public void checkLogIn() throws IOException{

        String tempUserName = "dikshant";
        String tempPassword = "123";

        Main m = new Main();
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
