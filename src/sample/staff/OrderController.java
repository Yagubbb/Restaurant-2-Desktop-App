package sample.staff;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderController {
    @FXML
    private ComboBox orderComboBox;
    @FXML
    private Spinner quantitySpinner;

    public Order getNewOrder() {
        String order = orderComboBox.getSelectionModel().getSelectedItem().toString();
        int quantity = Integer.parseInt(quantitySpinner.getValue().toString());
        Order newOrder = new Order(order,quantity);
        return newOrder;
    }

}
