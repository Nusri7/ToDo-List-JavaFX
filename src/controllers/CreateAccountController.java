package controllers;

import database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CreateAccountController {
    public Label lblUserId;
    public TextField txtEmail;
    public Label lblUserName;
    public Label lblemail;
    public Label lblNewPassword;
    public Label lblConfirmPassword;
    public TextField txtUserName;
    public Button btnRegister;
    public AnchorPane root;



    public void initialize(){
        setVisible(false);
        setDisable(true);
    }

    public PasswordField txtNewPassword;
    public PasswordField txtConfirmPassword;
    public Label lblPasswordDoesNotExist1;
    public Label lblPasswordDoesNotExist2;


    public void btnRegisterOnAction(ActionEvent actionEvent) {
        register();
        dbUpdate();

    }
    public void setBorder(String colour){
        txtNewPassword.setStyle("-fx-border-color:" + colour);
        txtConfirmPassword.setStyle("-fx-border-color:" + colour);
    }
    public void setVisible(boolean visibility){
        lblPasswordDoesNotExist1.setVisible(visibility);
        lblPasswordDoesNotExist2.setVisible(visibility);
    }

    public void autoGenerateID(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from user order by id desc limit 1");

            boolean isExist = resultSet.next();
            if(isExist){
                String userID = resultSet.getString(1);

                userID = userID.substring(1,userID.length());
                int intID = Integer.parseInt(userID);
                intID++;

                if(intID < 10){
                    lblUserId.setText("U00" + intID);
                }else if(intID < 100){
                    lblUserId.setText("U0" + intID);
                }else {
                    lblUserId.setText("U" + intID);
                }

            }else{
                lblUserId.setText("U001");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void txtNewPasswordOnAction(ActionEvent actionEvent) {
        txtConfirmPassword.requestFocus();
    }

    public void txtConfirmPasswordOnAction(ActionEvent actionEvent) {
        register();
        dbUpdate();
    }

    public void btnAddUserOnAction(ActionEvent actionEvent) {
        setDisable(false);
        Connection connection = DBConnection.getInstance().getConnection();
        System.out.println(connection);
        autoGenerateID();
    }
    public void register(){
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();



        if(newPassword.equals(confirmPassword)){
            setBorder("chartreuse");
            setVisible(false);

        }else{
            setBorder("crimson");
            setVisible(true);
            txtNewPassword.requestFocus();
        }

    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    public void txtEmailOnAction(ActionEvent actionEvent) {
        txtNewPassword.requestFocus();
    }



    public void txtConfirmPasswordOnKeyRelesed(KeyEvent keyEvent) {
        String text = txtNewPassword.getText();
        String text1 = txtConfirmPassword.getText();

        if(text.equals(text1)) {
            setBorder("chartreuse");
            setVisible(false);

        }else{
            setBorder("transparent");
        }


    }
    public void setDisable(boolean disability){
        txtEmail.setDisable(disability);
        txtNewPassword.setDisable(disability);
        txtConfirmPassword.setDisable(disability);
        txtUserName.setDisable(disability);
        lblUserId.setDisable(disability);
        lblemail.setDisable(disability);
        lblUserName.setDisable(disability);
        lblNewPassword.setDisable(disability);
        lblConfirmPassword.setDisable(disability);
        btnRegister.setDisable(disability);
    }

    public void txtNewPasswordOnKeyReleased(KeyEvent keyEvent) {
        String text = txtNewPassword.getText();
        String text1 = txtConfirmPassword.getText();
        if (text1.equals(text)){
            setVisible(false);

        }else{
            setBorder("transparent");
        }
    }
    public void dbUpdate(){
        Connection connection = DBConnection.getInstance().getConnection();
        String id =lblUserId.getText();
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtConfirmPassword.getText();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into user values(?,?,?,?)");
            preparedStatement.setObject(1,id);
            preparedStatement.setObject(2,userName);
            preparedStatement.setObject(3,email);
            preparedStatement.setObject(4,password);

            int i = preparedStatement.executeUpdate();

            if(i != 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Success");
                alert.showAndWait();

                Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
                Scene scene = new Scene(parent);

                Stage primaryStage = (Stage) root.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setTitle("Login Form");
                primaryStage.centerOnScreen();



            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }


    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);

        Stage primaryStage = (Stage) root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.centerOnScreen();

    }
}

