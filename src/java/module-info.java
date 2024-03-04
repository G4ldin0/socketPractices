module org.projApplication {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.projApplication.view;
    opens org.projApplication.view to javafx.fxml;
    exports org.projApplication.controller;
    opens org.projApplication.controller to javafx.fxml;
    exports org.projApplication.process;
}