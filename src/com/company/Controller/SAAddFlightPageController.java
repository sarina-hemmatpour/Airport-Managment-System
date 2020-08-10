package com.company.Controller;

import com.company.Main;
import com.company.Model.Airplane;
import com.company.Model.Flight;
import com.company.Model.Record;
import com.company.Model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SAAddFlightPageController implements Initializable {

    @FXML Button cancelBTN;
    @FXML Button addBTN;
    @FXML TextField idTF;
    @FXML TextField originTF;
    @FXML TextField destinationTF;
    @FXML TextField dateTF;
    @FXML TextField timeTF;
    @FXML TextField durationTF;
    @FXML TextField priceTF;
    @FXML TextField fineTF;
    @FXML Label warningLBL;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        warningLBL.setText("");


        cancelBTN.setOnAction(e -> {
            ((Stage) cancelBTN.getScene().getWindow()).close();

            SAFlightsPageController.addStageIsOpen=false;
        });

        addBTN.setOnAction( e -> addBTNaction(e));

    }

    private void addBTNaction(ActionEvent e)
    {
        if (idTF.getText().isEmpty() || originTF.getText().isEmpty() || destinationTF.getText().isEmpty() || timeTF.getText().isEmpty()
            || durationTF.getText().isEmpty() || dateTF.getText().isEmpty() ||priceTF.getText().isEmpty() ||fineTF.getText().isEmpty() )
        {
            warningLBL.setText("Complete all the fields!!!");
        }
        else if (!idTF.getText().matches("[0-9]+") ||
                !destinationTF.getText().matches("[a-z]+") ||
                !originTF.getText().matches("[a-z]+") ||
                !dateTF.getText().matches("\\d\\d/\\d\\d/\\d\\d")||
                !timeTF.getText().matches("[0-2][0-9]:[0-6][0-9]")||
                !priceTF.getText().matches("[1-9][0-9]*") ||
                !fineTF.getText().matches("[0-9]+%"))
        {
            warningLBL.setText("Invalid format!!!");
        }
        else if (!checkTime() || !checkDate())
        {
            warningLBL.setText("Invalid date and time!!!");
        }
        else if (checkInterfere())
        {
            warningLBL.setText("Interferes with other flights!!!");
        }
        else
        {
            warningLBL.setText("");

            SAFlightsPageController.addStageIsOpen=false;

            String id="";
            id+=Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getId();
            id+="-";
            id+=idTF.getText();

            Ticket newTicket=new Ticket(id , priceTF.getText() , fineTF.getText());
            //add to objects
            Main.tickets.add(newTicket);
            //add to file
            WriteReadFile<Ticket> ticketWriteReadFile=new WriteReadFile<>(Main.tickets,"Tickets.txt");
            ticketWriteReadFile.writeList();


            Flight newFlight=new Flight(id , Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex) ,
                    newTicket , originTF.getText() , destinationTF.getText() , dateTF.getText() , durationTF.getText(),
                    timeTF.getText());
            //add to the objects
            Main.flights.add(newFlight);
            //add to the file
            WriteReadFile<Flight> flightWriteReadFile=new WriteReadFile<>(Main.flights,"flights.txt");
            flightWriteReadFile.writeList();

            //add to the airplane
            Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().add(newFlight);
            //add to the file
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
//            //refreshing the price tableview
//            for (int i=0 ; i<Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().size() ; i++)
//            {
//                SAFlightsPageController.fakePriceTV.getItems().add(Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(i).getTicket());
//            }
//            SAFlightsPageController.fakePriceTV.refresh();

            //close
            ((Stage) addBTN.getScene().getWindow()).close();
            SAFlightsPageController.addStageIsOpen=false;

            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.superAdminAddFlight(newFlight);
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else if (LoginPageController.position== LoginPageController.position.Manager)
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.managerAddFlight(Main.managers.get(LoginPageController.loginUserIndex) , newFlight);
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else
            {
                //record
                Record record=new Record();
                Main.records.add(record);
                record.employeeAddFlight(Main.employees.get(LoginPageController.loginUserIndex) ,newFlight);
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }

        }
    }

    private boolean checkTime()
    {

        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();
        //joda krdane time haye now
        String nowString=dtf.format(now);
        String[] date_clockNow=nowString.split(" ");

        String[] dateNow=date_clockNow[0].split("/");
        String[] clockNow=date_clockNow[1].split(":");
        //cast into integer
        int yearNow=Integer.parseInt(dateNow[0])%1000;
        int monthNow=Integer.parseInt(dateNow[1]);
        int dayNow=Integer.parseInt(dateNow[2]);

        int hourNow=Integer.parseInt(clockNow[0]);
        int miniuteNow=Integer.parseInt(clockNow[1]);

        //joda kardane flight time ha

        String[] date=dateTF.getText().split("/");
        String[] clock=timeTF.getText().split(":");
        //cast into integer
        int year=Integer.parseInt(date[0]);
        int month=Integer.parseInt(date[1]);
        int day=Integer.parseInt(date[2]);

        int hour=Integer.parseInt(clock[0]);
        int miniute=Integer.parseInt(clock[1]);


        //check
        if (yearNow>year)
        {
            return false;
        }else if (year==yearNow)
        {
            if (monthNow>month)
            {
                return false;
            }else if (monthNow==month)
            {
                if (day<dayNow)
                {
                    return false;
                }else if (day==dayNow)
                {
                    if(hour<hourNow)
                    {
                        return false;
                    }else if (hour==hourNow)
                    {
                        if (miniute<=miniuteNow)
                        {
                            return false;
                        }
                    }
                }

            }
        }

        return true;


    }

    private boolean checkInterfere()
    {
        for (int i=0 ; i<Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().size() ; i++)
        {
            if ( ! Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(i).getDate()
                    .equals(dateTF.getText()))
            {
                return false;
            }
            else
            {
                if ( ! Main.airplanes.get(SAFlightsPlanesPageController.airplaneIndex).getFlights().get(i).getTakeOffTime()
                        .equals(timeTF.getText()))
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }


        }

        return false;
    }

    private boolean checkDate()
    {
        String[] date=dateTF.getText().split("/");
        String[] clock=timeTF.getText().split(":");
        //cast into integer
        int month=Integer.parseInt(date[1]);
        int day=Integer.parseInt(date[2]);

        int hour=Integer.parseInt(clock[0]);
        int miniute=Integer.parseInt(clock[1]);

        if (!(month>=1 && month<=12) || ! (day>=1 && day<=31))
        {
            return false;
        }
        if (!(hour>=0 && hour<=23) || !(miniute>=0 && miniute<=59))
        {
            return false;
        }
        return true;

    }
}
