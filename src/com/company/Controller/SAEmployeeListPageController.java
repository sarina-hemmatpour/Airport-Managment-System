package com.company.Controller;

import com.company.Main;
import com.company.Model.Employee;
import com.company.Model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SAEmployeeListPageController implements Initializable {


    @FXML
    Button closeBTN;
    @FXML Button editBTN;
    @FXML
    Label warningLBL;
    @FXML
    TableView employeesTV;
    @FXML
    TableColumn idCLM;
    @FXML TableColumn nameCLM;
    @FXML TableColumn lastnameCLM;
    @FXML TableColumn phoneCLM;
    @FXML TableColumn emailCLM;
    @FXML TableColumn usernameCLM;
    @FXML Button removeBTN;


    public static int editEmployeeIndex;
    public static Stage editEmployeeStage;
    public static boolean editEmployeePageIsOpen=false;

    public static TableView fakeEmployeesTV;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fakeEmployeesTV=employeesTV;

        //fill the tableView
        //fill the table view
        idCLM.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCLM.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastnameCLM.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        phoneCLM.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailCLM.setCellValueFactory(new PropertyValueFactory<>("emailAdress"));
        usernameCLM.setCellValueFactory(new PropertyValueFactory<>("username"));

        for (int i = 0; i< Main.employees.size() ; i++)
        {
            employeesTV.getItems().add(Main.employees.get(i));
        }

        closeBTN.setOnAction(e -> {

            SARegisterEmpoyeePageController.employeeListIsOpen=false;
            ((Stage)closeBTN.getScene().getWindow()).close();

            //close edit
            if (editEmployeePageIsOpen)
            {
                editEmployeePageIsOpen=false;
                editEmployeeStage.close();
            }


        });

        removeBTN.setOnAction(e -> removeBTNaction(e));

        editBTN.setOnAction(e -> {
            if (employeesTV.getSelectionModel().getSelectedItem() != null)
            {

                Employee employeeToRemove=(Employee) employeesTV.getSelectionModel().getSelectedItem();
                //find the index
                for (int i=0 ; i<Main.employees.size() ; i++)
                {
                    if (Main.employees.get(i).getId().equals(employeeToRemove.getId()))
                    {
                        editEmployeeIndex=i;
                        break;
                    }
                }

                FXMLLoader loader=new FXMLLoader(Main.class.getResource("View/SAEmployeeEditPage.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                editEmployeeStage=new Stage(StageStyle.UNDECORATED);
                editEmployeeStage.setScene(new Scene(loader.getRoot()));
                editEmployeeStage.show();



                warningLBL.setText("");
                editEmployeePageIsOpen=true;
            }
            else
            {
                warningLBL.setText("Select an employee!!!");
            }
        });

    }

    private void removeBTNaction(ActionEvent e)
    {
        if (employeesTV.getSelectionModel().getSelectedItem() != null)
        {
            Employee employeeToRemove=(Employee) employeesTV.getSelectionModel().getSelectedItem();
            int employeeIndex=-1;
            //find the index
            for (int i=0 ; i<Main.employees.size() ; i++)
            {
                if (Main.employees.get(i).getId().equals(employeeToRemove.getId()))
                {
                    employeeIndex=i;
                    break;
                }
            }

            //remove in objects
            Main.employees.remove(employeeIndex);
            //write in file
            WriteReadFile<Employee> employeeWriteReadFile=new WriteReadFile<>(Main.employees, "Employees.txt");
            employeeWriteReadFile.writeList();

            //refresh the table
            employeesTV.getItems().clear();
            for (int i=0 ; i< Main.employees.size() ; i++)
            {
                employeesTV.getItems().add(Main.employees.get(i));
            }
            employeesTV.refresh();


            warningLBL.setText("");
        }
        else
        {
            warningLBL.setText("Select an employee!!!");
        }
    }



}
