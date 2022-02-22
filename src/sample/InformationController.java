package sample;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.staff.Order;
import sample.staff.TodaysOrder;

public class InformationController {
    @FXML
    private DialogPane mainPane;

    public void setTable(TodaysOrder todaysOrder) {
        TableView<Order> foodTableView = new TableView<Order>();
        // System.out.println(todaysOrder.getOrdersList());

        TableColumn<Order, String> foodColumn = new TableColumn<>("Food");
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("food"));

        TableColumn<Order, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Order, Double> totalColumn = new TableColumn("Total");
        totalColumn.setCellValueFactory(new PropertyValueFactory("total"));

        foodTableView.getColumns().add(foodColumn);
        foodTableView.getColumns().add(quantityColumn);
        foodTableView.getColumns().add(totalColumn);
        foodTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        foodTableView.setFixedCellSize(40);
        foodTableView.prefHeightProperty().bind(foodTableView.fixedCellSizeProperty().multiply(Bindings.size(foodTableView.getItems()).add(1.01)));

        for (Order order : todaysOrder.getOrdersList()) {
            foodTableView.getItems().add(order);
        }

        mainPane.setContent(foodTableView);
    }
}
