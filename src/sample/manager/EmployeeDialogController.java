package sample.manager;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class EmployeeDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField surnameField;
    @FXML
    private Spinner ageSpinner;
    @FXML
    private TextField addressField;
    @FXML
    private Spinner paymentSpinner;
    @FXML
    private ComboBox typeBox;

    public void initialize() {
        firstNameField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (firstNameField.getText().equals("1")) {
                    firstNameField.setText("123");
                }
            }
        });

    }

    public Employee getNewEmployee() {

        if (firstNameField.getText().equals("") || surnameField.getText().equals("") || addressField.getText().equals("")) {
            return null;
        } else {
            return new Employee(firstNameField.getText(), surnameField.getText(),
                    Integer.parseInt(ageSpinner.getValue().toString()), addressField.getText(),
                    typeBox.getSelectionModel().getSelectedItem().toString().toLowerCase(), Integer.parseInt(paymentSpinner.getValue().toString()));
        }
    }

    public void editEmployee(Employee employee) {
        firstNameField.setText(employee.getName());
        surnameField.setText(employee.getSurname());
        addressField.setText(employee.getAddress());
        ageSpinner.getValueFactory().setValue(employee.getAge());
        paymentSpinner.getValueFactory().setValue(employee.getPayment());
        typeBox.setValue(employee.getType().substring(0, 1).toUpperCase() + employee.getType().substring(1));
    }
    public void updateEmployee(Employee employee){
        employee.setName(firstNameField.getText());
        employee.setSurname(surnameField.getText());
        employee.setAddress(addressField.getText());
        employee.setAge(Integer.parseInt(ageSpinner.getValue().toString()));
        employee.setPayment(Integer.parseInt(paymentSpinner.getValue().toString()));
        employee.setType(typeBox.getSelectionModel().getSelectedItem().toString().toLowerCase());

    }
}
