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

import javax.sound.midi.MidiChannel;
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
        Person person = null;
        if (LoginPageController.position== LoginPageController.position.SuperAdmin)
        {
            person=Main.superAdmin;
            addressLBL.setText(Main.superAdmin.getAddress());
            salaryLBL.setText(Main.superAdmin.getSalary());
        }
        else if (LoginPageController.position== LoginPageController.position.Manager)
        {
            person=Main.managers.get(LoginPageController.loginUserIndex);
            addressLBL.setText(Main.managers.get(LoginPageController.loginUserIndex).getAddress());
            salaryLBL.setText(Main.managers.get(LoginPageController.loginUserIndex).getSalary());
        }
        else if (LoginPageController.position== LoginPageController.position.Employee)
        {
            person=Main.employees.get(LoginPageController.loginUserIndex);
            addressLBL.setText(Main.employees.get(LoginPageController.loginUserIndex).getAddress());
            salaryLBL.setText(Main.employees.get(LoginPageController.loginUserIndex).getSalary());
        }

        nameLBL.setText(person.getName());
        lastnameLBL.setText(person.getLastname());
        usernameLBL.setText(person.getUsername());
        idLBL.setText(person.getId());
        emailLBL.setText(person.getEmailAdress());
        phoneLBL.setText(person.getPhoneNumber());



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

        Person person;
        if (LoginPageController.position== LoginPageController.position.SuperAdmin)
        {
             person=Main.superAdmin;
        }
        else if (LoginPageController.position== LoginPageController.position.Manager)
        {
            person=Main.managers.get(LoginPageController.loginUserIndex);
        }
        else
        {
            person=Main.employees.get(LoginPageController.loginUserIndex);
        }


        if (oldPF.getText().isEmpty() || newPF.getText().isEmpty())
        {
            warningPasswordLBL.setText("Complete both fields!!!");
        }
        else if (!oldPF.getText().equals(person.getPassword()))
        {
            warningPasswordLBL.setText("The password is not correct!!!");
        }
        else if(newPF.getText().equals(person.getPassword()))
        {
            warningPasswordLBL.setText("It's not a new password!!!");
        }
        else
        {
            warningPasswordLBL.setText("New password set!");

            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                //editting the object
                Main.superAdmin.setPassword(newPF.getText());

                //edit file
                WriteReadFile<Manager> superAdminWriteReadFile=new WriteReadFile<>(Main.superAdmin , "SuperAdmin.txt");
                superAdminWriteReadFile.write();


                //record
                Record record=new Record();
                Main.records.add(record);
                record.superAdminEditProfile();
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else if (LoginPageController.position== LoginPageController.position.Manager)
            {
                //editting the object
                Main.managers.get(LoginPageController.loginUserIndex).setPassword(newPF.getText());

                //write in fle
                WriteReadFile<Manager> managerWriteReadFile=new WriteReadFile<>(Main.managers, "Managers.txt");
                managerWriteReadFile.writeList();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.managerEditProfile(Main.managers.get(LoginPageController.loginUserIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();

            }
            else if (LoginPageController.position== LoginPageController.position.Employee)
            {
                Main.employees.get(LoginPageController.loginUserIndex).setPassword(newPF.getText());

                //editing the file
                WriteReadFile<Employee> employeeWriteReadFile=new WriteReadFile<>(Main.employees, "Employees.txt");
                employeeWriteReadFile.writeList();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.employeeEditProfile(Main.employees.get(LoginPageController.loginUserIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();

            }



            newPF.setText("");
            oldPF.setText("");
        }
    }


    private void editEmailBTNaction(ActionEvent e) throws IOException {

        Person person;
        if (LoginPageController.position== LoginPageController.position.SuperAdmin)
        {
            person=Main.superAdmin;
        }
        else if (LoginPageController.position== LoginPageController.position.Manager)
        {
            person=Main.managers.get(LoginPageController.loginUserIndex);
        }
        else
        {
            person=Main.employees.get(LoginPageController.loginUserIndex);
        }

        if (newEmailTF.getText().isEmpty())
        {
            warningEmailLBL.setText("Complete the field!!!");
        }
        else if (!(newEmailTF.getText().matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")))
        {
            warningEmailLBL.setText("Invalid format!!!");
        }
        else if(newEmailTF.getText().equals(person.getEmailAdress()))
        {
            warningEmailLBL.setText("It's not a new Email address!!!");
        }
        else if (!checkExistance())
        {
            warningEmailLBL.setText("Theres alredy a user with this info!!!");
        }
        else
        {
            warningEmailLBL.setText("New email set!");


            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                //editting the object
                //editting the object
                Main.superAdmin.setEmailAdress(newEmailTF.getText());

                emailLBL.setText(Main.superAdmin.getEmailAdress());

                //edit file
                WriteReadFile<Manager> superAdminWriteReadFile=new WriteReadFile<>(Main.superAdmin , "SuperAdmin.txt");
                superAdminWriteReadFile.write();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.superAdminEditProfile();
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else if (LoginPageController.position== LoginPageController.position.Manager)
            {
                //editting the object
                Main.managers.get(LoginPageController.loginUserIndex).setEmailAdress(newEmailTF.getText());

                emailLBL.setText(Main.managers.get(LoginPageController.loginUserIndex).getEmailAdress());

                //write in fle
                WriteReadFile<Manager> managerWriteReadFile=new WriteReadFile<>(Main.managers, "Managers.txt");
                managerWriteReadFile.writeList();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.managerEditProfile(Main.managers.get(LoginPageController.loginUserIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else if (LoginPageController.position== LoginPageController.position.Employee)
            {
                Main.employees.get(LoginPageController.loginUserIndex).setEmailAdress(newEmailTF.getText());

                emailLBL.setText(Main.employees.get(LoginPageController.loginUserIndex).getEmailAdress());

                //editing the file
                WriteReadFile<Employee> employeeWriteReadFile=new WriteReadFile<>(Main.employees, "Employees.txt");
                employeeWriteReadFile.writeList();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.employeeEditProfile(Main.employees.get(LoginPageController.loginUserIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }


            newEmailTF.setText("");
        }
    }

    private void editPhoneBTNaction(ActionEvent e) throws IOException {


        Person person;
        if (LoginPageController.position== LoginPageController.position.SuperAdmin)
        {
            person=Main.superAdmin;
        }
        else if (LoginPageController.position== LoginPageController.position.Manager)
        {
            person=Main.managers.get(LoginPageController.loginUserIndex);
        }
        else
        {
            person=Main.employees.get(LoginPageController.loginUserIndex);
        }

        if (newPhoneTF.getText().isEmpty())
        {
            warningPhoneLBL.setText("Complete the field!!!");
        }
        else if ((newPhoneTF.getText().length() !=11) || !(newPhoneTF.getText().substring(0 , 2).equals("09")) || !(newPhoneTF.getText().matches("[0-9]+")))
        {
            warningPhoneLBL.setText("Invalid format!!!");
        }
        else if(newPhoneTF.getText().equals(person.getPhoneNumber()))
        {
            warningPhoneLBL.setText("It's not a new phone number!!!");
        }
        else if (!checkExistance())
        {
            warningPhoneLBL.setText("Theres already a user with this info!!!");
        }
        else
        {
            warningPhoneLBL.setText("New phone number set!");


            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                //editting the object
                Main.superAdmin.setPhoneNumber(newPhoneTF.getText());

                phoneLBL.setText(Main.superAdmin.getPhoneNumber());
                //edit file
                WriteReadFile<Manager> superAdminWriteReadFile=new WriteReadFile<>(Main.superAdmin , "SuperAdmin.txt");
                superAdminWriteReadFile.write();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.superAdminEditProfile();
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else if (LoginPageController.position== LoginPageController.position.Manager)
            {
                //editting the object
                Main.managers.get(LoginPageController.loginUserIndex).setPhoneNumber(newPhoneTF.getText());

                phoneLBL.setText(Main.managers.get(LoginPageController.loginUserIndex).getPhoneNumber());

                //write in fle
                WriteReadFile<Manager> managerWriteReadFile=new WriteReadFile<>(Main.managers, "Managers.txt");
                managerWriteReadFile.writeList();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.managerEditProfile(Main.managers.get(LoginPageController.loginUserIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else if (LoginPageController.position== LoginPageController.position.Employee)
            {
                Main.employees.get(LoginPageController.loginUserIndex).setPhoneNumber(newPhoneTF.getText());

                phoneLBL.setText(Main.employees.get(LoginPageController.loginUserIndex).getPhoneNumber());

                //editing the file
                WriteReadFile<Employee> employeeWriteReadFile=new WriteReadFile<>(Main.employees, "Employees.txt");
                employeeWriteReadFile.writeList();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.employeeEditProfile(Main.employees.get(LoginPageController.loginUserIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }




            newPhoneTF.setText("");


        }
    }

    private void editAddressBTNaction(ActionEvent e) throws IOException {

        String address;
        if (LoginPageController.position== LoginPageController.position.SuperAdmin)
        {
            address=Main.superAdmin.getAddress();
        }
        else if (LoginPageController.position== LoginPageController.position.Manager)
        {
            address=Main.managers.get(LoginPageController.loginUserIndex).getAddress();
        }
        else
        {
            address=Main.employees.get(LoginPageController.loginUserIndex).getAddress();
        }
        if (newAddressTF.getText().isEmpty())
        {
            warningAddressLBL.setText("Complete the field!!!");
        }
        else if (address.equals(newAddressTF.getText()))
        {
            warningAddressLBL.setText("It's not a new Email address!!!");
        }
        else
        {
            warningAddressLBL.setText("new address set!!!");



            if (LoginPageController.position== LoginPageController.position.SuperAdmin)
            {
                //editing the object
                Main.superAdmin.setAddress(newAddressTF.getText());

                addressLBL.setText(Main.superAdmin.getAddress());

                //edit file
                WriteReadFile<Manager> superAdminWriteReadFile=new WriteReadFile<>(Main.superAdmin , "SuperAdmin.txt");
                superAdminWriteReadFile.write();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.superAdminEditProfile();
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }
            else if (LoginPageController.position== LoginPageController.position.Manager)
            {
                //editting the object
                Main.managers.get(LoginPageController.loginUserIndex).setAddress(newAddressTF.getText());

                addressLBL.setText(Main.managers.get(LoginPageController.loginUserIndex).getAddress());

                //write in fle
                WriteReadFile<Manager> managerWriteReadFile=new WriteReadFile<>(Main.managers, "Managers.txt");
                managerWriteReadFile.writeList();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.managerEditProfile(Main.managers.get(LoginPageController.loginUserIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();

            }
            else if (LoginPageController.position== LoginPageController.position.Employee)
            {
                Main.employees.get(LoginPageController.loginUserIndex).setAddress(newAddressTF.getText());

                addressLBL.setText(Main.employees.get(LoginPageController.loginUserIndex).getAddress());

                //editing the file
                WriteReadFile<Employee> employeeWriteReadFile=new WriteReadFile<>(Main.employees, "Employees.txt");
                employeeWriteReadFile.writeList();

                //record
                Record record=new Record();
                Main.records.add(record);
                record.employeeEditProfile(Main.employees.get(LoginPageController.loginUserIndex));
                //write in file
                WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(Main.records , "Records.txt");
                recordWriteReadFile.writeList();
            }

            newAddressTF.setText("");

        }
    }

    private boolean checkExistance()
    {
        //email:
        if (!newEmailTF.getText().equals(null))
        {

            for (int i=0 ; i<Main.passangers.size() ; i++)
            {
                if (newEmailTF.getText().toLowerCase().equals(Main.passangers.get(i).getEmailAdress().toLowerCase()))
                {
                    return false;
                }
            }
            for (int i=0 ; i<Main.managers.size() ; i++)
            {
                if (newEmailTF.getText().toLowerCase().equals(Main.managers.get(i).getEmailAdress().toLowerCase()))
                {
                    if (LoginPageController.position== LoginPageController.position.Manager)
                    {
                        if (i!=LoginPageController.loginUserIndex)
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }

                }
            }
            for (int i=0 ; i<Main.employees.size() ; i++ )
            {
                if (newEmailTF.getText().toLowerCase().equals(Main.employees.get(i).getEmailAdress().toLowerCase()) )
                {
                    if (LoginPageController.position== LoginPageController.position.Employee)
                    {
                        if (i!=LoginPageController.loginUserIndex)
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }

            if (LoginPageController.position!= LoginPageController.position.SuperAdmin)
            {
                if (newEmailTF.getText().toLowerCase().equals(Main.superAdmin.getEmailAdress().toLowerCase()))
                {
                    return false;
                }
            }
        }


        //phone nmbr:
        if (!newPhoneTF.getText().equals(null))
        {
            for (int i=0 ; i<Main.passangers.size() ; i++)
            {
                if (newPhoneTF.getText().equals(Main.passangers.get(i).getPhoneNumber()))
                {
                    return false;
                }
            }
            for (int i=0 ; i<Main.managers.size() ; i++)
            {
                if (newPhoneTF.getText().equals(Main.managers.get(i).getPhoneNumber()))
                {
                    if (LoginPageController.position== LoginPageController.position.Manager)
                    {
                        if (i!=LoginPageController.loginUserIndex)
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            for (int i=0 ; i<Main.employees.size() ; i++)
            {
                if (newPhoneTF.getText().equals(Main.employees.get(i).getPhoneNumber())  )
                {
                    if (LoginPageController.position== LoginPageController.position.Employee)
                    {
                        if (i!=LoginPageController.loginUserIndex)
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }

            if (LoginPageController.position!= LoginPageController.position.SuperAdmin)
            {
                if (newPhoneTF.getText().equals(Main.superAdmin.getPhoneNumber()))
                {
                    return false;
                }
            }
        }


        return true;
    }
}
