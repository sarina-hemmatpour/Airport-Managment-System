package com.company.Controller;

import com.company.Main;
import com.company.Model.Airplane;
import com.company.Model.Flight;
import com.company.Model.Passanger;
import com.company.Model.Record;
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

import javax.print.attribute.standard.MediaSize;
import java.io.IOException;
import java.net.URL;
import java.time.chrono.MinguoChronology;
import java.util.ResourceBundle;

public class SAPassangersPageController implements Initializable {


    @FXML Label warningLBL;
    @FXML TableView passangersTV;
    @FXML TableColumn idCLM;
    @FXML TableColumn nameCLM;
    @FXML TableColumn lastnameCLM;
    @FXML TableColumn phoneCLM;
    @FXML TableColumn emailCLM;
    @FXML TableColumn usernameCLM;
    @FXML Button removeBTN;
    @FXML Button editBTN;
    @FXML Button closeBTN;

    public static boolean editPageIsOpen=false;
    public static Stage editStage;

    public static int editPassangerIndex;

    public static TableView fakePassangerTV;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fakePassangerTV=passangersTV;

        //fill the table
        idCLM.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCLM.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastnameCLM.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        phoneCLM.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailCLM.setCellValueFactory(new PropertyValueFactory<>("emailAdress"));
        usernameCLM.setCellValueFactory(new PropertyValueFactory<>("username"));

        for (int i=0 ; i< Main.passangers.size() ; i++)
        {
            passangersTV.getItems().add(Main.passangers.get(i));
        }

        removeBTN.setOnAction(e -> removeBTNaction(e));

        closeBTN.setOnAction(e ->
        {
            SuperAdminPageController.passangersPageIsOpen=false;
            ((Stage)closeBTN.getScene().getWindow()).close();

            if (editPageIsOpen)
            {
                editPageIsOpen=false;
                editStage.close();
            }
        });


        editBTN.setOnAction(e -> editBTNaction(e));

    }

    private void editBTNaction(ActionEvent e)
    {
        if (passangersTV.getSelectionModel().getSelectedItem() !=null)
        {

            Passanger passanger=(Passanger) passangersTV.getSelectionModel().getSelectedItem();

            for (int i=0 ; i<Main.passangers.size() ; i++)
            {
                if (Main.passangers.get(i).getId().equals(passanger.getId()))
                {
                    editPassangerIndex=i;
                }
            }




            //loading
            FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/SAPassangerEditPage.fxml"));

            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            editStage=new Stage(StageStyle.UNDECORATED);
            editStage.setScene(new Scene(loader.getRoot()));
            editStage.show();

            editPageIsOpen=true;
            warningLBL.setText("");
        }
        else
        {
            warningLBL.setText("Select a passanger!!!");
        }
    }

    private void removeBTNaction(ActionEvent e)
    {

        if (passangersTV.getSelectionModel().getSelectedItem() !=null)
        {
            Passanger passangerToRemove=(Passanger) passangersTV.getSelectionModel().getSelectedItem();



            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.superAdminRemovePassanger(passangerToRemove);
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.managerRemovePassanger(Main.managers.get(LoginPageController.loginUserIndex) , passangerToRemove);
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }





            //remove from airplane lists
            for (int i=0 ; i<Main.airplanes.size() ; i++)
            {
                for (int j=0 ; j<Main.airplanes.get(i).getFlights().size() ; j++)
                {
                    for (int k=0 ; k<Main.airplanes.get(i).getFlights().get(j).getPassangers().size() ; k++)
                    {
                        if (Main.airplanes.get(i).getFlights().get(j).getPassangers().get(k).equals(passangerToRemove))
                        {
                            Main.airplanes.get(i).getFlights().get(j).getPassangers().remove(k);
                            String temp="";
                            temp+=(Integer.parseInt(Main.airplanes.get(i).getFlights().get(j).getNmbrOFsoldTickets())+1);
                            Main.airplanes.get(i).getFlights().get(j).setNmbrOFsoldTickets(temp);
                        }
                    }
                }
            }
            //write in files
            WriteReadFile<Airplane> airplaneWriteReadFile = new WriteReadFile<>(Main.airplanes, "airplanes.txt");
            airplaneWriteReadFile.writeList();


            //remove from the flights
            for (int i=0 ; i<Main.flights.size() ; i++)
            {
                for (int j=0 ; j<Main.flights.get(i).getPassangers().size() ; j++)
                {
                    if (Main.flights.get(i).getPassangers().get(j).equals(passangerToRemove))
                    {
                        Main.flights.get(i).getPassangers().remove(j);
                        String temp="";
                        temp+=(Integer.parseInt(Main.flights.get(i).getNmbrOFsoldTickets())+1);
                        Main.flights.get(i).setNmbrOFsoldTickets(temp);
                    }
                }
            }
            //write infile
            WriteReadFile<Flight> flightWriteReadFile = new WriteReadFile<>(Main.flights, "flights.txt");
            flightWriteReadFile.writeList();


            //remove from the list of passangers
            Main.passangers.remove(passangerToRemove);
            //write in file
            WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>( Main.passangers , "Passanger.txt");
            passangerWriteReadFile.writeList();

            //refresh the table
            passangersTV.getItems().clear();
            for (int i=0 ; i< Main.passangers.size() ; i++)
            {
                passangersTV.getItems().add(Main.passangers.get(i));
            }


            warningLBL.setText("");
        }
        else
        {
            warningLBL.setText("Select a passanger!!!");
        }


    }
}
