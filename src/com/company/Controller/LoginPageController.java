package com.company.Controller;
import java.io.FileReader;
import java.io.FileReader.*;
import com.company.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable
{

    @FXML TextField usernameTF;
    @FXML PasswordField passwordTF;
    @FXML Button loginBTN;
    @FXML Button registerBTN;
    @FXML Label warningLBL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginBTN.setOnAction(e -> {
            try {
                loginBTNaction(e);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }


    public void loginBTNaction(ActionEvent e) throws FileNotFoundException {
        if (usernameTF.getText().equals("") || passwordTF.getText().equals(""))
        {
            warningLBL.setText("Complete all the fields!!!");
        }
        else
        {
            //checking users
            ///////////////////////
            //super admin:
            if (usernameTF.getText().equals(Main.superAdmin.getUsername()))
            {
                //username exists
                if (passwordTF.getText().equals(Main.superAdmin.getPassword()))
                {
                    //password is correct
                    warningLBL.setText("");
                    //loding Super admin page
                    //***********************************************
                }
                else
                {
                    //passsword is not correct
                    warningLBL.setText("Incorrect password!!!");
                }
            }
            else
            {
                //manager:

                boolean managerIsFound=false;
                for (int i=0 ; i<Main.managers.size() ; i++)
                {
                    if (usernameTF.getText().equals(Main.managers.get(i).getUsername()))
                    {
                        //username is correct
                        if (passwordTF.getText().equals(Main.managers.get(i).getPassword()))
                        {
                            //password is correct
                            //loading manager page
                            //***********************************************
                            managerIsFound=true;
                            break;
                        }
                        else
                        {
                            //passsword is not correct
                            warningLBL.setText("Incorrect password!!!");
                            managerIsFound=true;
                            break;
                        }
                    }
                }

                //employee:
                boolean employeeIsFound=false;
                if (!managerIsFound)
                {
                    for (int i=0 ; i<Main.employees.size() ; i++)
                    {
                        if (usernameTF.getText().equals(Main.employees.get(i).getUsername()))
                        {
                            //username is correct
                            if (passwordTF.getText().equals(Main.employees.get(i).getPassword()))
                            {
                                //password is correct
                                //loading employee page
                                //***********************************************
                                employeeIsFound=true;
                                break;
                            }
                            else
                            {
                                //passsword is not correct
                                warningLBL.setText("Incorrect password!!!");
                                employeeIsFound=true;
                                break;
                            }
                        }
                    }
                }

                //pasanger:
                boolean passangerIsFound=false;
                if (!managerIsFound && !employeeIsFound)
                {
                    for (int i=0 ; i<Main.passangers.size() ; i++)
                    {
                        if (usernameTF.getText().equals(Main.passangers.get(i).getUsername()))
                        {
                            //username is correct
                            if (passwordTF.getText().equals(Main.passangers.get(i).getPassword()))
                            {
                                //password is correct
                                //loading passanger page
                                //***********************************************
                                passangerIsFound=true;
                                break;
                            }
                            else
                            {
                                //passsword is not correct
                                warningLBL.setText("Incorrect password!!!");
                                passangerIsFound=true;
                                break;
                            }
                        }
                    }
                }

                if (!managerIsFound && !employeeIsFound && !passangerIsFound)
                {
                    //no user with this username
                    warningLBL.setText("Incorrect username!!!");
                }

            }
        }
    }



}
