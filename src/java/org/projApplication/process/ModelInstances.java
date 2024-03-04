package org.projApplication.process;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

class Interface extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new VBox(new Label("teste"), new Button("oi"));
        stage.setTitle("Hello World!");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}

class Anel
{


    static class Main1{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.2", "127.0.0.4"};
            new ProcessUnit("127.0.0.1", neighbours);

        }
}

    static class Main2 {
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.3", "127.0.0.1"};
            new ProcessUnit("127.0.0.2", neighbours);
        }
}

    static class Main3 {
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.4", "127.0.0.2"};
            new ProcessUnit("127.0.0.3", neighbours);
        }
}

    static class Main4 {
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.1", "127.0.0.3"};
            new ProcessUnit("127.0.0.4", neighbours);
        }
    }
}

class Estrela
{
    static class e1{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.2", "127.0.0.3", "127.0.0.4"};
            new ProcessUnit("127.0.0.1", neighbours);
        }
    }

    static class e2{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.1"};
            new ProcessUnit("127.0.0.2", neighbours);
        }
    }

    static class e3{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.1"};
            new ProcessUnit("127.0.0.3", neighbours);
        }
    }

    static class e4{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.1"};
            new ProcessUnit("127.0.0.4", neighbours);
        }
    }

}
