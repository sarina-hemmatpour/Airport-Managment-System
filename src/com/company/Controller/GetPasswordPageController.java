package com.company.Controller;

import com.company.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GetPasswordPageController implements Initializable
{
    @FXML
    TextField idTF;
    @FXML
    Button cancelBTN;
    @FXML
    Button okBTN;
    @FXML Label passwordTFF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        cancelBTN.setOnAction(e -> {

            ((Stage)cancelBTN.getScene().getWindow()).close();
        });

        okBTN.setOnAction(e -> okBTNaction(e));


    }

    private void okBTNaction(ActionEvent e)
    {
        if (!(idTF.getText().isEmpty()))
        {
            //superadmin
            boolean isSuperAdmin=false;
            if (idTF.getText().equals(Main.superAdmin.getId()))
            {
                passwordTFF.setText("Your password is "+Main.superAdmin.getPassword());
                isSuperAdmin=true;
            }

            //manager
            boolean isManager=false;
            if (!isSuperAdmin)
            {
                for (int i=0 ; i<Main.managers.size() ; i++)
                {
                    if (idTF.getText().equals(Main.managers.get(i).getId()))
                    {
                        passwordTFF.setText("Your password is "+Main.managers.get(i).getPassword());
                        isManager=true;
                        break;
                    }
                }
            }

            //employee
            boolean isEmloyee=false;
            if (!isSuperAdmin && !isManager)
            {
                for (int i=0 ; i<Main.employees.size() ; i++)
                {
                    if (idTF.getText().equals(Main.employees.get(i).getId()))
                    {
                        passwordTFF.setText("Your password is "+Main.employees.get(i).getPassword());
                        isEmloyee=true;
                        break;
                    }
                }
            }

            //passanger
            boolean isPassanger=false;
            if (!isSuperAdmin && !isManager && !isEmloyee)
            {
                for (int i=0 ; i<Main.passangers.size() ; i++)
                {
                    if (idTF.getText().equals(Main.passangers.get(i).getId()))
                    {
                        passwordTFF.setText("Your password is "+Main.passangers.get(i).getPassword());
                        isPassanger=true;
                        break;
                    }
                }
            }

            if (!isSuperAdmin && !isManager && !isEmloyee && !isPassanger)
            {
                passwordTFF.setText("No user found!!!");
            }

        }
    }

}
