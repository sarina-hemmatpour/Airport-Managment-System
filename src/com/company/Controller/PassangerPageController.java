package com.company.Controller;

import com.company.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PassangerPageController implements Initializable {

    @FXML Label infoLBL;
    @FXML Label creditLBL;
    @FXML Label warningLBL;
    @FXML Button exitBTN;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        infoLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getName()+
                " "+Main.passangers.get(LoginPageController.loginUserIndex).getLastname());
        creditLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getCredit());

        exitBTN.setOnAction(e -> {
            ((Stage)exitBTN.getScene().getWindow()).close();
            Main.loginPageStage.show();
        });

    }
}
