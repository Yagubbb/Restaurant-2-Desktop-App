package sample.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.InformationController;
import sample.menu.BeverageData;
import sample.menu.Food;
import sample.menu.MealData;

import java.io.IOException;
import java.util.Optional;

public class StaffController {
    @FXML
    public TableView<Food> beverageTable;
    @FXML
    public TableView<Food> mealTable;
    @FXML
    public TableView<TodaysOrder> todaysOrderTable;
    @FXML
    public TableView<Order> orderTable;
    @FXML
    public Button finishOrderButton;
    @FXML
    private Label totalCost;

    private MealData mealData;
    private BeverageData beverageData;

    private OrderData orderData;
    private double total = 0.0;

    public void initialize() {
        orderData = new OrderData();
        orderTable.setItems(orderData.getOrders());
        orderData.loadTodaysOrders();

        todaysOrderTable.setItems(orderData.getTodaysOrders());

        mealData = new MealData();
        mealData.loadMeals();
        mealTable.setItems(mealData.getMeals());

        beverageData = new BeverageData();
        beverageData.loadBeverages();
        beverageTable.setItems(beverageData.getBeverages());

    }

    @FXML
    public void showAddOrderDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("orderdialog.fxml"));
            Pane orderDialogPane = fxmlLoader.load();

            OrderController orderController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) orderDialogPane);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Order newOrder = orderController.getNewOrder();
                if (newOrder.getQuantity() != 0) {
                    total += newOrder.getTotal();
                    totalCost.setText(String.valueOf(total));
                    orderData.addOrder(newOrder);
                }

            }
        } catch (IOException e) {
            System.out.println("Can not load");
            e.printStackTrace();
            return;
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
            deleteOrder();
        }
    }

    @FXML
    public void deleteOrder() {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No order selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to delete");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete order");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected order ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            orderData.deleteOrder(selectedOrder);
            total -= selectedOrder.getTotal();
            totalCost.setText(String.valueOf(total));

        }
    }

    @FXML
    public void finishOrder() {
        if (orderData.getOrders().size() != 0) {
            orderData.saveOrder();
            orderData.getOrders().clear();
            totalCost.setText(String.valueOf(total = 0.0));
        }
    }

    @FXML
    public void clearOrder() {
        orderData.getOrders().clear();
        totalCost.setText(String.valueOf(total = 0.0));
    }

    @FXML
    public void handleDoubleClick(MouseEvent click) {
        TodaysOrder selectedItem = todaysOrderTable.getSelectionModel().getSelectedItem();

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
    public void handleExit(){
        Platform.exit();
    }
}
