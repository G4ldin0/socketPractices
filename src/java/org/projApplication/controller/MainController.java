package org.projApplication.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import org.projApplication.process.PacketInfo;
import org.projApplication.process.Pair;
import org.projApplication.process.ProcessUnit;
import org.projApplication.process.TypeMessage;
import org.projApplication.view.MainApplication;


import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class MainController implements Initializable {
    @FXML
    TextField text;
    @FXML
    TextField id;
    @FXML
    ChoiceBox<String> typeMessage;
    @FXML
    ListView<PacketInfo> chatMessages;

    ProcessUnit instanceProcessUnit;
    private final String[] msgTypes = {"Unicast", "BroadCast"};

    public MainController(ProcessUnit processUnit)
    {
        instanceProcessUnit = processUnit;
    }

    @FXML
    protected void getValues()
    {
        if(!text.getCharacters().isEmpty()) {

            TypeMessage t = id.isDisabled() ? TypeMessage.BROADCAST : TypeMessage.UNICAST;

            byte destiny = ProcessUnit.getID();
            if (t.equals(TypeMessage.UNICAST)) destiny = Byte.parseByte(id.getCharacters().toString());

            String msg = text.getCharacters().toString();


            PacketInfo pkg = new PacketInfo(t, ProcessUnit.getAddress(), destiny, msg);
            ProcessUnit.sendMessage(pkg);


            text.clear();
            chatMessages.scrollTo(chatMessages.getItems().size());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chatMessages.setItems(ProcessUnit.log());
        chatMessages.setCellFactory(type -> new MessageCell());

        typeMessage.getItems().addAll(msgTypes);

        typeMessage.setValue(msgTypes[1]);
        id.setText(String.valueOf(ProcessUnit.getAddresses().get(0).r().getAddress()[3]));

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

    public Label[] createUsers(){
        Vector<Label> ArrayConstructor = new Vector<>();

        byte myId = ProcessUnit.getID();

        int count = 0;
        for(int i = 1; i <= 4; i++) {
            if(i != myId){
                Label newLabel = new Label("Cliente " + i);
                newLabel.setId(String.valueOf( 1 << ( i - 1 )));
                newLabel.setLayoutY(15.0 + 30.0 * count);
                newLabel.setLayoutX(15.0);
                newLabel.setPrefWidth(170.0);
                newLabel.getStyleClass().add("Button");
                newLabel.setCursor(Cursor.HAND);
                newLabel.setOnMouseClicked(event -> {
                            if (event.getButton().equals(MouseButton.PRIMARY)) {
                                typeMessage.setValue(msgTypes[0]);
                                id.setText(((Node)event.getSource()).getId());
                            }
                        }
                );
                ArrayConstructor.add(newLabel);
                count++;
            }
        }

        Label[] returnArray = new Label[ArrayConstructor.size()];
        ArrayConstructor.toArray(returnArray);
        return returnArray;
    }


}
