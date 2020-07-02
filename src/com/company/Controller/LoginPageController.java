package com.company.Controller;
import java.io.*;
import java.io.FileReader.*;
import com.company.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable
{

    @FXML TextField usernameTF;
    @FXML PasswordField passwordTF;
    @FXML Button loginBTN;
    @FXML Button registerBTN;
    @FXML Label warningLBL;
    @FXML Button forgotPasswordBTN;

    public static int loginUserIndex;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginBTN.setOnAction(e -> {
            try {
                loginBTNaction(e);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        registerBTN.setOnAction(e -> {
            try {
                registerBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        forgotPasswordBTN.setOnAction(e -> {
            try {
                setForgotPasswordBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setForgotPasswordBTNaction(ActionEvent e) throws IOException {


        FXMLLoader fppLoader=new FXMLLoader(Main.class.getResource("View/getPasswordPage.fxml"));


        fppLoader.load();

        Stage fppStage=new Stage(StageStyle.UNDECORATED);
        fppStage.setScene(new Scene(fppLoader.getRoot()));
        fppStage.show();
    }

    private void registerBTNaction(ActionEvent e) throws IOException {
        //loading register page:
        FXMLLoader registerPageLoader=new FXMLLoader(Main.class.getResource("View/RegisterPage.fxml"));

        registerPageLoader.load();

        Stage registerStage =new Stage(StageStyle.UNDECORATED);
        registerStage.setTitle("Register");
        registerStage.setScene(new Scene(registerPageLoader.getRoot()));
        registerStage.show();

    }

    private void loginBTNaction(ActionEvent e) throws FileNotFoundException {
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
                    ((Stage)loginBTN.getScene().getWindow()).close();

                    FXMLLoader sapLoader=new FXMLLoader(Main.class.getResource("View/SuperAdminPage.fxml"));
                    try {
                        sapLoader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    Stage superAdminStage=new Stage(StageStyle.UNDECORATED);
                    superAdminStage.setScene(new Scene(sapLoader.getRoot()));
                    superAdminStage.show();
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
                            loginUserIndex=i;
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
                                loginUserIndex=i;
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
                                ((Stage)loginBTN.getScene().getWindow()).close();
                                loginUserIndex=i;

                                FXMLLoader passangerLoader=new FXMLLoader(Main.class.getResource("View/PassangerPage.fxml"));

                                try {
                                    passangerLoader.load();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                Stage passangerStage=new Stage(StageStyle.UNDECORATED);
                                passangerStage.setScene(new Scene(passangerLoader.getRoot()));
                                passangerStage.show();


                                System.out.println(loginUserIndex);
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
