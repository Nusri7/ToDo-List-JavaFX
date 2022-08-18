package controllers;

import database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    public AnchorPane root;
    public PasswordField lblPassword;
    public TextField txtUserName;
    public static String welcomeUser;
    public static String userId;

    public void username() {
        String user;

        {
            assert txtUserName != null;
            user = txtUserName.getText();
        }
    }
    public void lblCreateNewAccountOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/CreateAccountForm.fxml"));
        Scene scene = new Scene(parent);

        Stage primaryStage = (Stage) root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register Form");
        primaryStage.centerOnScreen();

    }

    public void lblPasswordOnAction(ActionEvent actionEvent) {
        login();
    }
    public void login(){
        String userName = txtUserName.getText();
        String password = lblPassword.getText();

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where username = ? and password = ?");
            preparedStatement.setObject(1,userName);
            preparedStatement.setObject(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                welcomeUser = resultSet.getString(2);
                userId = resultSet.getString(1);

                Parent parent = FXMLLoader.load(this.getClass().getResource("../view/ToDoForm.fxml"));
                Scene scene = new Scene(parent);

                Stage primaryStage = (Stage) root.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setTitle("To Do List");
                primaryStage.centerOnScreen();

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"User Name and Password does not matched!");
                alert.showAndWait();
            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        txtUserName.requestFocus();
    }

    public void btnLogInOnAction(ActionEvent actionEvent) {
        login();
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        lblPassword.requestFocus();
    }
}
