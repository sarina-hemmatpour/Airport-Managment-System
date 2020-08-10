package com.company.Controller;

import com.company.Main;
import com.company.Model.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageChatPageController implements Initializable {

//    @FXML VBox chatVbox;

    @FXML Button closeBTN;
    @FXML TableView messagesTV;
    @FXML TableColumn messageCLM;
    @FXML TableColumn timeCLM;
    @FXML TableColumn senderCLM;
    @FXML Button deleteBTN;
    @FXML Label warningLBL;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


//        FXMLLoader[] messageShapeLoader= new FXMLLoader[Main.messages.size()];
//        for (int i=0 ; i< Main.messages.size() ; i++)
//        {
//            //creating a message shape for each message
//            messageShapeLoader[i]=new FXMLLoader(Main.class.getResource("View/MessageShape.fxml"));
//
//            MessageShapeController messageShapeController=messageShapeLoader[i].getController();
//            messageShapeController.index=i;
//            messageShapeController.fill();
//
//            try {
//                messageShapeLoader[i].load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            chatVbox.getChildren().add(messageShapeLoader[i].getRoot());

//          }


        // /////////////////////////////////////////////////////////

        //fill the table
        timeCLM.setCellValueFactory(new PropertyValueFactory<>("time"));
        messageCLM.setCellValueFactory(new PropertyValueFactory<>("text"));
        senderCLM.setCellValueFactory(new PropertyValueFactory<>("sender"));

        for (int i=0 ; i<Main.messages.size() ; i++)
        {
            messagesTV.getItems().add(Main.messages.get(i));
        }

        closeBTN.setOnAction(e -> {

            ((Stage)closeBTN.getScene().getWindow()).close();

            SuperAdminPageController.messageChatStageIsOpen=false;
        });

        deleteBTN.setOnAction(e -> deleteBTNaction(e));

    }

    private void deleteBTNaction(ActionEvent e)
    {
        if (messagesTV.getSelectionModel().getSelectedItem() !=null)
        {
            Message messageToRemove=(Message) messagesTV.getSelectionModel().getSelectedItem();
            int messageIndex=-1;
            //finding the index
            for (int i=0 ; i<Main.messages.size() ; i++)
            {
                if (Main.messages.get(i).getText().equals(messageToRemove.getText())  &&
                        Main.messages.get(i).getSender().equals(messageToRemove.getSender()))
                {
                    messageIndex=i;
                    break;
                }
            }

            //remove in objects
            Main.messages.remove(messageIndex);
            //write in file
            WriteReadFile<Message> messageWriteReadFile=new WriteReadFile<>(Main.messages , "Messages.txt");
            messageWriteReadFile.writeList();

            //refresh the table
            messagesTV.getItems().clear();
            for (int i=0 ; i<Main.messages.size() ; i++)
            {
                messagesTV.getItems().add(Main.messages.get(i));
            }
            messagesTV.refresh();



            warningLBL.setText("");
        }
        else
        {
            warningLBL.setText("Select a message!!!");
        }
    }
}
