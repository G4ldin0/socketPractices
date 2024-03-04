package org.projApplication.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.projApplication.controller.MainController;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class MainApplication extends Application {
    protected static String msg;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));

        final Label[][] calmaJaVai = new Label[1][1];

        // TODO: Esse objeto aqui vai ser a instancia de Processo que vou importar daqui a pouco
        final String[] instancia = {msg};
        fxmlLoader.setControllerFactory(type -> {
            try {
                Constructor<?> constructor = type.getConstructor(String.class);
                MainController instance = (MainController) constructor.newInstance(instancia[0]);
                stage.addEventHandler(KeyEvent.KEY_PRESSED, instance::handleKeyboardInput);
                calmaJaVai[0] = instance.createUsers();

                return instance;
            } catch (Exception exc) {
                // fatal...
                throw new RuntimeException(exc);
            }
        });


        Scene scene = fxmlLoader.load();

//        ((AnchorPane)((HBox)scene.getRoot()).getChildren().get(1)).getChildren().addAll(calmaJaVai[0]);

        var opa = ((HBox)scene.getRoot()).getChildren();
        ((AnchorPane)opa.get(1)).getChildren().addAll(calmaJaVai[0]);
        stage.setTitle("Prática Off-01");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

