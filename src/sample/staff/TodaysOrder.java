package sample.staff;

import java.util.ArrayList;

public class TodaysOrder {
    private String orderIndex;
    private String orderTime;
    private String orderCost;
    private ArrayList<Order> ordersList;


    public TodaysOrder() {
    }

    public TodaysOrder(String orderIndex, String orderTime, String orderCost, ArrayList<Order> ordersList) {
        this.orderIndex = orderIndex;
        this.orderTime = orderTime;
        this.orderCost = orderCost;
        this.ordersList = ordersList;
    }

    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    public ArrayList<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(ArrayList<Order> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public String toString() {
        return "TodaysOrder{" + "orderIndex='" + orderIndex + '\'' + ", orderTime='" + orderTime + '\'' + ", orderCost='" + orderCost + '\'' + ", ordersList=" + ordersList + '}';
    }
}
