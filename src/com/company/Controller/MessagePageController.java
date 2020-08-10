package com.company.Controller;

import com.company.Main;
import com.company.Model.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MessagePageController implements Initializable
{

    @FXML Button cancelBTN;
    @FXML Button sendBTN;
    @FXML TextArea messageTA;

    public String sender;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cancelBTN.setOnAction(e ->{
            ((Stage)cancelBTN.getScene().getWindow()).close();
            PassangerPageController.messagePageIsOpen=false;
        });

        sendBTN.setOnAction(e -> {
            try {
                sendBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void sendBTNaction(ActionEvent e) throws IOException {
        if (! messageTA.getText().isEmpty())
        {
            Message newMassage=new Message(messageTA.getText() , sender);

            Main.messages.add(newMassage);

            //save message in file
            WriteReadFile<Message> messageWriteReadFile=new WriteReadFile<>(Main.messages , "Messages.txt");
            messageWriteReadFile.writeList();

            //save message in file
//            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("Messages.txt" , true));
//
//            String toSave=(newMassage.getTime()+";"+newMassage.getText());
//
//            bufferedWriter.write(toSave);
//            bufferedWriter.newLine();
//            bufferedWriter.close();

            ((Stage)cancelBTN.getScene().getWindow()).close();
            PassangerPageController.messagePageIsOpen=false;
        }
    }
}
