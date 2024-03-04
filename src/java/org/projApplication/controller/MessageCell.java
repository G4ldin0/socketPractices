package org.projApplication.controller;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.projApplication.process.PacketInfo;

import java.util.List;

public class MessageCell extends ListCell<PacketInfo> {
    HBox parent = new HBox();
    Text from = new Text();
    Text message = new Text();
    public MessageCell(){
        parent.getChildren().addAll(from, message);
    }

    @Override
    protected void updateItem(PacketInfo msg, boolean b) {
        super.updateItem(msg, b);

        if(msg != null && !b){
        from.setText(msg.getSource().toString());

        message.setText(msg.getMessage());
        if(msg.isBroadCast())
            message.setStyle("-fx-text-fill: red; -fx-alignment: left");
        setGraphic(parent);
}

    }


}
