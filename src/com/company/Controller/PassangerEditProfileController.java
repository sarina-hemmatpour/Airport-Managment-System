package com.company.Controller;

import com.company.Main;
import com.company.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PassangerEditProfileController implements Initializable
{
    @FXML Button closeBTN;
    @FXML Label nameLBL;
    @FXML Label lastnameLBL;
    @FXML Label usernameLBL;
    @FXML Label idLBL;
    @FXML Label emailLBL;
    @FXML Label phoneLBL;
    @FXML Button editPasswordBTN;
    @FXML Button editEmailBTN;
    @FXML Button editPhoneBTN;
    @FXML PasswordField oldPF;
    @FXML PasswordField newPF;
    @FXML Label warningPasswordLBL;
    @FXML TextField newEmailTF;
    @FXML TextField newPhoneTF;
    @FXML Label warningEmailLBL;
    @FXML Label warningPhoneLBL;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        warningPasswordLBL.setText("");
        warningEmailLBL.setText("");
        warningPhoneLBL.setText("");

        //filling the form
        nameLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getName());
        lastnameLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getLastname());
        usernameLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getUsername());
        idLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getId());
        emailLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress());
        phoneLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber());


        //close button
        closeBTN.setOnAction(e -> {

            //remove tab
            RemoveTabInTabPane removeTabInTabPane=new RemoveTabInTabPane();
            int index=PassangerPageController.passangerPageTPfake.getTabs().indexOf(PassangerPageController.profileTab);
            removeTabInTabPane.removeTab( PassangerPageController.passangerPageTPfake, index);

            PassangerPageController.prifileTabIsOpen=false;
        });

        editPasswordBTN.setOnAction(e -> {
            try {
                EditPasswordBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        editEmailBTN.setOnAction(e -> {
            try {
                editEmailBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        editPhoneBTN.setOnAction(e -> {
            try {
                editPhoneBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////

    private void editPhoneBTNaction(ActionEvent e) throws IOException {
        if (newPhoneTF.getText().isEmpty())
        {
            warningPhoneLBL.setText("Complete the field!!!");
        }
        else if ((newPhoneTF.getText().length() !=11) || !(newPhoneTF.getText().substring(0 , 2).equals("09")) || !(newPhoneTF.getText().matches("[0-9]+")))
        {
            warningPhoneLBL.setText("Invalid format!!!");
        }
        else if(newPhoneTF.getText().equals(Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()))
        {
            warningPhoneLBL.setText("It's not a new phone number!!!");
        }
        else if (!checkExistance())
        {
            warningEmailLBL.setText("Theres already a user with this info!!!");
        }
        else
        {
            warningPhoneLBL.setText("New phone number set!");

            //editting file
//            String oldLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "+
//                    Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
//
//            //editting file
//            String newLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
//                    +newPhoneTF.getText()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "+
//                    Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
//
//            //find the line
////            int lineToBeEdited=0;
////            FileReader fileReader=new FileReader("Passanger.txt");
////            BufferedReader bufferedReader=new BufferedReader(fileReader);
////
////            String line;
////            while ((line=bufferedReader.readLine()) != null)
////            {
////                lineToBeEdited++;
////                if (line==oldLine)
////                {
////                    break;
////                }
////            }
//
//            String file="Passanger.txt";
//            ChangeLineInFile changeFile=new ChangeLineInFile();
//
//            changeFile.changeALineInATextFile(file,newLine,LoginPageController.loginUserIndex+1);

            //editting the object
            Main.passangers.get(LoginPageController.loginUserIndex).setPhoneNumber(newPhoneTF.getText());
            phoneLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber());
            //edit file
            WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>(Main.passangers , "Passanger.txt");
            passangerWriteReadFile.writeList();


            //edit flights
            for (int i=0 ; i<Main.flights.size() ; i++)
            {
                for (int j=0 ; j<Main.flights.get(i).getPassangers().size() ; j++)
                {
                    if (Main.flights.get(i).getPassangers().get(j).equals(Main.passangers.get(LoginPageController.loginUserIndex)))
                    {
                        Main.flights.get(i).getPassangers().get(j).setPhoneNumber(newPhoneTF.getText());
                    }
                }
            }
            //write in file
            WriteReadFile<Flight> flightWriteReadFile = new WriteReadFile<>(Main.flights, "flights.txt");
            flightWriteReadFile.writeList();



            //edit airplane lists
            for (int i=0 ; i<Main.airplanes.size() ; i++)
            {
                for (int j=0 ; j<Main.airplanes.get(i).getFlights().size() ; j++)
                {
                    for (int k=0 ; k<Main.airplanes.get(i).getFlights().get(j).getPassangers().size() ; k++)
                    {
                        if (Main.airplanes.get(i).getFlights().get(j).getPassangers().get(k)
                                .equals(Main.passangers.get(LoginPageController.loginUserIndex)))
                        {

                            Main.airplanes.get(i).getFlights().get(j).getPassangers().get(k).setPhoneNumber(newPhoneTF.getText());
                        }
                    }
                }
            }
            //write in files
            WriteReadFile<Airplane> airplaneWriteReadFile = new WriteReadFile<>(Main.airplanes, "airplanes.txt");
            airplaneWriteReadFile.writeList();


            //record
            Record record=new Record();
            Main.records.add(record);
            record.passangerEditProfile(Main.passangers.get(LoginPageController.loginUserIndex));
            //write in file
            WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
            recordWriteReadFile.writeList();

            newPhoneTF.setText("");


        }
    }

    private void EditPasswordBTNaction(ActionEvent e) throws IOException {
        if (oldPF.getText().isEmpty() || newPF.getText().isEmpty())
        {
            warningPasswordLBL.setText("Complete both fields!!!");
        }
        else if (!oldPF.getText().equals(Main.passangers.get(LoginPageController.loginUserIndex).getPassword()))
        {
            warningPasswordLBL.setText("The password is not correct!!!");
        }
        else if(newPF.getText().equals(Main.passangers.get(LoginPageController.loginUserIndex).getPassword()))
        {
            warningPasswordLBL.setText("It's not a new password!!!");
        }
        else
        {
            warningPasswordLBL.setText("New password set!");

            //editting file
//
//            String newLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
//                    +newPF.getText()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "+
//                    Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
//
//
//            String file="Passanger.txt";
//            ChangeLineInFile changeFile=new ChangeLineInFile();
//
//
//            changeFile.changeALineInATextFile(file,newLine,LoginPageController.loginUserIndex+1);

            //editting the object
            Main.passangers.get(LoginPageController.loginUserIndex).setPassword(newPF.getText());
            //edit file
            WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>(Main.passangers , "Passanger.txt");
            passangerWriteReadFile.writeList();

            //edit flights
            for (int i=0 ; i<Main.flights.size() ; i++)
            {
                for (int j=0 ; j<Main.flights.get(i).getPassangers().size() ; j++)
                {
                    if (Main.flights.get(i).getPassangers().get(j).equals(Main.passangers.get(LoginPageController.loginUserIndex)))
                    {
                        Main.flights.get(i).getPassangers().get(j).setPassword(newPF.getText());
                    }
                }
            }
            //write in file
            WriteReadFile<Flight> flightWriteReadFile = new WriteReadFile<>(Main.flights, "flights.txt");
            flightWriteReadFile.writeList();



            //edit airplane lists
            for (int i=0 ; i<Main.airplanes.size() ; i++)
            {
                for (int j=0 ; j<Main.airplanes.get(i).getFlights().size() ; j++)
                {
                    for (int k=0 ; k<Main.airplanes.get(i).getFlights().get(j).getPassangers().size() ; k++)
                    {
                        if (Main.airplanes.get(i).getFlights().get(j).getPassangers().get(k)
                                .equals(Main.passangers.get(LoginPageController.loginUserIndex)))
                        {

                            Main.airplanes.get(i).getFlights().get(j).getPassangers().get(k).setPassword(newPF.getText());
                        }
                    }
                }
            }
            //write in files
            WriteReadFile<Airplane> airplaneWriteReadFile = new WriteReadFile<>(Main.airplanes, "airplanes.txt");
            airplaneWriteReadFile.writeList();


            //record
            Record record=new Record();
            Main.records.add(record);
            record.passangerEditProfile(Main.passangers.get(LoginPageController.loginUserIndex));
            //write in file
            WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
            recordWriteReadFile.writeList();

            newPF.setText("");
            oldPF.setText("");
        }
    }

    private void editEmailBTNaction(ActionEvent e) throws IOException {
        if (newEmailTF.getText().isEmpty())
        {
            warningEmailLBL.setText("Complete the field!!!");
        }
        else if (!(newEmailTF.getText().matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")))
        {
            warningEmailLBL.setText("Invalid format!!!");
        }
        else if(newEmailTF.getText().equals(Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()))
        {
            warningEmailLBL.setText("It's not a new Email address!!!");
        }
        else if (!checkExistance())
        {
            warningEmailLBL.setText("Theres already a user with this info!!!");
        }
        else
        {
            warningEmailLBL.setText("New email set!");

            //editting file
//            String oldLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "+
//                    Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
//
//
//            String newLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
//                    +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
//                    +newEmailTF.getText()+" "+
//                    Main.passangers.get(LoginPageController.loginUserIndex).getCredit());
//
//            //find the line
////            int lineToBeEdited=1;
////            FileReader fileReader=new FileReader("Passanger.txt");
////            BufferedReader bufferedReader=new BufferedReader(fileReader);
////
////            String line;
////            while ((line=bufferedReader.readLine()) != null)
////            {
////
////                if (line==oldLine)
////                {
////                    break;
////                }
////                lineToBeEdited++;
////            }
//
//            String file="Passanger.txt";
//            ChangeLineInFile changeFile=new ChangeLineInFile();
//
//            changeFile.changeALineInATextFile(file,newLine,LoginPageController.loginUserIndex+1);

            //editting the object
            Main.passangers.get(LoginPageController.loginUserIndex).setEmailAdress(newEmailTF.getText());
            emailLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress());
            //edit file
            WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>(Main.passangers , "Passanger.txt");
            passangerWriteReadFile.writeList();


            //edit flights
            for (int i=0 ; i<Main.flights.size() ; i++)
            {
                for (int j=0 ; j<Main.flights.get(i).getPassangers().size() ; j++)
                {
                    if (Main.flights.get(i).getPassangers().get(j).equals(Main.passangers.get(LoginPageController.loginUserIndex)))
                    {
                        Main.flights.get(i).getPassangers().get(j).setEmailAdress(newEmailTF.getText());
                    }
                }
            }
            //write in file
            WriteReadFile<Flight> flightWriteReadFile = new WriteReadFile<>(Main.flights, "flights.txt");
            flightWriteReadFile.writeList();



            //edit airplane lists
            for (int i=0 ; i<Main.airplanes.size() ; i++)
            {
                for (int j=0 ; j<Main.airplanes.get(i).getFlights().size() ; j++)
                {
                    for (int k=0 ; k<Main.airplanes.get(i).getFlights().get(j).getPassangers().size() ; k++)
                    {
                        if (Main.airplanes.get(i).getFlights().get(j).getPassangers().get(k)
                                .equals(Main.passangers.get(LoginPageController.loginUserIndex)))
                        {

                            Main.airplanes.get(i).getFlights().get(j).getPassangers().get(k).setEmailAdress(newEmailTF.getText());
                        }
                    }
                }
            }
            //write in files
            WriteReadFile<Airplane> airplaneWriteReadFile = new WriteReadFile<>(Main.airplanes, "airplanes.txt");
            airplaneWriteReadFile.writeList();


            //record
            Record record=new Record();
            Main.records.add(record);
            record.passangerEditProfile(Main.passangers.get(LoginPageController.loginUserIndex));
            //write in file
            WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
            recordWriteReadFile.writeList();

            newEmailTF.setText("");
        }
    }


    private boolean checkExistance()
    {
        //email:
        if (!newEmailTF.getText().equals(null))
        {
            for (int i=0 ; i<Main.passangers.size() ; i++)
            {
                if (newEmailTF.getText().equals(Main.passangers.get(i).getEmailAdress()) && i!=LoginPageController.loginUserIndex)
                {
                    return false;
                }
            }
            for (int i=0 ; i<Main.managers.size() ; i++)
            {
                if (newEmailTF.getText().equals(Main.managers.get(i).getEmailAdress()))
                {
                    return false;
                }
            }
            for (int i=0 ; i<Main.employees.size() ; i++ )
            {
                if (newEmailTF.getText().equals(Main.employees.get(i).getEmailAdress()))
                {
                    return false;
                }
            }
            if (newEmailTF.getText().equals(Main.superAdmin.getEmailAdress()))
            {
                return false;
            }
        }


        //phone nmbr:
        if (!newPhoneTF.getText().equals(null))
        {
            for (int i=0 ; i<Main.passangers.size() ; i++ )
            {
                if (newPhoneTF.getText().equals(Main.passangers.get(i).getPhoneNumber())&& i!=LoginPageController.loginUserIndex)
                {
                    return false;
                }
            }
            for (int i=0 ; i<Main.managers.size() ; i++)
            {
                if (newPhoneTF.getText().equals(Main.managers.get(i).getPhoneNumber()))
                {
                    return false;
                }
            }
            for (int i=0 ; i<Main.employees.size() ; i++)
            {
                if (newPhoneTF.getText().equals(Main.employees.get(i).getPhoneNumber()))
                {
                    return false;
                }
            }
            if (newPhoneTF.getText().equals(Main.superAdmin.getPhoneNumber()))
            {
                return false;
            }
        }


        return true;
    }
}
