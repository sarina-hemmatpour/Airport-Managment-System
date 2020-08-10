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

    public enum position{SuperAdmin,Manager,Employee}
    public static position position;


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
                forgotPasswordBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void forgotPasswordBTNaction(ActionEvent e) throws IOException {


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

                    SuperAdminPageController controller=sapLoader.getController();
                    controller.infoLBL.setText(Main.superAdmin.getName() + " " + Main.superAdmin.getLastname());
                    controller.positionLBL.setText("Super Admin");



                    Stage superAdminStage=new Stage(StageStyle.UNDECORATED);
                    superAdminStage.setScene(new Scene(sapLoader.getRoot()));
                    superAdminStage.show();
                    position= LoginPageController.position.SuperAdmin;

                    usernameTF.setText("");
                    passwordTF.setText("");
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
                            loginUserIndex=i;

                            FXMLLoader Loader=new FXMLLoader(Main.class.getResource("View/SuperAdminPage.fxml"));
                            try {
                                Loader.load();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            SuperAdminPageController controller=Loader.getController();
                            controller.infoLBL.setText(Main.managers.get(loginUserIndex).getName() + " " + Main.managers.get(loginUserIndex).getLastname());
                            controller.positionLBL.setText("Manager");



                            Stage stage=new Stage(StageStyle.UNDECORATED);
                            stage.setScene(new Scene(Loader.getRoot()));
                            stage.show();

                            position= LoginPageController.position.Manager;

                            usernameTF.setText("");
                            passwordTF.setText("");
                            ((Stage)loginBTN.getScene().getWindow()).close();
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

                                FXMLLoader Loader=new FXMLLoader(Main.class.getResource("View/SuperAdminPage.fxml"));
                                try {
                                    Loader.load();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                SuperAdminPageController controller=Loader.getController();
                                controller.infoLBL.setText(Main.employees.get(loginUserIndex).getName() + " " + Main.employees.get(loginUserIndex).getLastname());
                                controller.positionLBL.setText("Employee");



                                Stage stage=new Stage(StageStyle.UNDECORATED);
                                stage.setScene(new Scene(Loader.getRoot()));
                                stage.show();

                                position= LoginPageController.position.Employee;

                                ((Stage)loginBTN.getScene().getWindow() ).close();
                                usernameTF.setText("");
                                passwordTF.setText("");
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
                                usernameTF.setText("");
                                passwordTF.setText("");
                                ((Stage)loginBTN.getScene().getWindow()).close();
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
