package org.projApplication.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;


import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    TextField text;
    @FXML
    TextField id;
    @FXML
    ChoiceBox<String> typeMessage;
    @FXML
    ListView<String> chatMessages;

    ObservableList<String> logMessages;
    private final String[] msgTypes = {"Unicast", "BroadCast"};

    public MainController(String msg)
    {
        logMessages = FXCollections.observableArrayList(msg);
    }

    @FXML
    protected void getValues()
    {
        String newMsg = typeMessage.getValue() + (id.isDisabled() ? " " : " " + id.getCharacters() + " ") + text.getCharacters();
        logMessages.add(newMsg);
        System.out.println(newMsg);
        text.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chatMessages.setItems(logMessages);

        for(String e : msgTypes) typeMessage.getItems().add(e);

        typeMessage.setValue(msgTypes[1]);

        typeMessage.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                id.setDisable((int)observableValue.getValue() != 0);
                text.clear();
            }
        });
    }

    public void handleKeyboardInput(KeyEvent e){
        switch(e.getCode())
        {
            case ESCAPE:
                Platform.exit();
                break;
            case ENTER:
                getValues();
            default:
                break;
        }
    }

    public Label[] createUsers()
    {
        Label[] calma = new Label[4];

        for(int i = 0; i < 4; ++i) {
            final int idNUm = i + 1;
            calma[i] = new Label("Cliente " + (i + 1));
            calma[i].setLayoutY(15.0 + 30.0*(i));
            calma[i].setLayoutX(15.0);
            calma[i].setPrefWidth(170.0);
            calma[i].setCursor(Cursor.HAND);
            calma[i].setOnMouseClicked( e -> {
                    if(e.getButton().equals(MouseButton.PRIMARY)) {
                        typeMessage.setValue(msgTypes[0]);
                        id.setText(String.valueOf(idNUm));
                    }
                }
            );
        }

//        enderecos.toArray(calma);
        return calma;
    }


}
