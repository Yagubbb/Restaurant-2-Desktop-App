package sample.manager;

public class Person {
    private String name,surname;
    private int age;

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;

        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}

