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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PassangerPageController implements Initializable {

    @FXML Label infoLBL;
    @FXML Label creditLBL;
    @FXML Label warningLBL;
    @FXML Button exitBTN;
    @FXML TableView flightsTV;
    @FXML Button profileBTN;
    @FXML Button raiseCreditBTN;
    @FXML TextField raiseCreditTF;
    @FXML TabPane passangerPageTP;
    @FXML Button messageBTN;
    @FXML Button buyBTN;
    @FXML Button myTicketsBTN;

    //tableview part
    @FXML TableColumn originCLM;
    @FXML TableColumn destinationCLM;
    @FXML TableColumn dateCLM;
    @FXML TableColumn timeCLM;

//    @FXML TableView priceTV;
    @FXML TableColumn priceCLM;

    static boolean prifileTabIsOpen=false;
    static boolean messagePageIsOpen=false;
    static boolean flightsTabIsOpen=false;


    static Tab profileTab;
    static Tab flightsTab;

    public static TabPane passangerPageTPfake;

    public static Label creditLBLfake;

    Stage messageStage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        passangerPageTPfake=passangerPageTP;


        creditLBLfake=creditLBL;

        infoLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getName()+
                " "+Main.passangers.get(LoginPageController.loginUserIndex).getLastname());
        creditLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());

        //filling tableview
        originCLM.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationCLM.setCellValueFactory(new PropertyValueFactory<>("desination"));
        dateCLM.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCLM.setCellValueFactory(new PropertyValueFactory<>("takeOffTime"));
        priceCLM.setCellValueFactory(new PropertyValueFactory<>("flightPrice"));


        System.out.println("size:"+Main.flights.size());
        for (int i=0 ; i<Main.flights.size() ; i++)
        {
            if (Main.flights.get(i).getFlightStatus()== Flight.Status.notDONE)
            {
                flightsTV.getItems().add(Main.flights.get(i));
            }

        }



//        for (int i=0 ; i<Main.flights.size() ; i++)
//        {
//            if (Main.flights.get(i).getFlightStatus()== Flight.Status.notDONE)
//            {
//                priceTV.getItems().add(Main.flights.get(i).getTicket());
//            }
//
//        }


        //exit Button
        exitBTN.setOnAction(e -> {

            if (messagePageIsOpen)
            {
                messageStage.close();
            }

            prifileTabIsOpen=false;
            messagePageIsOpen=false;
            ((Stage)exitBTN.getScene().getWindow()).close();
            Main.loginPageStage.show();
        });

        //raise credit button
        raiseCreditBTN.setOnAction(e -> {
            try {
                raiseCreditBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //Profile button
        profileBTN.setOnAction(e -> {
            if (!prifileTabIsOpen)
            {
                FXMLLoader pEditProfLoader=new FXMLLoader(Main.class.getResource("View/PassangerEditProfilePage.fxml"));

                try {
                    pEditProfLoader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                profileTab=new Tab("Profile");
                profileTab.setStyle("-fx-background-color: slategray");
                profileTab.setContent(pEditProfLoader.getRoot());
                passangerPageTP.getTabs().add(profileTab);

                prifileTabIsOpen=true;
            }


        });

        myTicketsBTN.setOnAction(e -> {

            if (!flightsTabIsOpen)
            {
                FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/PassangerFlightPage.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                flightsTab=new Tab("Flights");
                flightsTab.setStyle("-fx-background-color: slategray");
                flightsTab.setContent(loader.getRoot());
                passangerPageTP.getTabs().add(flightsTab);

                flightsTabIsOpen=true;
            }


        });

        //messege button
        messageBTN.setOnAction(e ->{

            if (!messagePageIsOpen)
            {
                FXMLLoader messageLoader=new FXMLLoader(Main.class.getResource("View/MessagePage.fxml"));

                try {
                    messageLoader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                MessagePageController messagePageController=messageLoader.getController();
                messagePageController.sender="Passanger";

                messageStage=new Stage(StageStyle.UNDECORATED);
                messageStage.setScene(new Scene(messageLoader.getRoot()));
                messageStage.show();

                messagePageIsOpen=true;
            }

        });


        buyBTN.setOnAction(e ->buyBTNaction(e));


    }


    private void buyBTNaction(ActionEvent e)
    {
        if ( flightsTV.getSelectionModel().getSelectedItem() != null)
        {
            Flight flight=(Flight) flightsTV.getSelectionModel().getSelectedItem();
            int indexInFlights=-1;
            int indexInAirplane=-1;
            int indexOfAirplane=-1;

            for (int i=0 ; i<Main.airplanes.size() ; i++)
            {
                if (Main.airplanes.get(i).getId().equals(flight.getAirplane().getId()))
                {
                    indexOfAirplane=i;
                    break;
                }
            }

            for (int i=0 ; i<Main.airplanes.get(indexOfAirplane).getFlights().size() ; i++)
            {
                if (Main.airplanes.get(indexOfAirplane).getFlights().get(i).getId().equals(flight.getId()))
                {
                    indexInAirplane=i;
                    break;
                }
            }

            for (int i=0 ; i<Main.flights.size() ; i++)
            {
                if (Main.flights.get(i).getId().equals(flight.getId()))
                {
                    indexInFlights=i;
                    break;
                }
            }
            int maxValue=Integer.parseInt(flight.getAirplane().getSeats())-Integer.parseInt(flight.getNmbrOFsoldTickets());
            if (flight.getFlightStatus()== Flight.Status.DONE)
            {
                warningLBL.setText("This flight is already Done!!!");
            }
            else if (maxValue==0)
            {
                //its full
                warningLBL.setText("Sorry, full capacity!!!");
            }
            else if (checkInterfere((Flight) flightsTV.getSelectionModel().getSelectedItem()))
            {
                warningLBL.setText("This flight interferes with your other Flights");
            }
            else if (Integer.parseInt(flight.getTicket().getPrice()) >
                    Integer.parseInt(Main.passangers.get(LoginPageController.loginUserIndex).getCredit()))
            {
                //low money
                warningLBL.setText("Your credit is not enough!!!");
            }
            else
            {

                //every thing is ok
                //lets buy

                //pay for the ticket
                //decrease credit
                int oldCredit=Integer.parseInt(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
                int flightPrice=Integer.parseInt(flight.getTicket().getPrice());
                int newCredit=oldCredit-flightPrice;
                String tempCredit="";
                tempCredit+=newCredit;
                Main.passangers.get(LoginPageController.loginUserIndex).setCredit(tempCredit);


                //edit passangers ticket
                Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().add(flight.getTicket());
                //edit in file
                WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>( Main.passangers , "Passanger.txt");
                passangerWriteReadFile.writeList();

                for (int i=0 ; i<Main.airplanes.get(indexOfAirplane).getFlights().get(indexInAirplane).getPassangers().size() ; i++)
                {
                    System.out.println("before adding to the flight: " +
                            Main.airplanes.get(indexOfAirplane).getFlights().get(indexInAirplane).getPassangers().get(i).getName());
                }

                //edit the flight

                Main.flights.get(indexInFlights).getPassangers().add(Main.passangers.get(LoginPageController.loginUserIndex));

                String tempSold="";
                int tempInt=Integer.parseInt(Main.flights.get(indexInFlights).getNmbrOFsoldTickets())+1;
                tempSold+=tempInt;
                Main.flights.get(indexInFlights).setNmbrOFsoldTickets(tempSold);
                //write in file
                WriteReadFile<Flight> flightWriteReadFile = new WriteReadFile<>(Main.flights, "flights.txt");
                flightWriteReadFile.writeList();



                //edit in airplanes
                for (int i=0 ; i<Main.airplanes.get(indexOfAirplane).getFlights().get(indexInAirplane).getPassangers().size() ; i++)
                {
                    System.out.println("before: " +
                            Main.airplanes.get(indexOfAirplane).getFlights().get(indexInAirplane).getPassangers().get(i).getName());
                }


                if (Main.flights.get(indexInFlights)!=Main.airplanes.get(indexOfAirplane).getFlights().get(indexInAirplane))
                {
                    Main.airplanes.get(indexOfAirplane).getFlights().get(indexInAirplane).getPassangers()
                            .add(Main.passangers.get(LoginPageController.loginUserIndex));

                    Main.airplanes.get(indexOfAirplane).getFlights().get(indexInAirplane).setNmbrOFsoldTickets(tempSold);
                }

                //edit in file
                WriteReadFile<Airplane> airplaneWriteReadFile = new WriteReadFile<>(Main.airplanes, "airplanes.txt");
                airplaneWriteReadFile.writeList();

                creditLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());


                warningLBL.setText("Done!!!");


                //refresh the table
                if (flightsTabIsOpen)
                {
                    PassangerFlightPageController.fakeFlightTV.getItems().clear();

//                    PassangerFlightPageController.fakePriceTV.getItems().clear();

                    for (int i=0 ; i< Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().size() ; i++)
                    {
                        for (int j=0 ; j<Main.flights.size() ; j++)
                        {
                            if (Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().get(i).getId()
                                    .equals(Main.flights.get(j).getId()))
                            {
                                PassangerFlightPageController.fakeFlightTV.getItems().add(Main.flights.get(j));
//                                PassangerFlightPageController.fakePriceTV.getItems().add(Main.flights.get(j).getTicket());
                            }
                        }
                    }

                    PassangerFlightPageController.fakeFlightTV.refresh();
//                    PassangerFlightPageController.fakePriceTV.refresh();



                    //record
                    Record record=new Record();
                    Main.records.add(record);
                    record.passangerBuyTicket(Main.passangers.get(LoginPageController.loginUserIndex),Main.flights.get(indexInFlights).getTicket());
                    //write in file
                    WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                    recordWriteReadFile.writeList();

                }
            }
        }
        else
        {
            warningLBL.setText("Select a flight!!!");
        }
    }




    private void raiseCreditBTNaction(ActionEvent e) throws IOException {
        if (raiseCreditTF.getText().isEmpty())
        {
            warningLBL.setText("Write how much cash you want to add to your credit!!!");
        }
        else
        {
            if (!(raiseCreditTF.getText().matches("[0-9]+")))
            {
                warningLBL.setText("Invalid format: Use numbers!!!");
            }
            else
            {
                int cash=Integer.parseInt(raiseCreditTF.getText());
                cash+=Integer.parseInt((Main.passangers.get(LoginPageController.loginUserIndex).getCredit()));
                String add="";
                add+=cash;

                //edit file
//                String oldline=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "+
//                        Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
//
//                String newLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
//                        +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "
//                        +add);

                //find the line
//                int lineToBeEdited=0;
//                FileReader fileReader=new FileReader("Passanger.txt");
//                BufferedReader bufferedReader=new BufferedReader(fileReader);
//
//                String line;
//                while ((line=bufferedReader.readLine()) != null)
//                {
//                    lineToBeEdited++;
//                    if (line==oldline)
//                    {
//                        break;
//                    }
//                }
//
//                String file="Passanger.txt";
//                ChangeLineInFile changeFile=new ChangeLineInFile();
//
//
//                changeFile.changeALineInATextFile(file,newLine,lineToBeEdited);


                //edit the object
                Main.passangers.get(LoginPageController.loginUserIndex).setCredit(add);
                creditLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
                warningLBL.setText("");
                raiseCreditTF.setText("");

                //edit file
                WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>(Main.passangers , "Passanger.txt");
                passangerWriteReadFile.writeList();


                Record record=new Record();
                Main.records.add(record);
                record.passangerRaiseCredit(Main.passangers.get(LoginPageController.loginUserIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
        }
    }



    private boolean checkInterfere(Flight choosenFlight)
    {
        for (int i=0 ; i<Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().size() ; i++)
        {
            //find the flight time for each ticket
            int flightIndex=-1;
            for (int j=0 ; j<Main.flights.size() ; j++)
            {
                if (Main.flights.get(j).getId().equals(Main.passangers.get(LoginPageController.loginUserIndex).getBaughtTickets().get(i).getId()))
                {
                    flightIndex=j;
                }
            }
            System.out.println("flightindex: "+flightIndex);


            if (Main.flights.get(flightIndex).getId().equals(choosenFlight.getId()))
            {
                return false;
            }
            else
            {
                if (!Main.flights.get(flightIndex).getDate().equals(choosenFlight.getDate()))
                {
                    return false;
                }
                else
                {
                    if (!Main.flights.get(flightIndex).getTakeOffTime().equals(choosenFlight.getTakeOffTime()))
                    {
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
