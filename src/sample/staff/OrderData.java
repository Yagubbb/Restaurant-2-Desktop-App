package sample.staff;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.menu.Food;

public class OrderData {
    private static final String ORDER_FILE = "src/sample/staff/data/" + LocalDate.now().toString() + ".json";
    DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm");
    private File file = new File(ORDER_FILE);

    private ObservableList<Order> orders;
    private ObservableList<TodaysOrder> todaysOrders;

    public OrderData() {
        orders = FXCollections.observableArrayList();
        todaysOrders = FXCollections.observableArrayList();
    }

    public void loadTodaysOrders() {
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(file.getAbsolutePath());
            JSONArray orderArray = (JSONArray) jsonParser.parse(fileReader);
            String[] splitStr;

            for (int i = 0; i < orderArray.size(); i++) {
                JSONObject orderObject = (JSONObject) orderArray.get(i);
                splitStr = orderObject.keySet().toString().split("\\s+");

                JSONArray array = (JSONArray) orderObject.get(" order" + (i + 1) + " " + splitStr[2] + " ");
                ArrayList<Order> orders = new ArrayList<>();
                double orderTotal = 0.0;
                for (int j = 0; j < array.size(); j++) {
                    JSONObject jsonObject = (JSONObject) array.get(j);
                    Order order = new Order(jsonObject.get("name").toString(), Integer.parseInt(jsonObject.get("quantity").toString()));
                    orders.add(order);
                    orderTotal += Double.parseDouble(jsonObject.get("total").toString());
                }

                TodaysOrder todaysOrder = new TodaysOrder(String.valueOf(i + 1), splitStr[2], String.valueOf(orderTotal), orders);
                todaysOrders.add(todaysOrder);
            }
        } catch (IOException | ParseException e) {
        }
    }

    public void loadOrders(String day) {
        try {

            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader("src/sample/staff/data/" + day + ".json");
            JSONArray orderArray = (JSONArray) jsonParser.parse(fileReader);
            String[] splitStr;

            for (int i = 0; i < orderArray.size(); i++) {
                JSONObject orderObject = (JSONObject) orderArray.get(i);
                splitStr = orderObject.keySet().toString().split("\\s+");

                JSONArray array = (JSONArray) orderObject.get(" order" + (i + 1) + " " + splitStr[2] + " ");
                ArrayList<Order> orders = new ArrayList<>();
                double orderTotal = 0.0;
                for (int j = 0; j < array.size(); j++) {
                    JSONObject jsonObject = (JSONObject) array.get(j);
                    Order order = new Order(jsonObject.get("name").toString(), Integer.parseInt(jsonObject.get("quantity").toString()));
                    orders.add(order);

                    orderTotal += Double.parseDouble(jsonObject.get("total").toString());
                }
                TodaysOrder todaysOrder = new TodaysOrder(String.valueOf(i + 1), splitStr[2], String.valueOf(orderTotal), orders);
                todaysOrders.add(todaysOrder);
            }
        } catch (IOException | ParseException e) {
        }
    }
    public double loadTotal(String day) {
        double orderTotal = 0.0;
        try {

            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader("src/sample/staff/data/" + day + ".json");
            JSONArray orderArray = (JSONArray) jsonParser.parse(fileReader);
            String[] splitStr;

            for (int i = 0; i < orderArray.size(); i++) {
                JSONObject orderObject = (JSONObject) orderArray.get(i);
                splitStr = orderObject.keySet().toString().split("\\s+");

                JSONArray array = (JSONArray) orderObject.get(" order" + (i + 1) + " " + splitStr[2] + " ");

                for (int j = 0; j < array.size(); j++) {
                    JSONObject jsonObject = (JSONObject) array.get(j);

                    orderTotal += Double.parseDouble(jsonObject.get("total").toString());
                    System.out.println(Double.parseDouble(jsonObject.get("total").toString()));
                }

            }

        } catch (IOException | ParseException e) {
        }
        return orderTotal;
    }
    public void deleteOrder(Order item) {
        orders.remove(item);
    }
    public void saveOrder() {
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader;
        PrintWriter writer;
        String temp;
        JSONArray ordersArray;
        if (!(file.exists() && !file.isDirectory())) {
            try {
                file.createNewFile();
                System.out.println("Not found");
                //  fileReader = new FileReader(file.getAbsolutePath());
                ordersArray = new JSONArray();
                writer = new PrintWriter(ORDER_FILE);
                writer.print("");
                writer.close();
                temp = ordersArray.toJSONString();

                FileWriter fileWriter = new FileWriter(ORDER_FILE);
                fileWriter.write(temp);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileReader = new FileReader(file.getAbsolutePath());
            ordersArray = (JSONArray) jsonParser.parse(fileReader);

            String key = " order" + (ordersArray.size() + 1) + " " + LocalDateTime.now().format(df) + " ";

            JSONArray orderList = new JSONArray();
            ArrayList<Order> orderArrayList = new ArrayList<>();
            double total = 0.0;
            for (Order order : orders) {
                JSONObject food = new JSONObject();
                food.put("name", order.getFood().getName());
                food.put("quantity", order.getQuantity());
                total += order.getTotal();
                food.put("total", order.getTotal());
                orderArrayList.add(order);
                orderList.add(food);
            }
            JSONObject object = new JSONObject();
            object.put(key, orderList);
            ordersArray.add(object);


            writer = new PrintWriter(ORDER_FILE);
            writer.print("");
            writer.close();
            temp = ordersArray.toJSONString();

            FileWriter fileWriter = new FileWriter(ORDER_FILE);
            fileWriter.write(temp);
            fileWriter.flush();
            fileWriter.close();
            TodaysOrder todaysOrder = new TodaysOrder(String.valueOf((ordersArray.size())), LocalDateTime.now().format(df), String.valueOf(total), orderArrayList);
            todaysOrders.add(todaysOrder);
            System.out.println("Orders saved");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


    }

    public ObservableList<TodaysOrder> getTodaysOrders() {
        return todaysOrders;
    }

    public void setTodaysOrders(ObservableList<TodaysOrder> todaysOrders) {
        this.todaysOrders = todaysOrders;
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ObservableList<Order> orders) {
        this.orders = orders;
    }

    public void addTodaysOrder(TodaysOrder item) {
        todaysOrders.add(item);
    }

    public void addOrder(Order item) {
        orders.add(item);
    }

}
