package com.company.Controller;

import com.company.Main;
import com.company.Model.Airplane;
import com.company.Model.Flight;
import com.company.Model.Passanger;
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

public class SAFlightPassangersPageController implements Initializable {


    @FXML Label flightIdLBL;
    @FXML Label warningLBL;
    @FXML TableView passangersTV;
    @FXML TableColumn idCLM;
    @FXML TableColumn nameCLM;
    @FXML TableColumn lastnameCLM;
    @FXML TableColumn phoneCLM;
    @FXML TableColumn emailCLM;
    @FXML TableColumn usernameCLM;
    @FXML Button removeBTN;
    @FXML Button closeBTN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //label
        flightIdLBL.setText(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights()
                .get(SAFlightsPageController.passangerFlightIndex).getId());

        //fill the table
        idCLM.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCLM.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastnameCLM.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        phoneCLM.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailCLM.setCellValueFactory(new PropertyValueFactory<>("emailAdress"));
        usernameCLM.setCellValueFactory(new PropertyValueFactory<>("username"));

        for (int i=0 ; i<Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex)
                .getFlights().get(SAFlightsPageController.passangerFlightIndex).getPassangers().size() ; i++)
        {
            passangersTV.getItems().add(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex)
                    .getFlights().get(SAFlightsPageController.passangerFlightIndex).getPassangers().get(i));
        }
        System.out.println("jadval: "+Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex)
                .getFlights().get(SAFlightsPageController.passangerFlightIndex).getPassangers().size());

        closeBTN.setOnAction(e -> {
            ((Stage) closeBTN.getScene().getWindow()).close();

            SAFlightsPageController.passangerStageIsOpen=false;
        });

        removeBTN.setOnAction(e ->removeBTNaction(e));

    }


    private void removeBTNaction(ActionEvent e)
    {


        if (passangersTV.getSelectionModel().getSelectedItem() !=null)
        {
            Passanger passangerToRemove=(Passanger) passangersTV.getSelectionModel().getSelectedItem();
            int index = -1;
            for (int i=0 ; i<Main.passangers.size() ; i++)
            {
                if (Main.passangers.get(i).getId().equals(passangerToRemove.getId()))
                {
                    index=i;
                    break;
                }
            }

            System.out.println(index + "passanger index");

            //rairplane
            for (int k=0 ; k<Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(SAFlightsPageController.removeFlightIndex).getPassangers().size() ; k++)
            {
                if (Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights()
                        .get(SAFlightsPageController.removeFlightIndex).getPassangers().get(k).getId().equals(Main.passangers.get(index).getId()))
                {
                    Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights()
                            .get(SAFlightsPageController.removeFlightIndex).getPassangers().remove(k);

                    String temp="";
                    temp+=(Integer.parseInt(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights()
                            .get(SAFlightsPageController.removeFlightIndex).getNmbrOFsoldTickets())+1);
                    Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights()
                            .get(SAFlightsPageController.removeFlightIndex).setNmbrOFsoldTickets(temp);
                }
            }
            //write in files
            WriteReadFile<Airplane> airplaneWriteReadFile = new WriteReadFile<>(Main.airplanes, "airplanes.txt");
            airplaneWriteReadFile.writeList();



            //remove from flights
            for (int j=0 ; j<Main.flights.get(SAFlightsPageController.removeFlightIndex).getPassangers().size() ; j++)
            {
                if (Main.flights.get(SAFlightsPageController.removeFlightIndex).getPassangers().get(j).getId().equals(Main.passangers.get(index).getId()))
                {
                    Main.flights.get(SAFlightsPageController.removeFlightIndex).getPassangers().remove(j);
                    String temp="";
                    temp+=(Integer.parseInt((Main.flights.get(SAFlightsPageController.removeFlightIndex).getNmbrOFsoldTickets()))+1);
                    Main.flights.get(SAFlightsPageController.removeFlightIndex).setNmbrOFsoldTickets(temp);
                }
            }
            //write infile
            WriteReadFile<Flight> flightWriteReadFile = new WriteReadFile<>(Main.flights, "flights.txt");
            flightWriteReadFile.writeList();


            //remove the flight from the list of flights of this passanger
            //and return the money
            for (int i=0 ; i<Main.passangers.get(index).getBaughtTickets().size() ; i++)
            {
                if (Main.passangers.get(index).getBaughtTickets().get(i).getId().equals(
                        Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(SAFlightsPageController.passangerFlightIndex).getTicket().getId()
                ))
                {
                    //remove the ticket
                    Main.passangers.get(index).getBaughtTickets().remove(i);

                    //returnmoney
                    int oldCredit=Integer.parseInt(passangerToRemove.getCredit());
                    System.out.println(oldCredit+"*old");
                    int returnPrice=Integer.parseInt(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(SAFlightsPageController.passangerFlightIndex).getTicket().getPrice());
                    System.out.println("price" + returnPrice);
                    int newCredit=oldCredit+returnPrice;
                    System.out.println(newCredit+"new" );
                    String tempCredit="";
                    tempCredit+=newCredit;
                    Main.passangers.get(index).setCredit(tempCredit);
                    System.out.println("passangercredit "+Main.passangers.get(index).getCredit());
                }
            }
            //write in file
            WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>( Main.passangers , "Passanger.txt");
            passangerWriteReadFile.writeList();



            //refreshing the tableview

            passangersTV.getItems().clear();

            for (int i=0 ; i<Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex)
                    .getFlights().get(SAFlightsPageController.passangerFlightIndex).getPassangers().size() ; i++)
            {
                passangersTV.getItems().add(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex)
                        .getFlights().get(SAFlightsPageController.passangerFlightIndex).getPassangers().get(i));
            }


            if (LoginPageController.position== LoginPageController.position.Employee)
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.employeeRemovePassangerInFlight(Main.employees.get(LoginPageController.loginUserIndex) , passangerToRemove ,Main.flights.get(SAFlightsPageController.removeFlightIndex) );
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else if (LoginPageController.position== LoginPageController.position.Manager)
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.managerRemovePassangerInFlight(Main.managers.get(LoginPageController.loginUserIndex),passangerToRemove ,
                        Main.flights.get(SAFlightsPageController.removeFlightIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.superAdminRemovePassangerInFlight(passangerToRemove ,
                        Main.flights.get(SAFlightsPageController.removeFlightIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }

            warningLBL.setText("");
        }
        else
        {
            warningLBL.setText("Select a passanger!!!");
        }




    }
}
