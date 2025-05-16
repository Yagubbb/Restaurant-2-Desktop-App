package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainwindow.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());


        // Set up the scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Restaurant");
        primaryStage.setScene(scene);
        Image icon = new Image(getClass().getResource("/icons/app-icon.jpg").toExternalForm());
        primaryStage.getIcons().add(icon);
        // Pass the Stage to the Controller
        Controller controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}