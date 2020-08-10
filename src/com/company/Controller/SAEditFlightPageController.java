package com.company.Controller;

import com.company.Main;
import com.company.Model.Airplane;
import com.company.Model.Flight;
import com.company.Model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SAEditFlightPageController implements Initializable {

    @FXML Button cancelBTN;
    @FXML Button editBTN;
    @FXML Label idLBL;
    @FXML TextField originTF;
    @FXML TextField destinationTF;
    @FXML TextField dateTF;
    @FXML TextField timeTF;
    @FXML TextField durationTF;
    @FXML TextField priceTF;
    @FXML TextField fineTF;
    @FXML Label warningLBL;



    private Flight flight=Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(SAFlightsPageController.editFlightIndex);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //filling the form


        originTF.setText(flight.getOrigin());
        destinationTF.setText(flight.getDesination());
        dateTF.setText(flight.getDate());
        timeTF.setText(flight.getTakeOffTime());
        durationTF.setText(flight.getDuration());
        priceTF.setText(flight.getTicket().getPrice());
        fineTF.setText(flight.getTicket().getFine());



        cancelBTN.setOnAction(e -> {
            ((Stage) cancelBTN.getScene().getWindow()).close();

            SAFlightsPageController.editStageIsOpen=false;
        });

        editBTN.setOnAction(e -> edtBTNaction(e));

    }

    private void edtBTNaction(ActionEvent e)
    {
        if (originTF.getText().isEmpty() || destinationTF.getText().isEmpty() || dateTF.getText().isEmpty() ||
            timeTF.getText().isEmpty() || durationTF.getText().isEmpty() || priceTF.getText().isEmpty() || fineTF.getText().isEmpty()  )
        {
            warningLBL.setText("Complete all the fields");
        }
        else if (!destinationTF.getText().matches("[a-z]+") ||
                !originTF.getText().matches("[a-z]+") ||
                !dateTF.getText().matches("[0-9][0-9]/[0-1][1-9]/[0-3][1-9]")||
                !timeTF.getText().matches("[0-2][0-9]:[0-6][0-9]")||
                !priceTF.getText().matches("[1-9][0-9]*") ||
                !fineTF.getText().matches("[0-9]+%"))
        {
            warningLBL.setText("Invalid format!!!");
        }
        else
        {
            warningLBL.setText("");
            SAFlightsPageController.editStageIsOpen=false;

            //editting the object
            flight.setOrigin(originTF.getText());
            flight.setDesination(destinationTF.getText());
            flight.setDate(dateTF.getText());
            flight.setTakeOffTime(timeTF.getText());
            flight.setDuration(durationTF.getText());
            flight.getTicket().setFine(fineTF.getText());
            flight.getTicket().setPrice(priceTF.getText());

            //edditing the file
            WriteReadFile<Ticket> ticketWriteReadFile=new WriteReadFile<>(Main.tickets,"Tickets.txt");
            ticketWriteReadFile.writeList();

            WriteReadFile<Flight> flightWriteReadFile=new WriteReadFile<>(Main.flights,"flights.txt");
            flightWriteReadFile.writeList();

            WriteReadFile<Airplane> airplaneWriteReadFile=new WriteReadFile<>(Main.airplanes,"airplanes.txt");
            airplaneWriteReadFile.writeList();


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

            //close
            ((Stage) editBTN.getScene().getWindow()).close();
            SAFlightsPageController.editStageIsOpen=false;
        }
    }
}
