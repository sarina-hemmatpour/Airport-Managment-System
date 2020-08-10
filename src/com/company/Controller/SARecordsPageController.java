package com.company.Controller;

import com.company.Main;
import com.company.Model.Message;
import com.company.Model.Record;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;



public class SARecordsPageController implements Initializable {

    @FXML
    Button closeBTN;
    @FXML
    TableView recordsTV;
    @FXML
    TableColumn recordCLM;
    @FXML TableColumn timeCLM;
    @FXML Button deleteBTN;
    @FXML
    Label warningLBL;


    static TableView fakeRecordsTV;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fakeRecordsTV=recordsTV;

        //fill the table
        timeCLM.setCellValueFactory(new PropertyValueFactory<>("time"));
        recordCLM.setCellValueFactory(new PropertyValueFactory<>("text"));

        for (int i = 0; i< Main.messages.size() ; i++)
        {
            recordsTV.getItems().add(Main.records.get(i));
        }


        closeBTN.setOnAction(e -> {

            ((Stage)closeBTN.getScene().getWindow()).close();
            SuperAdminPageController.recordPageIsOpen=false;

        });

        deleteBTN.setOnAction(e -> deleteBTNaction(e));

    }

    private void deleteBTNaction(ActionEvent e)
    {
        if (recordsTV.getSelectionModel().getSelectedItem() !=null)
        {
            Record recordToRemove=(Record) recordsTV.getSelectionModel().getSelectedItem();
            int recordIndex=-1;
            //finding the index
            for (int i=0 ; i<Main.records.size() ; i++)
            {
                if (Main.records.get(i).getText().equals(recordToRemove.getText())  &&
                        Main.records.get(i).getTime().equals(recordToRemove.getTime()))
                {
                    recordIndex=i;
                    break;
                }
            }

            //remove in objects
            Main.records.remove(recordIndex);
            //write in file
            WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
            recordWriteReadFile.writeList();

            //refresh the table
            recordsTV.getItems().clear();
            for (int i=0 ; i<Main.records.size() ; i++)
            {
                recordsTV.getItems().add(Main.records.get(i));
            }
            recordsTV.refresh();



            warningLBL.setText("");
        }
        else
        {
            warningLBL.setText("Select a message!!!");
        }
    }

    public static void refreshRecordsTV()
    {
        SARecordsPageController.fakeRecordsTV.getItems().clear();
        for (int i=0 ; i<Main.records.size() ; i++)
        {
            SARecordsPageController.fakeRecordsTV.getItems().add(Main.records.get(i));
        }
        SARecordsPageController.fakeRecordsTV.refresh();
    }
}
