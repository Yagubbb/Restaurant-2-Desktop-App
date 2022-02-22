package sample.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class EmployeeData {

    private static final String EMPLOYEE_FILE = "src/sample/manager/data/employeedata.json";

    private ObservableList<Employee> waiters;
    private ObservableList<Employee> chiefs;
    private ObservableList<Employee> cashiers;

    public EmployeeData() {

        waiters = FXCollections.observableArrayList();
        chiefs = FXCollections.observableArrayList();
        cashiers = FXCollections.observableArrayList();
    }

    public void loadEmployees() {
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(EMPLOYEE_FILE);
            JSONArray employeeArray = (JSONArray) jsonParser.parse(fileReader);
            for (Object object : employeeArray) {
                JSONObject employeeObject = (JSONObject) object;
                switch (employeeObject.get("type").toString()) {
                    case "cashier":
                        cashiers.add(getEmployee(employeeObject));
                        break;
                    case "waiter":
                        waiters.add(getEmployee(employeeObject));
                        break;
                    case "chief":
                        chiefs.add(getEmployee(employeeObject));
                        break;
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Employee> getWaiters() {
        return waiters;
    }

    public void setWaiters(ObservableList<Employee> waiters) {
        this.waiters = waiters;
    }

    public ObservableList<Employee> getChiefs() {
        return chiefs;
    }

    public void setChiefs(ObservableList<Employee> chiefs) {
        this.chiefs = chiefs;
    }

    public ObservableList<Employee> getCashiers() {
        return cashiers;
    }

    public void setCashiers(ObservableList<Employee> cashiers) {
        this.cashiers = cashiers;
    }

    public Employee getEmployee(JSONObject object) {
        return new Employee(object.get("name").toString(), object.get("surname").toString(),
                Integer.parseInt(object.get("age").toString()), object.get("address").toString(),
                object.get("type").toString(), Integer.parseInt(object.get("payment").toString()));

    }

    public JSONObject getJsonObject(Employee newEmployee) {
        JSONObject employee = new JSONObject();
        employee.put("name", newEmployee.getName());
        employee.put("surname", newEmployee.getSurname());
        employee.put("address", newEmployee.getAddress());
        employee.put("payment", newEmployee.getPayment());
        employee.put("type", newEmployee.getType());
        employee.put("age", newEmployee.getAge());
        return employee;
    }

    public void addEmployee(Employee newEmployee) {
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(EMPLOYEE_FILE);
            JSONArray employeeArray = (JSONArray) jsonParser.parse(fileReader);
            JSONObject employee = new JSONObject();
            employee.put("name", newEmployee.getName());
            employee.put("surname", newEmployee.getSurname());
            employee.put("address", newEmployee.getAddress());
            employee.put("payment", newEmployee.getPayment());
            employee.put("type", newEmployee.getType());
            employee.put("age", newEmployee.getAge());
            employeeArray.add(employee);
            PrintWriter writer = new PrintWriter(EMPLOYEE_FILE);
            writer.print("");
            writer.close();
            String temp = employeeArray.toJSONString();

            FileWriter fileWriter = new FileWriter(EMPLOYEE_FILE);
            fileWriter.write(temp);
            fileWriter.flush();
            fileWriter.close();

            switch (newEmployee.getType()) {
                case "cashier":
                    cashiers.add(newEmployee);
                    break;
                case "waiter":
                    waiters.add(newEmployee);
                    break;
                case "chief":
                    chiefs.add(newEmployee);
                    break;
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(Employee employee) {
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(EMPLOYEE_FILE);
            JSONArray employeeArray = (JSONArray) jsonParser.parse(fileReader);
            for (Object object : employeeArray) {
                JSONObject employeeObject = (JSONObject) object;
                if (employee.equals(getEmployee(employeeObject))) {

                    employeeArray.remove(object);
                    PrintWriter writer = new PrintWriter(EMPLOYEE_FILE);
                    writer.print("");
                    writer.close();
                    String temp = employeeArray.toJSONString();

                    FileWriter fileWriter = new FileWriter(EMPLOYEE_FILE);
                    fileWriter.write(temp);
                    fileWriter.flush();
                    fileWriter.close();
                    removeEmployee(employee);
                    return;
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }


    }

    public void removeEmployee(Employee employee) {
        switch (employee.getType()) {
            case "cashier":
                cashiers.remove(employee);
                break;
            case "waiter":
                waiters.remove(employee);
                break;
            case "chief":
                chiefs.remove(employee);
                break;
        }
    }

    public void editEmployee(Employee employee, Employee newEmployee) {
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(EMPLOYEE_FILE);
            JSONArray employeeArray = (JSONArray) jsonParser.parse(fileReader);
            for (Object object : employeeArray) {
                JSONObject employeeObject = (JSONObject) object;
                if (employee.equals(getEmployee(employeeObject))) {

                    employeeArray.set(employeeArray.indexOf(object), getJsonObject(newEmployee));
                    PrintWriter writer = new PrintWriter(EMPLOYEE_FILE);
                    writer.print("");
                    writer.close();
                    String temp = employeeArray.toJSONString();

                    FileWriter fileWriter = new FileWriter(EMPLOYEE_FILE);
                    fileWriter.write(temp);
                    fileWriter.flush();
                    fileWriter.close();

                    switch (employee.getType()) {
                        case "cashier":
                            cashiers.set(cashiers.indexOf(employee),newEmployee);
                            break;
                        case "waiter":
                            waiters.set(waiters.indexOf(employee),newEmployee);
                            break;
                        case "chief":
                            chiefs.set(chiefs.indexOf(employee),newEmployee);
                            break;
                    }
                    return;
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }

    public String  getTotalPayment(){
        int total = 0;
        for (Employee employee : chiefs){
            System.out.println(employee.getPayment());
            total += employee.getPayment();
        }
        for (Employee employee : cashiers){
            System.out.println(employee.getPayment());
            total += employee.getPayment();
        }
        for (Employee employee : waiters){
            System.out.println(employee.getPayment());
            total += employee.getPayment();
        }
        return Integer.toString(total);
    }
}
