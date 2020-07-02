package com.company.Controller;

import com.company.Main;
import com.company.Model.Airplane;
import com.company.Model.ChangeLineInFile;
import com.company.Model.Flight;
import com.company.Model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

    //tableview part
    @FXML TableColumn<Flight,String> originCLM;
    @FXML TableColumn<Flight,String> destinationCLM;
    @FXML TableColumn<Flight,String> dateCLM;
    @FXML TableColumn<Flight,String> timeCLM;
    @FXML TableColumn<Ticket,String> priceCLM;
    @FXML TableColumn<Airplane,String> seatsCLM;
    @FXML TableColumn ticketsCLM;

    static boolean prifileTabIsOpen=false;

    private Tab profileTab;

    private static TabPane passangerPageTPfake;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        passangerPageTPfake=passangerPageTP;


        infoLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getName()+
                " "+Main.passangers.get(LoginPageController.loginUserIndex).getLastname());
        creditLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
//        System.out.println(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());

        //filling tableview
//        originCLM.setCellValueFactory(new PropertyValueFactory<>("origin"));
//        destinationCLM.setCellValueFactory(new PropertyValueFactory<>("destination"));
//        dateCLM.setCellValueFactory(new PropertyValueFactory<>("date"));
//        timeCLM.setCellValueFactory(new PropertyValueFactory<>("time"));
//        priceCLM.setCellValueFactory(new PropertyValueFactory<>("price"));
//        seatsCLM.setCellValueFactory(new PropertyValueFactory<>("seats"));


        //exit Button
        exitBTN.setOnAction(e -> {
            prifileTabIsOpen=false;
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

        //messege button
        messageBTN.setOnAction(e ->{

        });



    }

    public static void closeTab(int i)
    {
        passangerPageTPfake.getTabs().remove(i);
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
                String oldline=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "+
                        Main.passangers.get(LoginPageController.loginUserIndex).getCredit());

                String newLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
                        +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "
                        +add);

                //find the line
                int lineToBeEdited=0;
                FileReader fileReader=new FileReader("Passanger.txt");
                BufferedReader bufferedReader=new BufferedReader(fileReader);

                String line;
                while ((line=bufferedReader.readLine()) != null)
                {
                    lineToBeEdited++;
                    if (line==oldline)
                    {
                        break;
                    }
                }

                String file="Passanger.txt";
                ChangeLineInFile changeFile=new ChangeLineInFile();


                changeFile.changeALineInATextFile(file,newLine,lineToBeEdited);


                //edit the object
                Main.passangers.get(LoginPageController.loginUserIndex).setCredit(add);
                creditLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
                warningLBL.setText("");
            }
        }
    }

    private void fillTableView()
    {

    }
}
