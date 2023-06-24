package com.example.crud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private BorderPane login_form;

    @FXML
    private BorderPane register_form;
    @FXML
    private Button create_account;

    @FXML
    private Button si_login;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button su_button;

    @FXML
    private Button su_login;

    @FXML
    private PasswordField su_password;

    @FXML
    private TextField su_username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void Login(){
        String sql = "SELECT username,password FROM admin2 WHERE username = ? and password = ?";
        connect = Database.connect();
        try{
            Alert alert;
            if(si_username.getText().isEmpty() || si_password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();

                if(result.next()){
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    // TO HIDE THE LOGIN FORM
                    si_login.getScene().getWindow().hide();

                    // TO OPEN A NEW WINDOW
                    Parent root = FXMLLoader.load(getClass().getResource("form.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setTitle("JAVA CRUD OPERATION");
                    stage.setScene(scene);
                    stage.show();

                }else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Credentials");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void Register(){
        String sql = "INSERT INTO admin2 (username, password) VALUES (?,?)";
        connect = Database.connect();
        try{
            Alert alert;
            if(su_username.getText().isEmpty() || su_password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, su_username.getText());
                prepare.setString(2, su_password.getText());
                prepare.executeUpdate();
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("User created Successfully");
                alert.showAndWait();

                login_form.setVisible(true);
                register_form.setVisible(false);
                su_username.setText("");
                su_password.setText("");

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void SwitchForm(ActionEvent event) {
        if(event.getSource() == su_login){
            login_form.setVisible(true);
            register_form.setVisible(false);
        }else{
            if(event.getSource() ==create_account ){
                login_form.setVisible(false);
                register_form.setVisible(true);
            }
        }

    }
}