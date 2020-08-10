package com.company.Controller;

import com.company.Main;

import com.company.Model.Employee;
import com.company.Model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SAEmployeeEditPageController implements Initializable {

    @FXML
    Button cancelBTN;
    @FXML Button editBTN;
    @FXML
    TextField nameTF;
    @FXML TextField lastnameTF;
    @FXML TextField usernameTF;
    @FXML TextField passwordTF;
    @FXML TextField creditTF;
    @FXML TextField emailAddressTF;
    @FXML TextField phoneNumberTF;
    @FXML TextField idTF;
    @FXML
    Label warningLBL;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Employee employee=Main.employees.get(SAEmployeeListPageController.editEmployeeIndex);

        //fill
        nameTF.setText(employee.getName());
        lastnameTF.setText(employee.getLastname());
        idTF.setText(employee.getId());
        emailAddressTF.setText(employee.getEmailAdress());
        phoneNumberTF.setText(employee.getPhoneNumber());
        passwordTF.setText(employee.getPassword());
        usernameTF.setText(employee.getUsername());
        creditTF.setText(employee.getSalary());


        cancelBTN.setOnAction(e -> {

            SAEmployeeListPageController.editEmployeePageIsOpen=false;
            ((Stage) cancelBTN.getScene().getWindow()).close();
        });
        editBTN.setOnAction(e -> {
            try {
                editBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


    }



    private void editBTNaction(ActionEvent e) throws IOException {
        if (nameTF.getText().isEmpty() || lastnameTF.getText().isEmpty() || usernameTF.getText().isEmpty() || passwordTF.getText().isEmpty()
                || creditTF.getText().isEmpty() || emailAddressTF.getText().isEmpty() || idTF.getText().isEmpty())
        {
            //some fields arnt filled
            warningLBL.setText("Copmlete all the fields!!!");
        }
        else
        {
            if (!checkSpace())
            {
                //there is space in the fields
                warningLBL.setText("Remove any space in the fields!!!");
            }
            else
            {
                if (!checkFormat())
                {
                    //invalid format
                    warningLBL.setText("Use valid format in the fields!!!");
                }
                else if (!checkExistance())
                {
                    warningLBL.setText("There is already a user with this info!!!");
                }
                else
                {
                    //everything is ok
                    //lets edit
                    int index=SAEmployeeListPageController.editEmployeeIndex;

                    //edit the object
                    Main.employees.get(index).setSalary(creditTF.getText());
                    Main.employees.get(index).setName(nameTF.getText());
                    Main.employees.get(index).setLastname(lastnameTF.getText());
                    Main.employees.get(index).setId(idTF.getText());
                    Main.employees.get(index).setUsername(usernameTF.getText());
                    Main.employees.get(index).setPassword(passwordTF.getText());
                    Main.employees.get(index).setEmailAdress(emailAddressTF.getText());
                    Main.employees.get(index).setPhoneNumber(phoneNumberTF.getText());
                    //write in file
                    WriteReadFile<Employee> employeeWriteReadFile=new WriteReadFile<>(Main.employees, "Employees.txt");
                    employeeWriteReadFile.writeList();

                    //refresh the tableview
                    SAEmployeeListPageController.fakeEmployeesTV.getItems().clear();
                    for (int i=0 ; i< Main.employees.size() ; i++)
                    {
                        SAEmployeeListPageController.fakeEmployeesTV.getItems().add(Main.employees.get(i));
                    }
                    SAEmployeeListPageController.fakeEmployeesTV.refresh();


                    warningLBL.setText("");
                    ((Stage)editBTN.getScene().getWindow()).close();

                }
            }
        }
    }





    private boolean checkFormat()
    {
        //name:
        if (!(nameTF.getText().matches("[a-zA-Z]+")) || !(lastnameTF.getText().matches("[a-zA-Z]+")))
        {
            return false;
        }

        if (!(idTF.getText().matches("[0-9]+")))
        {
            return false;
        }

        if ( (phoneNumberTF.getText().length() !=11) || !(phoneNumberTF.getText().substring(0 , 2).equals("09")) || !(phoneNumberTF.getText().matches("[0-9]+")))
        {
            return false;
        }

        if (!(emailAddressTF.getText().matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")))
        {
            return false;
        }
        if (!(creditTF.getText().matches("[0-9]+")))
        {
            return false;
        }

        return true;

    }


    private boolean checkSpace()
    {
        //name:
        String check1=nameTF.getText();
        check1=check1.trim();
        String[] tempCheck1=check1.split(" ");
        if (tempCheck1.length>1)
        {
            return false;
        }


        //lastname
        String check2=lastnameTF.getText();
        check2=check2.trim();
        String[] tempCheck2=check2.split(" ");
        if (tempCheck2.length>1)
        {
            return false;
        }

        //usernsme
        String check3=usernameTF.getText();
        check3=check3.trim();
        String[] tempCheck3=check3.split(" ");
        if (tempCheck3.length>1)
        {
            return false;
        }

        //password
        String check4=passwordTF.getText();
        check4=check4.trim();
        String[] tempCheck4=check4.split(" ");
        if (tempCheck4.length>1)
        {
            return false;
        }

        //credit
        String check5=creditTF.getText();
        check5=check5.trim();
        String[] tempCheck5=check5.split(" ");
        if (tempCheck5.length>1)
        {
            return false;
        }

        //phone number
        String check6=phoneNumberTF.getText();
        check6=check6.trim();
        String[] tempCheck6=check6.split(" ");
        if (tempCheck6.length>1)
        {
            return false;
        }


        //email adress
        String check8=emailAddressTF.getText();
        check8=check8.trim();
        String[] tempCheck=check8.split(" ");
        if (tempCheck.length>1)
        {
            return false;
        }

        return true;

    }

    private boolean checkExistance()
    {
        //id:
        for (int i = 0; i< Main.employees.size() ; i++)
        {
            if (idTF.getText().equals(Main.employees.get(i).getId()) && i!=SAEmployeeListPageController.editEmployeeIndex)
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (idTF.getText().equals(Main.passangers.get(i).getId()))
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.managers.size() ; i++)
        {
            if (idTF.getText().equals(Main.managers.get(i).getId()))
            {
                return false;
            }
        }
        if (idTF.getText().equals(Main.superAdmin.getId()))
        {
            return false;
        }

        //username:
        for (int i=0 ; i<Main.employees.size() ; i++)
        {
            if (usernameTF.getText().equals(Main.employees.get(i).getUsername()) && i!=SAEmployeeListPageController.editEmployeeIndex)
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (usernameTF.getText().equals(Main.passangers.get(i).getUsername()))
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.managers.size() ; i++)
        {
            if (usernameTF.getText().equals(Main.managers.get(i).getUsername()))
            {
                return false;
            }
        }
        if (usernameTF.getText().equals(Main.superAdmin.getUsername()))
        {
            return false;
        }

        //email:
        for (int i=0 ; i<Main.employees.size() ; i++)
        {
            if (emailAddressTF.getText().toLowerCase().equals(Main.employees.get(i).getEmailAdress().toLowerCase()) && i!=SAEmployeeListPageController.editEmployeeIndex)
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (emailAddressTF.getText().toLowerCase().equals(Main.passangers.get(i).getEmailAdress().toLowerCase()))
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.managers.size() ; i++)
        {
            if (emailAddressTF.getText().toLowerCase().equals(Main.managers.get(i).getEmailAdress().toLowerCase()))
            {
                return false;
            }
        }
        if (emailAddressTF.getText().toLowerCase().equals(Main.superAdmin.getEmailAdress().toLowerCase()))
        {
            return false;
        }

        //phone nmbr:
        for (int i=0 ; i<Main.employees.size() ; i++)
        {
            if (phoneNumberTF.getText().equals(Main.employees.get(i).getPhoneNumber()) && i!=SAEmployeeListPageController.editEmployeeIndex)
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (phoneNumberTF.getText().equals(Main.passangers.get(i).getPhoneNumber()))
            {
                return false;
            }
        }
        for (int i=0 ; i<Main.managers.size() ; i++)
        {
            if (phoneNumberTF.getText().equals(Main.managers.get(i).getPhoneNumber()))
            {
                return false;
            }
        }
        if (phoneNumberTF.getText().equals(Main.superAdmin.getPhoneNumber()))
        {
            return false;
        }

        return true;
    }
}


