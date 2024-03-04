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
import org.projApplication.process.ProcessUnit;

public class MainApplication extends Application {
    protected static String msg;

    public static ProcessUnit instanceProcessUnit;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));

        final Label[][] UserLabels = new Label[1][1];

        final String[] instancia = {msg};

        fxmlLoader.setControllerFactory(type -> {
            try {
                Constructor<?> constructor = type.getConstructor(ProcessUnit.class);
                MainController instance = (MainController) constructor.newInstance(instanceProcessUnit);
                stage.addEventHandler(KeyEvent.KEY_PRESSED, instance::handleKeyboardInput);
                UserLabels[0] = instance.createUsers();
                System.out.println(UserLabels[0][0].getText());

                return instance;
            } catch (Exception exc) {
                // fatal...
                throw new RuntimeException(exc);
            }
        });


        Scene scene = fxmlLoader.load();

        var UserPane = ((HBox)scene.getRoot()).getChildren();

        //TODO:tirar esse for daq...
        for(Label e : UserLabels[0])
            ((AnchorPane)UserPane.get(1)).getChildren().add(e);


        stage.setTitle("Prática Off-01");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    //TODO:Testar se isso aq funciona
    @Override
    public void stop() throws Exception {
        super.stop();
        ProcessUnit.end();
    }

    public static void main(String[] args) {
        launch();
    }
}

