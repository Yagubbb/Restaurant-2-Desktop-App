package sample.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.InformationController;
import sample.staff.OrderData;
import sample.staff.TodaysOrder;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ManagerController {
    @FXML
    private ListView<String> daysListView;
    @FXML
    private Label incomeLabel;
    @FXML
    private TableView<TodaysOrder> ordersTable;
    @FXML
    private TableView<Employee> waitersTable;
    @FXML
    private TableView<Employee> chiefsTable;
    @FXML
    private TableView<Employee> cashiersTable;
    @FXML
    private Label daysIncomeLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label paymentLabel;
    @FXML
    private Label totalPaymentLabel;

    private OrderData orderData;
    private EmployeeData employeeData;
    public Double income = 0.0;

    public void initialize() {
        orderData = new OrderData();
        employeeData = new EmployeeData();
        ObservableList<String> days = FXCollections.observableArrayList();

        File dir = new File("src/sample/staff/data");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {

                String st = child.toString().substring(child.toString().lastIndexOf('\\') + 1, child.toString().lastIndexOf('.'));
                System.out.println(st);
                income += orderData.loadTotal(st);
                days.add(st);
            }
        }
        incomeLabel.setText(String.valueOf(income));
        daysListView.setItems(days);


        daysListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ordersTable.getItems().clear();
                String item = daysListView.getSelectionModel().getSelectedItem();

                orderData.loadOrders(item);
                daysIncomeLabel.setText(String.valueOf(orderData.loadTotal(item)));
                ordersTable.setItems(orderData.getTodaysOrders());

            }
        });

        employeeData.loadEmployees();
        chiefsTable.setItems(employeeData.getChiefs());
        waitersTable.setItems(employeeData.getWaiters());
        cashiersTable.setItems(employeeData.getCashiers());
        totalPaymentLabel.setText(employeeData.getTotalPayment());
    }

    @FXML
    public void showAddEmployee() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("employeedialog.fxml"));
            Pane employeeDialogPane = fxmlLoader.load();

            EmployeeDialogController employeeDialogController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) employeeDialogPane);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.show();


            dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
                Employee employee = employeeDialogController.getNewEmployee();
                if (employee == null) {
                    event.consume();
                } else {
                    employeeData.addEmployee(employee);
                    totalPaymentLabel.setText(employeeData.getTotalPayment());

                }
            });

        } catch (IOException e) {
            System.out.println("Can not load");
            e.printStackTrace();
            return;
        }

    }

    @FXML
    public void deleteEmployee() {
        Employee selectedEmployee = waitersTable.getSelectionModel().getSelectedItem() != null ? waitersTable.getSelectionModel().getSelectedItem() :
                cashiersTable.getSelectionModel().getSelectedItem() != null ? cashiersTable.getSelectionModel().getSelectedItem() : chiefsTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No employee selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the employee you want to delete");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete employee");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected employee "
                + selectedEmployee.getName() + " " + selectedEmployee.getSurname());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            employeeData.deleteEmployee(selectedEmployee);
            handleLabels(null);
            totalPaymentLabel.setText(employeeData.getTotalPayment());
        }
    }

    public void showEditDialog() {
        Employee selectedEmployee = waitersTable.getSelectionModel().getSelectedItem() != null ? waitersTable.getSelectionModel().getSelectedItem() :
                cashiersTable.getSelectionModel().getSelectedItem() != null ? cashiersTable.getSelectionModel().getSelectedItem() : chiefsTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No employee selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the employee you want to edit");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("employeedialog.fxml"));
            Pane employeeDialogPane = fxmlLoader.load();

            EmployeeDialogController employeeDialogController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) employeeDialogPane);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            employeeDialogController.editEmployee(selectedEmployee);
            dialog.show();

            dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
                Employee employee = employeeDialogController.getNewEmployee();
                if (employee == null) {
                    event.consume();
                } else {
                    employeeData.editEmployee(selectedEmployee,employee);
                    handleLabels(employee);
                    totalPaymentLabel.setText(employeeData.getTotalPayment());


                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void handleDoubleClick(MouseEvent click) {
        TodaysOrder selectedItem = ordersTable.getSelectionModel().getSelectedItem();

        if (click.getClickCount() == 2) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/information.fxml"));
                Pane informationDialogPane = fxmlLoader.load();

                InformationController informationController = fxmlLoader.getController();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane((DialogPane) informationDialogPane);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

                informationController.setTable(selectedItem);
                dialog.showAndWait();

            } catch (IOException e) {
                System.out.println("Can not load");
                e.printStackTrace();
                return;
            }
        }

    }

    @FXML
    public void handleClick(MouseEvent click) {
        Employee selectedEmployee;

        if (click.getSource().equals(cashiersTable)) {
            selectedEmployee = cashiersTable.getSelectionModel().getSelectedItem();
            chiefsTable.getSelectionModel().clearSelection();
            waitersTable.getSelectionModel().clearSelection();
        } else if (click.getSource().equals(chiefsTable)) {
            selectedEmployee = chiefsTable.getSelectionModel().getSelectedItem();
            waitersTable.getSelectionModel().clearSelection();
            cashiersTable.getSelectionModel().clearSelection();
        } else {
            selectedEmployee = waitersTable.getSelectionModel().getSelectedItem();
            cashiersTable.getSelectionModel().clearSelection();
            chiefsTable.getSelectionModel().clearSelection();
        }
        handleLabels(selectedEmployee);
    }

    private void handleLabels(Employee employee) {
        if (employee == null) {
            nameLabel.setText("");
            surnameLabel.setText("");
            ageLabel.setText("");
            addressLabel.setText("");
            paymentLabel.setText("");
        } else {
            nameLabel.setText(employee.getName());
            surnameLabel.setText(employee.getSurname());
            ageLabel.setText(String.valueOf(employee.getAge()));
            addressLabel.setText(employee.getAddress());
            paymentLabel.setText(String.valueOf(employee.getPayment()));
        }
    }


}
