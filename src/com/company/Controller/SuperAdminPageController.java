package com.company.Controller;

import com.company.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SuperAdminPageController implements Initializable
{

    @FXML Button profileBTN;
    @FXML Button massegeBTN;
    @FXML Button exitBTN;
    @FXML Button managerBTN;
    @FXML Button employeeBTN;
    @FXML Button pfBTN;
    @FXML Button recordBTN;
    @FXML Button passangerBTN;
    @FXML Button financialUnitBTN;
    @FXML Label infoLBL;
    @FXML Label positionLBL;
    @FXML TabPane superAdminTP;

    static boolean planesflightsTabIsOpen=false;

    private static TabPane pfPageTPfake;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pfPageTPfake=superAdminTP;

        infoLBL.setText(Main.superAdmin.getName() + " " + Main.superAdmin.getLastname());
        positionLBL.setText("Super Admin");

        //exit button
        exitBTN.setOnAction(e -> {
            planesflightsTabIsOpen=false;


            ((Stage)exitBTN.getScene().getWindow()).close();
            Main.loginPageStage.show();
        });

        //planes and flights button
        pfBTN.setOnAction(e -> {

            if (!planesflightsTabIsOpen)
            {
                FXMLLoader pfLoader=new FXMLLoader(Main.class.getResource("View/SAFlightsPlanesPage.fxml"));

                try {
                    pfLoader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Tab flightsPlanesTab=new Tab("Planes");
                flightsPlanesTab.setStyle("-fx-background-color : EE82EE");
                flightsPlanesTab.setContent(pfLoader.getRoot());
                superAdminTP.getTabs().add(flightsPlanesTab);

                planesflightsTabIsOpen=true;
            }



        });



    }


    public static void closeTab(int i)
    {
        pfPageTPfake.getTabs().remove(i);
    }
}


