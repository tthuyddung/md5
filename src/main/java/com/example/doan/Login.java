package com.example.doan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Database.Database;
import javafx.stage.Stage;

public class Login implements Initializable {

    @FXML
    private Hyperlink lo_create;

    @FXML
    private Button lo_loginBtn;

    @FXML
    private PasswordField lo_password;

    @FXML
    private TextField lo_username;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Hyperlink re_already;

    @FXML
    private TextField re_email;

    @FXML
    private PasswordField re_password;

    @FXML
    private Button re_registerBtn;

    @FXML
    private TextField re_username;

    @FXML
    private AnchorPane register_form;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public boolean validEmail(){//first-letter][second-letter->before @] [@] [ten] [.] [com]
        Pattern patten = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher match = patten.matcher(re_email.getText());

        Alert alert;

        if(match.find() && match.group().matches(re_email.getText())){
            return true;
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email");
            alert.showAndWait();

            return false;
        }
    }

    public void singup(){
        String sql = "INSERT INTO user (email,username,password) VALUES (?,?,?)";

        connect = Database.connectDb();

        try{
            prepare= connect.prepareStatement(sql);
            prepare.setString(1, re_email.getText());
            prepare.setString(2, re_username.getText());
            prepare.setString(3,re_password.getText());

            String encryptedPassword = MD5Encryption.encrypt(re_password.getText());
            prepare.setString(3, encryptedPassword);

            Alert alert;

            if(re_email.getText().isEmpty()
                    || re_username.getText().isEmpty()
                    || re_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();


            }else {
                if (validEmail()) {
                    prepare.execute();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully create a new account");
                    alert.showAndWait();

                    re_email.setText("");
                    re_username.setText("");
                    re_password.setText("");
                } else {


                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void signin(){
        String sql = "SELECT * FROM user WHERE username = ? and password = ?";

        connect = Database.connectDb();
        try{
            prepare  = connect.prepareStatement(sql);
            prepare.setString(1, lo_username.getText());
            prepare.setString(2, lo_password.getText());

            String encryptedPassword = MD5Encryption.encrypt(lo_password.getText());
            prepare.setString(2, encryptedPassword);

            result = prepare.executeQuery();

            Alert alert;

            if(lo_username.getText().isEmpty() || lo_password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fiedls");
                alert.showAndWait();
            }else{
                if(result.next()){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succcessfully Login");
                    alert.showAndWait();

                    Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.show();
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public void switchForm(ActionEvent event){
        if(event.getSource() == lo_create){
            login_form.setVisible(false);
            register_form.setVisible(true);
        }else if(event.getSource() == re_already){
            login_form.setVisible(true);
            register_form.setVisible(false);
        }
    }

    public  void initialize(URL url, ResourceBundle rb){


    }

}