package sample.staff;

import sample.menu.Food;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;

public class Order {
    public Food getFood() {
        return food;
    }
    private Food food;
    private int quantity;
    private double total;
    private String time;

    public double getTotal() {
        return total;
    }

    public Order() {
    }

    public Order(String name, int quantity) {
        this.food = getFood(name);
        this.quantity = quantity;
        this.total = quantity * this.food.getCost();
        this.time = LocalDate.now().toString();
    }

    public Food getFood(String name) {
        try {
            XMLInputFactory inputFactory1 = XMLInputFactory.newInstance();
            InputStream in1 = new FileInputStream("meals.xml");
            XMLEventReader eventReader1 = inputFactory1.createXMLEventReader(in1);

            XMLInputFactory inputFactory2 = XMLInputFactory.newInstance();
            InputStream in2 = new FileInputStream("beverage.xml");
            XMLEventReader eventReader2 = inputFactory2.createXMLEventReader(in2);

           // Food food = null;
            String tempSt;
            double tempDouble;
            while (eventReader1.hasNext()) {
                XMLEvent event = eventReader1.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals("name")) {
                            event = eventReader1.nextEvent();
                            if (name.equals(event.asCharacters().getData())) {

                                tempSt = event.asCharacters().getData();

                                while (eventReader1.hasNext()) {
                                    XMLEvent event2 = eventReader1.nextEvent();
                                    if (event2.isStartElement()) {
                                        StartElement startElement2 = event2.asStartElement();
                                        if (event2.isStartElement()) {
                                            if (event2.asStartElement().getName().getLocalPart()
                                                    .equals("cost")) {
                                                event2 = eventReader1.nextEvent();
                                                tempDouble = Double.parseDouble(event2.asCharacters().getData());
                                                return new Food(tempSt,tempDouble);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }



                }


            }
            while (eventReader2.hasNext()) {
                XMLEvent event = eventReader2.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have a contact item, we create a new contact

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals("name")) {
                            event = eventReader2.nextEvent();

                            if (name.equals(event.asCharacters().getData())) {

                                tempSt = event.asCharacters().getData();

                                while (eventReader2.hasNext()) {
                                    XMLEvent event2 = eventReader2.nextEvent();
                                    if (event2.isStartElement()) {
                                        StartElement startElement2 = event2.asStartElement();
                                        if (event2.isStartElement()) {
                                            if (event2.asStartElement().getName().getLocalPart()
                                                    .equals("cost")) {
                                                event2 = eventReader2.nextEvent();
                                                tempDouble = Double.parseDouble(event2.asCharacters().getData());
                                                return new Food(tempSt,tempDouble);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }



                }


            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        return new Food("Test", 3.5);
    }

    public String getFoodName() {
        return this.food.getName();
    }


    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "" + food ;
    }
}
