module Restaurant2 {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.xml;
    requires json.simple;

    opens sample to javafx.fxml;
    opens sample.menu to javafx.fxml;
    opens sample.staff to javafx.fxml, javafx.base;
    opens sample.manager to javafx.fxml, javafx.base;
    exports sample;
    exports sample.menu;

}