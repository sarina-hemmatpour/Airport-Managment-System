package com.company.Controller;

import com.company.Main;
import com.company.Model.Airplane;
import com.company.Model.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SAFlightsPlanesPageController implements Initializable {

    @FXML Button closeBTN;
    @FXML Button addPlaneBTN;
    @FXML Label warningLBL;
    @FXML TextField idTF;
    @FXML TextField nmbrOfSeatsTF;
    @FXML TableView planesTV;
    @FXML TableColumn seatsTC;
    @FXML TableColumn idTC;
    @FXML Button editBTN;
    @FXML Button removeBTN;
    @FXML Button flightsBTN;
    @FXML TextField editSeatsTF;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //fillling the table
        idTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        seatsTC.setCellValueFactory(new PropertyValueFactory<>("seats"));
        for (int i=0 ; i<Main.airplanes.size() ; i++)
        {
            planesTV.getItems().add(Main.airplanes.get(i));
        }

        closeBTN.setOnAction(e -> {
            //closing the tab
            RemoveTabInTabPane removeTabInTabPane=new RemoveTabInTabPane();
            int index=SuperAdminPageController.SAPageTPfake.getTabs().indexOf(SuperAdminPageController.flightsPlanesTab);
            removeTabInTabPane.removeTab( SuperAdminPageController.SAPageTPfake, index);


            SuperAdminPageController.planesflightsTabIsOpen=false;
        });

        addPlaneBTN.setOnAction(e -> {
            try {
                addPlaneBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        editBTN.setOnAction(e -> editBTNaction(e));

        removeBTN.setOnAction(e -> {
            try {
                removeBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        flightsBTN.setOnAction(e ->{

            //check if an object is selected
            if (planesTV.getSelectionModel().getSelectedItem()!=null)
            {
                //loading a new stage

                FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("View/SAFlightsPage.fxml"));

                try {
                    fxmlLoader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Stage flightsStage=new Stage();
                flightsStage.setTitle("Flights");
                flightsStage.setScene(new Scene(fxmlLoader.getRoot()));
                flightsStage.show();

                //set id
                SAFlightsPageController saFlightsPageController=fxmlLoader.getController();
                saFlightsPageController.airplaneIndex=Main.airplanes.indexOf(planesTV.getSelectionModel().getSelectedItem());
                System.out.println(saFlightsPageController.airplaneIndex);
                saFlightsPageController.planeIdLBL.setText(Main.airplanes.get(saFlightsPageController.airplaneIndex).getId());

                warningLBL.setText("");

            }
            else
            {
                warningLBL.setText("Select an airplane!!!");
            }


        });

    }

    // ////////////////////////////////////////////////////////////////////////




    //remove a plane
    private void removeBTNaction(ActionEvent e) throws IOException {

        if (planesTV.getSelectionModel().getSelectedItem()==null)
        {
            warningLBL.setText("Select an airplane!!!");
        }
        else {

            int index = Main.airplanes.indexOf(planesTV.getSelectionModel().getSelectedItem());


            //remove in file
//        String flightsInfo="";
//        for (int i=0 ; i<Main.airplanes.get(index).getFlights().size() ; i++)
//        {
//            if (i==Main.airplanes.get(index).getFlights().size()-1)
//            {
//                flightsInfo+=Main.airplanes.get(index).getFlights().get(i).getId();
//            }
//            else
//            {
//                flightsInfo+=Main.airplanes.get(index).getFlights().get(i).getId() + ";";
//            }
//
//        }
//
//        String oldLine=Main.airplanes.get(index).getId()+" "+Main.airplanes.get(index).getSeats()+" "+flightsInfo;
//
//        RemoveLineInFile removeLineInFile=new RemoveLineInFile();
//        removeLineInFile.removeLine("airplanes.txt" , oldLine);


            //remove any flights with this airplane
            for (int i = 0; i < Main.airplanes.get(index).getFlights().size(); i++) {
                for (int j = 0; j < Main.flights.size(); i++) {
                    if (Main.flights.get(j) == Main.airplanes.get(index).getFlights().get(i)) {
                        Main.flights.remove(j);
                        break;
                    }
                }
            }

            //remove flights in file
            WriteReadFile<Flight> flightWriteReadFile = new WriteReadFile<>(Main.flights, "flights.txt");
            flightWriteReadFile.writeList();

            //remove airplane in arraylist
            Main.airplanes.remove(index);

            //remove airplane in file
            WriteReadFile<Airplane> airplaneWriteReadFile = new WriteReadFile<>(Main.airplanes, "airplanes.txt");
            airplaneWriteReadFile.writeList();

            //refilling the table
            planesTV.getItems().clear();

            for (int i = 0; i < Main.airplanes.size(); i++) {
                planesTV.getItems().add(Main.airplanes.get(i));
            }

            //refresh the table
            planesTV.refresh();

        }

    }



    //edit planes (only seats)
    private void editBTNaction(ActionEvent e)
    {
        if (planesTV.getSelectionModel().getSelectedItem()==null)
        {
            warningLBL.setText("Select an airplane!!!");
        }
        else
        {
            if (editSeatsTF.getText().isEmpty())
            {
                warningLBL.setText("fill the field!!!");
            }
            else if (!editSeatsTF.getText().matches("[0-9]+"))
            {
                warningLBL.setText("Invalid format!!!");
            }
            else if (editSeatsTF.getText().matches("[0]+"))
            {
                warningLBL.setText("Number of seats cant be 0!!!");
            }
            else if (editSeatsTF.getText().substring(0,1).equals("0"))
            {
                warningLBL.setText("Remove any zero at the first of your number!!!");
            }
            else
            {
                //every thing is ok

                int index=Main.airplanes.indexOf(planesTV.getSelectionModel().getSelectedItem());

                //save changes in arraylist
                Main.airplanes.get(index).setSeats(editSeatsTF.getText());

                //save changes in file
                WriteReadFile<Airplane> airplaneWriteReadFile=new WriteReadFile<>(Main.airplanes , "airplanes.txt");
                airplaneWriteReadFile.writeList();

                //save changes in file
//
//                String flightsInfo="";
//                for (int i=0 ; i<Main.airplanes.get(index).getFlights().size() ; i++)
//                {
//                    if (i==Main.airplanes.get(index).getFlights().size()-1)
//                    {
//                        flightsInfo+=Main.airplanes.get(index).getFlights().get(i).getId();
//                    }
//                    else
//                    {
//                        flightsInfo+=Main.airplanes.get(index).getFlights().get(i).getId() + ";";
//                    }
//
//                }
//
//                String newLine=Main.airplanes.get(index).getId()+" "+editSeatsTF.getText()+" "+flightsInfo;
//
//                ChangeLineInFile changeLineInFile=new ChangeLineInFile();
//                changeLineInFile.changeALineInATextFile("airplanes.txt" ,newLine , index+1);

                warningLBL.setText("");
                editSeatsTF.setText("");

                //clearing the tableview
                planesTV.getItems().clear();

                //refilling the tableview
                for (int i=0 ; i<Main.airplanes.size() ; i++)
                {
                    planesTV.getItems().add(Main.airplanes.get(i));
                }

                //refresh the table
                planesTV.refresh();
            }
        }

    }





    private void addPlaneBTNaction(ActionEvent e) throws IOException {
        if (idTF.getText().isEmpty() || nmbrOfSeatsTF.getText().isEmpty())
        {
            warningLBL.setText("fill both fields!!!");
        }
        else if (!idTF.getText().matches("[0-9]+") || !nmbrOfSeatsTF.getText().matches("[0-9]+"))
        {
            warningLBL.setText("Invalid format!!!");
        }
        else if (nmbrOfSeatsTF.getText().matches("[0]+"))
        {
            warningLBL.setText("Number of seats cant be 0!!!");
        }
        else if (nmbrOfSeatsTF.getText().substring(0,1).equals("0"))
        {
            warningLBL.setText("Remove any zero at the first of your number!!!");
        }
        else if(checkExistance())
        {
            warningLBL.setText("There's already a plane with this id!!!");
        }
        else
        {
            //everythinh is ok

            //creating an object
            Airplane newPlane=new Airplane(idTF.getText() , nmbrOfSeatsTF.getText());
            Main.airplanes.add(newPlane);

            //saving in file
            WriteReadFile<Airplane> airplaneWriteReadFile=new WriteReadFile<>(Main.airplanes , "airplanes.txt");
            airplaneWriteReadFile.writeList();

            //writing in file
//             BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("airplanes.txt" , true));
//             String toSave=newPlane.getId()+" "+newPlane.getSeats();
//             bufferedWriter.write(toSave);
//             bufferedWriter.newLine();
//             bufferedWriter.close();

             warningLBL.setText("");
             idTF.setText("");
             nmbrOfSeatsTF.setText("");

             planesTV.getItems().add(Main.airplanes.get(Main.airplanes.size()-1));

             planesTV.refresh();
        }
    }

    private boolean checkExistance()
    {
        for (int i=0 ; i<Main.airplanes.size() ; i++)
        {
            if (idTF.getText().equals(Main.airplanes.get(i).getId()))
            {
                return true;
            }
        }
        return false;
    }
}
