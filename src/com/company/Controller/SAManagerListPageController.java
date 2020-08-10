package com.company.Controller;

import com.company.Main;
import com.company.Model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SAManagerListPageController implements Initializable {


    @FXML Button closeBTN;
    @FXML Button editBTN;
    @FXML
    Label warningLBL;
    @FXML
    TableView managersTV;
    @FXML
    TableColumn idCLM;
    @FXML TableColumn nameCLM;
    @FXML TableColumn lastnameCLM;
    @FXML TableColumn phoneCLM;
    @FXML TableColumn emailCLM;
    @FXML TableColumn usernameCLM;
    @FXML Button removeBTN;


    public static int editManagerIndex;
    public static Stage editManagerStage;
    public static boolean editManagerPageIsOpen=false;

    public static TableView fakeManagersTV;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fakeManagersTV=managersTV;

        //fill the table view
        idCLM.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCLM.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastnameCLM.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        phoneCLM.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailCLM.setCellValueFactory(new PropertyValueFactory<>("emailAdress"));
        usernameCLM.setCellValueFactory(new PropertyValueFactory<>("username"));

        for (int i=0 ; i< Main.managers.size() ; i++)
        {
            managersTV.getItems().add(Main.managers.get(i));
        }

        closeBTN.setOnAction(e -> {

            SARegisterManagerPageController.managerListPageIsOpen=false;
            ((Stage)closeBTN.getScene().getWindow()).close();

            //close edit page
            if (editManagerPageIsOpen)
            {
                editManagerStage.close();
                editManagerPageIsOpen=false;
            }

        });


        removeBTN.setOnAction(e -> removeBTNaction(e));

        editBTN.setOnAction(e -> {

            if (managersTV.getSelectionModel().getSelectedItem() != null)
            {
                Manager managerToEdit=(Manager) managersTV.getSelectionModel().getSelectedItem();
                editManagerIndex=-1;
                //find it
                for (int i=0 ; i<Main.managers.size() ; i++)
                {
                    if (Main.managers.get(i).getId().equals(managerToEdit.getId()))
                    {
                        editManagerIndex=i;
                        break;
                    }
                }

                //load the page
                FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/SAManagerEditPage.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                editManagerStage=new Stage(StageStyle.UNDECORATED);
                editManagerStage.setScene(new Scene(loader.getRoot()));
                editManagerStage.show();


                editManagerPageIsOpen=true;
                warningLBL.setText("");
            }
            else
            {
                warningLBL.setText("Select a manager!!!");
            }


        });

    }

    private void removeBTNaction(ActionEvent e)
    {
        if (managersTV.getSelectionModel().getSelectedItem() != null)
        {
            Manager managerToRemove=(Manager) managersTV.getSelectionModel().getSelectedItem();
            int managerIndex=-1;
            //find the index
            for (int i=0 ; i<Main.managers.size() ; i++)
            {
                if (Main.managers.get(i).getId().equals(managerToRemove.getId()))
                {
                    managerIndex=i;
                    break;
                }
            }

            //remove in objects
            Main.managers.remove(managerIndex);
            //write in file
            WriteReadFile<Manager> managerWriteReadFile=new WriteReadFile<>(Main.managers, "Managers.txt");
            managerWriteReadFile.writeList();

            //refresh the table
            managersTV.getItems().clear();
            for (int i=0 ; i< Main.managers.size() ; i++)
            {
                managersTV.getItems().add(Main.managers.get(i));
            }
            managersTV.refresh();


            warningLBL.setText("");
        }
        else
        {
            warningLBL.setText("Select a manager!!!");
        }
    }
}
