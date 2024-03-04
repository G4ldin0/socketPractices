package org.projApplication.controller;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.projApplication.process.PacketInfo;

import java.util.List;

public class MessageCell extends ListCell<PacketInfo> {
    HBox parent = new HBox();
    Label from = new Label();
    Label message = new Label();
    public MessageCell(){
        parent.getChildren().addAll(from, message);
    }

    @Override
    protected void updateItem(PacketInfo msg, boolean b) {

        if(msg != null && !b){
            from.setText("From " + String.valueOf(msg.getSource().getAddress()[3]) + " : ");
            from.prefWidth(100.0);

            from.getStyleClass().add("MessageCell");

            message.setText(msg.getMessage());
            message.minWidth(parent.getMaxWidth());
            message.getStyleClass().add("MessageCell");

            setGraphic(parent);
        }

        super.updateItem(msg, b);


    }


}
