package com.company.Controller;

import com.company.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageShapeController implements Initializable {

    @FXML Label senderLBL;
    @FXML Label timeLBL;
    @FXML TextArea massageTA;
    @FXML AnchorPane pmRoot;


    public int index;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    public void fill()
    {
        senderLBL.setText(Main.messages.get(index).getSender());
        timeLBL.setText(Main.messages.get(index).getTime());
        massageTA.setText(Main.messages.get(index).getText());
    }
}
