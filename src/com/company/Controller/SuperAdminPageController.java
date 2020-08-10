package com.company.Controller;

import com.company.Main;
import com.company.Model.Flight;
import com.company.Model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML Label accessLBL;

    static Tab flightsPlanesTab;
    static Tab profileTab;
    static Tab managerRegisterTab;
    static Tab employeeRegisterTab;

    static boolean planesflightsTabIsOpen=false;
    static boolean prifileTabIsOpen=false;
    static boolean passangersPageIsOpen=false;
    static boolean managerRegisterTabIsOpen=false;
    static boolean employeeRegisterTabIsOpen=false;
    static boolean messageChatStageIsOpen=false;
    static boolean messagePageIsOpen=false;
    static boolean recordPageIsOpen=false;

    static Stage passangersStage;
    static Stage messageChatStage;
    static Stage messagePageStage;
    static Stage recordPageStage;

    public static TabPane SAPageTPfake;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SAPageTPfake=superAdminTP;


        //exit button
        exitBTN.setOnAction(e -> {
            prifileTabIsOpen=false;

            //flights part
            if (planesflightsTabIsOpen)
            {
                if (SAFlightsPlanesPageController.flightStageIsOpen) {
                    SAFlightsPlanesPageController.flightStage.close();
                    SAFlightsPlanesPageController.flightStageIsOpen = false;

                }

                if (SAFlightsPageController.editStageIsOpen )
                {
                    SAFlightsPageController.editStageIsOpen=false;
                    SAFlightsPageController.editStage.close();
                }
                if (SAFlightsPageController.addStageIsOpen)
                {
                    SAFlightsPageController.addStageIsOpen=false;
                    SAFlightsPageController.addStage.close();
                }
                if (SAFlightsPageController.passangerStageIsOpen)
                {
                    SAFlightsPageController.passangerStage.close();
                    SAFlightsPageController.passangerStageIsOpen=false;
                }
                planesflightsTabIsOpen=false;
                managerRegisterTabIsOpen=false;
            }

            //passanger part
            if (passangersPageIsOpen)
            {
                passangersPageIsOpen=false;
                passangersStage.close();

                if (SAPassangersPageController.editPageIsOpen)
                {
                    SAPassangersPageController.editPageIsOpen=false;
                    SAPassangersPageController.editStage.close();
                }
            }

            //close managerlistPage
            if (SARegisterManagerPageController.managerListPageIsOpen)
            {
                SARegisterManagerPageController.managerListStage.close();
                SARegisterManagerPageController.managerListPageIsOpen=false;

                //close edit page
                if (SAManagerListPageController.editManagerPageIsOpen)
                {
                    SAManagerListPageController.editManagerStage.close();
                    SAManagerListPageController.editManagerPageIsOpen=false;
                }
            }

            //close employeeList
            if (SARegisterEmpoyeePageController.employeeListIsOpen)
            {
                SARegisterEmpoyeePageController.employeeListIsOpen=false;
                SARegisterEmpoyeePageController.employeeListStage.close();

                //close edit stage******************
                if (SAEmployeeListPageController.editEmployeePageIsOpen)
                {
                    SAEmployeeListPageController.editEmployeePageIsOpen=false;
                    SAEmployeeListPageController.editEmployeeStage.close();
                }
            }


            //close message stage
            if (messageChatStageIsOpen)
            {
                messageChatStage.close();
                messageChatStageIsOpen=false;
            }

            //close send message Page part
            if (messagePageIsOpen)
            {
                messageChatStageIsOpen=false;
                messagePageStage.close();
            }

            ((Stage)exitBTN.getScene().getWindow()).close();
            Main.loginPageStage.show();

            if (recordPageIsOpen)
            {
                recordPageIsOpen=false;
                recordPageStage.close();
            }
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

                flightsPlanesTab=new Tab("Planes");
                flightsPlanesTab.setStyle("-fx-background-color : EE82EE");
                flightsPlanesTab.setContent(pfLoader.getRoot());
                superAdminTP.getTabs().add(flightsPlanesTab);

                accessLBL.setText("");
                planesflightsTabIsOpen=true;
            }
        });

        managerBTN.setOnAction(e -> {

            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/SARegisterManagerPage.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                managerRegisterTab=new Tab("Register Manager");
                managerRegisterTab.setStyle("-fx-background-color : EE82EE");
                managerRegisterTab.setContent(loader.getRoot());
                superAdminTP.getTabs().add(managerRegisterTab);

                accessLBL.setText("");
                managerRegisterTabIsOpen=true;
                accessLBL.setText("");

            }else
            {
                accessLBL.setText("Unaccessible!!!");
            }


        });

        employeeBTN.setOnAction( e -> {
            if (LoginPageController.position== LoginPageController.position.SuperAdmin ||
                    LoginPageController.position== LoginPageController.position.Manager)
            {
                FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/SARegisterEmployeePage.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                employeeRegisterTab=new Tab("Register Employee");
                employeeRegisterTab.setStyle("-fx-background-color : EE82EE");
                employeeRegisterTab.setContent(loader.getRoot());
                superAdminTP.getTabs().add(employeeRegisterTab);

                accessLBL.setText("");
                employeeRegisterTabIsOpen=true;
            }
            else
            {
                accessLBL.setText("Unaccessible!!!");
            }



        });

        //profile button
        profileBTN.setOnAction(e -> {

            {
                if (!prifileTabIsOpen)
                {
                    FXMLLoader saEditProfLoader=new FXMLLoader(Main.class.getResource("View/SAEditProfilePage.fxml"));

                    try {
                        saEditProfLoader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    profileTab=new Tab("Profile");
                    profileTab.setStyle("-fx-background-color: EE82EE");
                    profileTab.setContent(saEditProfLoader.getRoot());
                    superAdminTP.getTabs().add(profileTab);

                    accessLBL.setText("");
                    prifileTabIsOpen=true;
                }
            }

        });

        financialUnitBTN.setOnAction(e -> {
            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                accessLBL.setText("");
            }
            else
            {
                accessLBL.setText("Unaccessible!!!");
            }
        });

        recordBTN.setOnAction(e -> {
            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                if (!recordPageIsOpen)
                {
                    FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/SARecordsPage.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    recordPageStage=new Stage(StageStyle.UNDECORATED);
                    recordPageStage.setScene(new Scene(loader.getRoot()));
                    recordPageStage.show();

                    recordPageIsOpen=true;
                }



                accessLBL.setText("");

            }
            else
            {
                accessLBL.setText("Unaccessible!!!");
            }
        });


        massegeBTN.setOnAction(e -> {

            if (LoginPageController.position== LoginPageController.position.SuperAdmin ||
                    LoginPageController.position== LoginPageController.position.Manager)
            {
                //            FXMLLoader messagechatLoader=new FXMLLoader(Main.class.getResource("View/MessageChatPage.fxml"));
//
//            try {
//                messagechatLoader.load();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//
//            messageTab=new Tab("Messages");
//            messageTab.setStyle("-fx-background-color : EE82EE");
//            messageTab.setContent(messagechatLoader.getRoot());
//            superAdminTP.getTabs().add(messageTab);


                //newwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww

                FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/MessageChatPage.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                messageChatStage=new Stage(StageStyle.UNDECORATED);
                messageChatStage.setScene(new Scene(loader.getRoot()));
                messageChatStage.show();

                accessLBL.setText("");
                messageChatStageIsOpen=true;
            }
            else
            {
                if (!messagePageIsOpen)
                {
                    FXMLLoader messageLoader=new FXMLLoader(Main.class.getResource("View/MessagePage.fxml"));

                    try {
                        messageLoader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    MessagePageController messagePageController=messageLoader.getController();
                    messagePageController.sender="Employee";

                    messagePageStage=new Stage(StageStyle.UNDECORATED);
                    messagePageStage.setScene(new Scene(messageLoader.getRoot()));
                    messagePageStage.show();

                    messagePageIsOpen=true;
                    accessLBL.setText("");
                }
            }



        });

        passangerBTN.setOnAction(e -> {

            if (LoginPageController.position== LoginPageController.position.SuperAdmin ||
                    LoginPageController.position== LoginPageController.position.Manager)
            {
                if (!passangersPageIsOpen)
                {
                    FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/SAPassangersPage.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }


                    passangersStage=new Stage(StageStyle.UNDECORATED);
                    passangersStage.setScene(new Scene(loader.getRoot()));
                    passangersStage.show();

                    accessLBL.setText("");
                    passangersPageIsOpen=true;


                }
            }

            else
            {
                accessLBL.setText("Unaccessible!!!");
            }



        });

    }

}


