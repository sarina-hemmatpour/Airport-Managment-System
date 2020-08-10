package com.company.Controller;

import com.company.Main;
import com.company.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.nio.file.LinkOption;
import java.util.ResourceBundle;

public class PassangerFlightPageController implements Initializable {

    @FXML TableColumn originCLM;
    @FXML TableColumn destinationCLM;
    @FXML TableColumn dateCLM;
    @FXML TableColumn timeCLM;
    @FXML TableView flightsTV;

//    @FXML TableView priceTV;
    @FXML TableColumn priceCLM;
    @FXML TableColumn fineCLM;
    @FXML TableColumn statusCLM;

    @FXML Button exitBTN;
    @FXML Button cancelBTN;
    @FXML
    Label warningLBL;

    static TableView fakeFlightTV;
//    static TableView fakePriceTV;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fakeFlightTV=flightsTV;
//        fakePriceTV=priceTV;

        //filling tableview
        originCLM.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationCLM.setCellValueFactory(new PropertyValueFactory<>("desination"));
        dateCLM.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCLM.setCellValueFactory(new PropertyValueFactory<>("takeOffTime"));
        statusCLM.setCellValueFactory(new PropertyValueFactory<>("flightStatus"));
        priceCLM.setCellValueFactory(new PropertyValueFactory<>("flightPrice"));
        fineCLM.setCellValueFactory(new PropertyValueFactory<>("flightFine"));


//        priceCLM.setCellValueFactory(new PropertyValueFactory<>("price"));
//        fineCLM.setCellValueFactory(new PropertyValueFactory<>("fine"));

        for (int i=0 ; i< Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().size() ; i++)
        {
            for (int j=0 ; j<Main.flights.size() ; j++)
            {
                if (Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().get(i).getId()
                        .equals(Main.flights.get(j).getId()))
                {
                    flightsTV.getItems().add(Main.flights.get(j));
//                    priceTV.getItems().add(Main.flights.get(j).getTicket());
                }
            }
        }



        exitBTN.setOnAction(e -> {
            //remove tab
            RemoveTabInTabPane removeTabInTabPane=new RemoveTabInTabPane();
            int index=PassangerPageController.passangerPageTPfake.getTabs().indexOf(PassangerPageController.flightsTab);
            removeTabInTabPane.removeTab( PassangerPageController.passangerPageTPfake, index);

            PassangerPageController.flightsTabIsOpen=false;
        });

        cancelBTN.setOnAction(e -> cancelBTNaction(e));


    }


    private void cancelBTNaction(ActionEvent e)
    {
        if (flightsTV.getSelectionModel().getSelectedItem() != null)
        {
            Flight flightToRemove=(Flight) flightsTV.getSelectionModel().getSelectedItem();

            if (flightToRemove.getFlightStatus()== Flight.Status.notDONE)
            {
                int flightIdex=-1;

                Ticket ticketToRemove=flightToRemove.getTicket();
                int ticketIndex=-1;

                Airplane airplaneToRemove=flightToRemove.getAirplane();
                int airplaneIndex=-1;

                //ticket index in passenger baught ticket


                for (int i=0 ; i<Main.flights.size() ; i++)
                {
                    if (Main.flights.get(i).getId().equals(flightToRemove.getId()))
                    {
                        flightIdex=i;
                        break;
                    }
                }


                for (int i=0 ; i<Main.airplanes.size() ; i++)
                {
                    if (Main.airplanes.get(i).getId().equals(airplaneToRemove.getId()))
                    {
                        airplaneIndex=i;
                        break;
                    }
                }

                //remove the ticket from the ticketslist in passanger
                for (int i=0 ; i<Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().size() ; i++)
                {
                    if (Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().get(i).getId()
                            .equals(ticketToRemove.getId()))
                    {
                        Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().remove(i);
                        break;
                    }
                }


                //raise credit (fine)
                float fine=Integer.parseInt(ticketToRemove.getPrice().substring(0,ticketToRemove.getPrice().length()-1));
                float kasr=(100-fine)/100;
                int price= (int) (Integer.parseInt(ticketToRemove.getPrice())*(kasr));
                int oldCredit=Integer.parseInt(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
                int newCredit=price+oldCredit;
                String tempCredit="";
                tempCredit+=newCredit;
                Main.passangers.get(LoginPageController.loginUserIndex).setCredit(tempCredit);
                //set the credit label
                PassangerPageController.creditLBLfake.setText(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());

                //write in file
                WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>(Main.passangers , "Passanger.txt");
                passangerWriteReadFile.writeList();


                //edit flights
                //remove the passanger from the list

                for (int i=0 ; i<Main.flights.get(flightIdex).getPassangers().size() ; i++)
                {
                    if (Main.flights.get(flightIdex).getPassangers().get(i).getId().equals(
                            Main.passangers.get(LoginPageController.loginUserIndex).getId()
                    ))
                    {
                        Main.flights.get(flightIdex).getPassangers().remove(i);
                        break;
                    }
                }
                //raise number of sold tickets
                String tempNmbr="";
                tempNmbr+=(Integer.parseInt(Main.flights.get(flightIdex).getNmbrOFsoldTickets())+1);
                Main.flights.get(flightIdex).setNmbrOFsoldTickets(tempNmbr);
                //write in file
                WriteReadFile<Flight> flightWriteReadFile = new WriteReadFile<>(Main.flights, "flights.txt");
                flightWriteReadFile.writeList();


                //remove the passanger in airplane
                for (int i=0 ; i<Main.airplanes.get(airplaneIndex).getFlights().size() ; i++)
                {
                    if (Main.airplanes.get(airplaneIndex).getFlights().get(i).getId().equals(
                            flightToRemove.getId()
                    ))
                    {
                        for (int j=0 ; j<Main.airplanes.get(airplaneIndex).getFlights().get(i).getPassangers().size() ; j++)
                        {
                            if (Main.airplanes.get(airplaneIndex).getFlights().get(i).getPassangers().get(j).getId().equals(
                                    Main.passangers.get(LoginPageController.loginUserIndex).getId()
                            ))
                            {
                                Main.airplanes.get(airplaneIndex).getFlights().get(i).getPassangers().remove(j);
                                break;
                            }
                        }


                        break;
                    }
                }
                //write in file
                WriteReadFile<Airplane> airplaneWriteReadFile = new WriteReadFile<>(Main.airplanes, "airplanes.txt");
                airplaneWriteReadFile.writeList();


                //refresh the tableview
                flightsTV.getItems().clear();
//                priceTV.getItems().clear();

                for (int i=0 ; i< Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().size() ; i++)
                {
                    for (int j=0 ; j<Main.flights.size() ; j++)
                    {
                        if (Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().get(i).getId()
                                .equals(Main.flights.get(j).getId()))
                        {
                            flightsTV.getItems().add(Main.flights.get(j));
//                            priceTV.getItems().add(Main.flights.get(j).getTicket());
                        }
                    }
                }

                flightsTV.refresh();
//                priceTV.refresh();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.passangerCancelTicket(Main.passangers.get(LoginPageController.loginUserIndex) , Main.flights.get(flightIdex).getTicket());
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();

                warningLBL.setText("");
            }
            else
            {
                warningLBL.setText("This flight is already Done!!!");
            }





        }
        else
        {
            warningLBL.setText("Select a flight!!!");
        }


    }

}
