package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Controller {
    @FXML
    private BorderPane main;

    @FXML
    private ToggleButton staffToggleButton;
    @FXML
    private ToggleButton managerToggleButton;
    @FXML
    private Label label;



    @FXML
    public void changePane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        if (managerToggleButton.isSelected()) {

            loader.setLocation(getClass().getResource("manager/managerwindow.fxml"));
        } else {
            loader.setLocation(getClass().getResource("staff/staffwindow.fxml"));

        }
        main.setCenter(loader.load());
    }



}
