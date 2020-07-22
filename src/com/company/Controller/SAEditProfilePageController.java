package com.company.Controller;

import com.company.Main;
import com.company.Model.Manager;
import com.company.Model.Passanger;
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

public class SAEditProfilePageController implements Initializable {

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
    @FXML Label addressLBL;
    @FXML TextField newAddressTF;
    @FXML Button editAddressBTN;
    @FXML Label salaryLBL;
    @FXML Label warningAddressLBL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        warningPasswordLBL.setText("");
        warningEmailLBL.setText("");
        warningPhoneLBL.setText("");
        warningAddressLBL.setText("");

        //filling the form
        nameLBL.setText(Main.superAdmin.getName());
        lastnameLBL.setText(Main.superAdmin.getLastname());
        usernameLBL.setText(Main.superAdmin.getUsername());
        idLBL.setText(Main.superAdmin.getId());
        emailLBL.setText(Main.superAdmin.getEmailAdress());
        phoneLBL.setText(Main.superAdmin.getPhoneNumber());
        addressLBL.setText(Main.superAdmin.getAddress());
        salaryLBL.setText(Main.superAdmin.getSalary());

        //close tab
        closeBTN.setOnAction(e -> {
            //remove tab
            RemoveTabInTabPane removeTabInTabPane=new RemoveTabInTabPane();
            int index=SuperAdminPageController.SAPageTPfake.getTabs().indexOf(SuperAdminPageController.profileTab);
            removeTabInTabPane.removeTab( SuperAdminPageController.SAPageTPfake, index);

            SuperAdminPageController.prifileTabIsOpen=false;
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

        editAddressBTN.setOnAction(e -> {
            try {
                editAddressBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void EditPasswordBTNaction(ActionEvent e) throws IOException {
        if (oldPF.getText().isEmpty() || newPF.getText().isEmpty())
        {
            warningPasswordLBL.setText("Complete both fields!!!");
        }
        else if (!oldPF.getText().equals(Main.superAdmin.getPassword()))
        {
            warningPasswordLBL.setText("The password is not correct!!!");
        }
        else if(newPF.getText().equals(Main.superAdmin.getPassword()))
        {
            warningPasswordLBL.setText("It's not a new password!!!");
        }
        else
        {
            warningPasswordLBL.setText("New password set!");


            //editting the object
            Main.superAdmin.setPassword(newPF.getText());

            //edit file
            WriteReadFile<Manager> superAdminWriteReadFile=new WriteReadFile<>(Main.superAdmin , "SuperAdmin.txt");
            superAdminWriteReadFile.write();


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
        else if(newEmailTF.getText().equals(Main.superAdmin.getEmailAdress()))
        {
            warningEmailLBL.setText("It's not a new Email address!!!");
        }
        else
        {
            warningEmailLBL.setText("New email set!");

            //editting the object
            Main.superAdmin.setEmailAdress(newEmailTF.getText());

            emailLBL.setText(Main.superAdmin.getEmailAdress());

            //edit file
            WriteReadFile<Manager> superadminWriteReadFile=new WriteReadFile<>(Main.superAdmin , "SuperAdmin.txt");
            superadminWriteReadFile.write();


            newEmailTF.setText("");
        }
    }

    private void editPhoneBTNaction(ActionEvent e) throws IOException {
        if (newPhoneTF.getText().isEmpty())
        {
            warningPhoneLBL.setText("Complete the field!!!");
        }
        else if ((newPhoneTF.getText().length() !=11) || !(newPhoneTF.getText().substring(0 , 2).equals("09")) || !(newPhoneTF.getText().matches("[0-9]+")))
        {
            warningPhoneLBL.setText("Invalid format!!!");
        }
        else if(newPhoneTF.getText().equals(Main.superAdmin.getPhoneNumber()))
        {
            warningPhoneLBL.setText("It's not a new phone number!!!");
        }
        else
        {
            warningPhoneLBL.setText("New phone number set!");

            //editting the object
            Main.superAdmin.setPhoneNumber(newPhoneTF.getText());

            phoneLBL.setText(Main.superAdmin.getPhoneNumber());


            //edit file
            WriteReadFile<Manager> superadminWriteReadFile=new WriteReadFile<>(Main.superAdmin , "SuperAdmin.txt");
            superadminWriteReadFile.write();

            newPhoneTF.setText("");


        }
    }

    private void editAddressBTNaction(ActionEvent e) throws IOException {
        if (newAddressTF.getText().isEmpty())
        {
            warningAddressLBL.setText("Complete the field!!!");
        }
        else if (Main.superAdmin.getAddress().equals(newAddressTF.getText()))
        {
            warningAddressLBL.setText("It's not a new Email address!!!");
        }
        else
        {
            warningAddressLBL.setText("new address set!!!");

            //editing the object
            Main.superAdmin.setAddress(newAddressTF.getText());

            addressLBL.setText(Main.superAdmin.getAddress());


            //edit file
            WriteReadFile<Manager> superadminWriteReadFile=new WriteReadFile<>(Main.superAdmin , "SuperAdmin.txt");
            superadminWriteReadFile.write();

            newAddressTF.setText("");

        }
    }
}
