package com.company.Controller;

import com.company.Main;
import com.company.Model.ChangeLineInFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
            PassangerPageController.closeTab(1);
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
        else
        {
            warningPhoneLBL.setText("New phone number set!");

            //editting file
            String oldLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "+
                    Main.passangers.get(LoginPageController.loginUserIndex).getCredit());

            //editting file
            String newLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getName()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getLastname()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getUsername()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getPassword()+" "
                    +newPhoneTF.getText()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "+
                    Main.passangers.get(LoginPageController.loginUserIndex).getCredit());

            //find the line
//            int lineToBeEdited=0;
//            FileReader fileReader=new FileReader("Passanger.txt");
//            BufferedReader bufferedReader=new BufferedReader(fileReader);
//
//            String line;
//            while ((line=bufferedReader.readLine()) != null)
//            {
//                lineToBeEdited++;
//                if (line==oldLine)
//                {
//                    break;
//                }
//            }

            String file="Passanger.txt";
            ChangeLineInFile changeFile=new ChangeLineInFile();

            changeFile.changeALineInATextFile(file,newLine,LoginPageController.loginUserIndex+1);

            //editting the object
            Main.passangers.get(LoginPageController.loginUserIndex).setPhoneNumber(newPhoneTF.getText());

            phoneLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber());


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
            String oldLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
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
                    +newPF.getText()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getPhoneNumber()+" "
                    +Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress()+" "+
                    Main.passangers.get(LoginPageController.loginUserIndex).getCredit());

            //find the line
//            int lineToBeEdited=0;
//            FileReader fileReader=new FileReader("Passanger.txt");
//            BufferedReader bufferedReader=new BufferedReader(fileReader);
//
//            String line;
//            while ((line=bufferedReader.readLine()) != null)
//            {
//                lineToBeEdited++;
//                if (line==oldLine)
//                {
//                    break;
//                }
//            }

            String file="Passanger.txt";
            ChangeLineInFile changeFile=new ChangeLineInFile();


            changeFile.changeALineInATextFile(file,newLine,LoginPageController.loginUserIndex+1);

            //editting the object
            Main.passangers.get(LoginPageController.loginUserIndex).setPassword(newPF.getText());
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
        else
        {
            warningEmailLBL.setText("New email set!");

            //editting file
            String oldLine=(Main.passangers.get(LoginPageController.loginUserIndex).getId()+" "
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
                    +newEmailTF.getText()+" "+
                    Main.passangers.get(LoginPageController.loginUserIndex).getCredit());

            //find the line
//            int lineToBeEdited=1;
//            FileReader fileReader=new FileReader("Passanger.txt");
//            BufferedReader bufferedReader=new BufferedReader(fileReader);
//
//            String line;
//            while ((line=bufferedReader.readLine()) != null)
//            {
//
//                if (line==oldLine)
//                {
//                    break;
//                }
//                lineToBeEdited++;
//            }

            String file="Passanger.txt";
            ChangeLineInFile changeFile=new ChangeLineInFile();

            changeFile.changeALineInATextFile(file,newLine,LoginPageController.loginUserIndex+1);

            //editting the object
            Main.passangers.get(LoginPageController.loginUserIndex).setEmailAdress(newEmailTF.getText());

            emailLBL.setText(Main.passangers.get(LoginPageController.loginUserIndex).getEmailAdress());
        }
    }
}
