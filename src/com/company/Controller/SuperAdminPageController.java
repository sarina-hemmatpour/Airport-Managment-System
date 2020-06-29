package com.company.Controller;

import com.company.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        exitBTN.setOnAction(e -> {
            ((Stage)exitBTN.getScene().getWindow()).close();
            Main.loginPageStage.show();
        });


    }
}
