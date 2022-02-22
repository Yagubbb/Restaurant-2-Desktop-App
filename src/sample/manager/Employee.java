package sample.manager;


import java.util.Objects;

public class Employee extends Person {

    String address, type;
    int payment;

    public Employee(String name, String surname, int age, String address, String type, int payment) {
        super(name, surname, age);
        this.address = address;
        this.type = type;
        this.payment = payment;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return payment == employee.payment &&
                address.equals(employee.address) &&
                type.equals(employee.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, type, payment);
    }
}
