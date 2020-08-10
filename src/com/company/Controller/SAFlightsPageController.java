package com.company.Controller;

import com.company.Main;
import com.company.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SAFlightsPageController implements Initializable
{

    @FXML Label planeIdLBL;
    @FXML Label warningLBL;
    @FXML TableView flightsTV;
    @FXML TableColumn idCLM;
    @FXML TableColumn originCLM;
    @FXML TableColumn destinationCLM;
    @FXML TableColumn dateCLM;
    @FXML TableColumn timeCLM;
    @FXML TableColumn durationCLM;
    @FXML TableColumn ticketNumberCLM;
    @FXML TableColumn statusCLM;
    @FXML Button addBTN;
    @FXML Button editBTN;
    @FXML Button removeBTN;
    @FXML Button closeBTN;
    @FXML Button passangersBTN;

//    @FXML TableView priceTV;
    @FXML TableColumn priceCLM;


    public static int removeFlightIndex;
    public static int editFlightIndex;
    public static int passangerFlightIndex;


    static boolean addStageIsOpen=false;
    public static Stage addStage;

    static boolean editStageIsOpen=false;
    public static Stage editStage;

    static boolean passangerStageIsOpen=false;
    public static Stage passangerStage;

    public  static TableView fakeFlightsTV;
//    public  static TableView fakePriceTV;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        fakeFlightsTV=flightsTV;
//        fakePriceTV=priceTV;


        //fillling the table
        idCLM.setCellValueFactory(new PropertyValueFactory<>("id"));
        originCLM.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationCLM.setCellValueFactory(new PropertyValueFactory<>("desination"));
        dateCLM.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCLM.setCellValueFactory(new PropertyValueFactory<>("takeOffTime"));
        durationCLM.setCellValueFactory(new PropertyValueFactory<>("duration"));
        ticketNumberCLM.setCellValueFactory(new PropertyValueFactory<>("nmbrOFsoldTickets"));
        statusCLM.setCellValueFactory(new PropertyValueFactory<>("flightStatus"));
        priceCLM.setCellValueFactory(new PropertyValueFactory<>("flightPrice"));

        for (int i = 0; i< Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().size() ; i++)
        {
            flightsTV.getItems().add(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(i));
        }

        //filling the table for price


//        for (int i=0 ; i<Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().size() ; i++)
//        {
//            priceTV.getItems().add(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(i).getTicket());
//        }

        closeBTN.setOnAction(e -> {
            ((Stage) closeBTN.getScene().getWindow()).close();


            SAFlightsPlanesPageController.flightStageIsOpen=false;

            if (addStageIsOpen )
            {
                addStage.close();
                addStageIsOpen=false;

            }
            if (editStageIsOpen)
            {
                editStage.close();
                editStageIsOpen=false;
            }
            if (passangerStageIsOpen)
            {
                passangerStage.close();
                passangerStageIsOpen=false;
            }
        });

        addBTN.setOnAction(e -> {

            if (!addStageIsOpen)
            {
                //loading add a flight page
                FXMLLoader addloader=new FXMLLoader(Main.class.getResource("View/SAAddFlightPage.fxml"));

                try {
                    addloader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                addStage=new Stage(StageStyle.UNDECORATED);

                addStage.setScene(new Scene(addloader.getRoot()));
                addStage.show();

                addStage=addStage;


                addStageIsOpen=true;
            }

        });


        editBTN.setOnAction(e -> {

            if (!editStageIsOpen)
            {
                if (flightsTV.getSelectionModel().getSelectedItem()!=null)
                {
                    editFlightIndex=Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().indexOf(flightsTV.getSelectionModel().getSelectedItem());
                    //deit page loading
                    FXMLLoader editLoader=new FXMLLoader(Main.class.getResource("View/SAEditFlightPage.fxml"));
                    try {
                        editLoader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    //set id
                    SAEditFlightPageController editFlightPageController=editLoader.getController();
                    editFlightPageController.idLBL.setText(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(editFlightIndex).getId());


                    editStage=new Stage(StageStyle.UNDECORATED);
                    editStage.setScene(new Scene(editLoader.getRoot()));
                    editStage.show();

                    editStageIsOpen=true;
                    warningLBL.setText("");
                }
                else
                {
                    warningLBL.setText("Select a flight!!!");
                }
            }


        });

        removeBTN.setOnAction(e -> removeBTNaction(e));


        passangersBTN.setOnAction( e -> {

            if (!passangerStageIsOpen)
            {
                if (flightsTV.getSelectionModel().getSelectedItem() != null)
                {
                    passangerFlightIndex=Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().indexOf(flightsTV.getSelectionModel().getSelectedItem());

                    //loading
                    FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/SAFlightPassangers.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    passangerStage=new Stage(StageStyle.UNDECORATED);
                    passangerStage.setScene(new Scene(loader.getRoot()));
                    passangerStage.show();


                    warningLBL.setText("");
                    passangerStageIsOpen=true;
                }
                else
                {
                    warningLBL.setText("Select a flight!!!");
                }
            }
        });

    }


    private void removeBTNaction(ActionEvent e)
    {
        if (flightsTV.getSelectionModel().getSelectedItem()!=null)
        {
//            removeFlightIndex=Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().indexOf(flightsTV.getSelectionModel().getSelectedItem());
//            Flight flightToRemove=Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(removeFlightIndex);


            Flight selectedFlight=(Flight) flightsTV.getSelectionModel().getSelectedItem();
            int indexInFlights=-1;
            int indexInAirplane=-1;
            int ticketIndex=-1;

            for (int i=0 ; i<Main.tickets.size() ; i++)
            {
                if (Main.tickets.get(i).getId().equals(selectedFlight.getId()))
                {
                    ticketIndex=i;
                    break;
                }
            }

            for (int i=0 ; i<Main.flights.size() ; i++)
            {
                if (Main.flights.get(i).getId().equals(selectedFlight.getId()))
                {
                    indexInFlights=i;
                    break;
                }
            }

            for (int i=0 ; i<Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().size() ; i++)
            {
                if (Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(i).getId()
                    .equals(selectedFlight.getId()))
                {
                    indexInAirplane=i;
                    break;
                }
            }

            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.superAdminRemoveFlight(Main.flights.get(indexInFlights));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else if (LoginPageController.position== LoginPageController.position.Manager)
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.managerRemoveFlight(Main.managers.get(LoginPageController.loginUserIndex) , Main.flights.get(indexInFlights));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.employeeRemoveFlight(Main.employees.get(LoginPageController.loginUserIndex ), Main.flights.get(indexInFlights));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }

            //remove tickets for this flight in passangers and raise credit
            for (int i=0 ; i<Main.passangers.size() ; i++)
            {
                for (int j=0 ; j<Main.passangers.get(i).getBaughtTickets().size() ; j++)
                {
                    if (Main.passangers.get(i).getBaughtTickets().get(j).getId().equals(selectedFlight.getTicket().getId()))
                    {
                        //raise credit
                        int oldCredit=Integer.parseInt(Main.passangers.get(i).getCredit());
                        int returnPrice=Integer.parseInt(selectedFlight.getTicket().getPrice());
                        int newCredit=oldCredit+returnPrice;
                        String tempCredit="";
                        tempCredit+=newCredit;
                        Main.passangers.get(i).setCredit(tempCredit);


                        //remove the tickect
                        Main.passangers.get(i).getBaughtTickets().remove(j);
                    }
                }
            }
            //wrie in file
            WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>( Main.passangers , "Passanger.txt");
            passangerWriteReadFile.writeList();


            //remove th eticket of this flight fot tickets
            Main.tickets.remove(ticketIndex);
            //write in file
            WriteReadFile<Ticket> ticketWriteReadFile=new WriteReadFile<>(Main.tickets , "Tickets.txt");
            ticketWriteReadFile.writeList();

            //remove the flight for the airplane
            Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().remove(indexInAirplane);
            //write in file
            WriteReadFile<Airplane> airplaneWriteReadFile = new WriteReadFile<>(Main.airplanes, "airplanes.txt");
            airplaneWriteReadFile.writeList();

            //remove the flight in flights
            Main.flights.remove(indexInFlights);
            //write in file
            WriteReadFile<Flight> flightWriteReadFile = new WriteReadFile<>(Main.flights, "flights.txt");
            flightWriteReadFile.writeList();


            //refreshing the flights and price tableview
            //refresh the table view
            SAFlightsPageController.fakeFlightsTV.getItems().clear();
//            SAFlightsPageController.fakePriceTV.getItems().clear();


            for (int i = 0; i< Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().size() ; i++)
            {
                SAFlightsPageController.fakeFlightsTV.getItems().add(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(i));
            }

            SAFlightsPageController.fakeFlightsTV.refresh();
            //refreshing the price tableview
//            for (int i=0 ; i<Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().size() ; i++)
//            {
//                SAFlightsPageController.fakePriceTV.getItems().add(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(i).getTicket());
//            }
//            SAFlightsPageController.fakePriceTV.refresh();


            warningLBL.setText("");




        }
        else
        {
            warningLBL.setText("Select a flight!!!");
        }
    }
}
