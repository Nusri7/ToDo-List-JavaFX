import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AppInitializer extends Application {

    public static void main(String[] args) {launch(args); }



    @Override
    public void start (Stage primaryStage) throws IOException {
        Parent parent = (Parent)FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("view/LoginForm.fxml")));
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login To Do");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

}
