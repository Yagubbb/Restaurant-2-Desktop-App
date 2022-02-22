package sample.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MealData {
    private static final String FOODS_FILE = "meals.xml";
    private static final String FOOD = "meal";

    private static final String NAME = "name";
    private static final String COST = "cost";

    private ObservableList<Food> meals;

    public MealData() {
        meals = FXCollections.observableArrayList();
    }

    public ObservableList<Food> getMeals(){
        return meals;

    }

    public void loadMeals() {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(FOODS_FILE);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Food food = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(FOOD)) {
                        food = new Food();
                        continue;
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(NAME)) {
                            event = eventReader.nextEvent();
                            food.setName(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(COST)) {
                        event = eventReader.nextEvent();
                        food.setCost(Double.parseDouble(event.asCharacters().getData()));
                        continue;
                    }




                }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(FOOD)) {
                        meals.add(food);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
