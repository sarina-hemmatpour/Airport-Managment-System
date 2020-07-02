package com.company.Controller;

import com.company.Main;
import com.company.Model.Passanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPageController implements Initializable
{
    @FXML Button cancelBTN;
    @FXML Button signUpBTN;
    @FXML TextField nameTF;
    @FXML TextField lastnameTF;
    @FXML TextField usernameTF;
    @FXML PasswordField passwordTF;
    @FXML TextField creditTF;
    @FXML TextField addressTF;
    @FXML TextField emailAddressTF;
    @FXML TextField phoneNumberTF;
    @FXML TextField idTF;
    @FXML Label warningLBL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        cancelBTN.setOnAction(e -> cancelBTNaction(e));

        signUpBTN.setOnAction(e -> {
            try {
                signUpBTNaction(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void cancelBTNaction(ActionEvent e)
    {
         ((Stage) cancelBTN.getScene().getWindow()).close();
    }

    private void signUpBTNaction(ActionEvent e) throws IOException {
        if (nameTF.getText().isEmpty() || lastnameTF.getText().isEmpty() || usernameTF.getText().isEmpty() || passwordTF.getText().isEmpty()
        || creditTF.getText().isEmpty() || addressTF.getText().isEmpty() || emailAddressTF.getText().isEmpty() || idTF.getText().isEmpty())
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
                else
                {
                    if (!checkExistance())
                    {
                        //there is already a user with this information
                        warningLBL.setText("There is already a user with this info!!!");
                    }
                    else
                    {
                        //every thing is ok
                        //saving information

                        Passanger newPassanger=new Passanger(idTF.getText(),nameTF.getText(),lastnameTF.getText(),
                                usernameTF.getText(),passwordTF.getText(),phoneNumberTF.getText(),emailAddressTF.getText(),
                                creditTF.getText());
                        Main.passangers.add(newPassanger);

                        //writing thes information in passanger file

                        FileWriter passangerFW=new FileWriter("Passanger.txt" , true);
                        BufferedWriter passangerBW=new BufferedWriter(passangerFW);

                        String toSave=(idTF.getText()+" "+nameTF.getText()+" "+lastnameTF.getText()+" "+
                                usernameTF.getText()+" "+passwordTF.getText()+" "+phoneNumberTF.getText()+" "+emailAddressTF.getText()+" "+
                                creditTF.getText());
                        passangerBW.write(toSave);
                        passangerBW.newLine();
                        passangerBW.close();

                        //register completed
                        ((Stage)signUpBTN.getScene().getWindow()).close();
                    }

                }
            }
        }
    }


    private boolean checkExistance()
    {
        //id:
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (idTF.getText().equals(Main.passangers.get(i).getId()))
            {
                return false;
            }
        }

        //username:
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (usernameTF.getText().equals(Main.passangers.get(i).getUsername()))
            {
                return false;
            }
        }

        //email:
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (emailAddressTF.getText().equals(Main.passangers.get(i).getEmailAdress()))
            {
                return false;
            }
        }

        //phone nmbr:
        for (int i=0 ; i<Main.passangers.size() ; i++)
        {
            if (phoneNumberTF.getText().equals(Main.passangers.get(i).getPhoneNumber()))
            {
                return false;
            }
        }

        return true;
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

        //address
            String check7=addressTF.getText();
            check7=check7.trim();
            String[] tempCheck7=check7.split(" ");
            if (tempCheck7.length>1)
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






}
