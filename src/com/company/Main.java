package com.company;

import com.company.Model.Employee;
import com.company.Model.Manager;
import com.company.Model.Passanger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {


    public static Manager superAdmin ;
    public static ArrayList<Manager> managers=new ArrayList<>();
    public static ArrayList<Employee> employees=new ArrayList<>();
    public static ArrayList<Passanger> passangers=new ArrayList<>();





    public static void main(String[] args) throws IOException {

        ////////////////////////////////////////////////////////////////////////////
        //writing superAdmin in file

        FileReader superAdminFR=new FileReader("SuperAdmin.txt");
        BufferedReader superAdminBR=new BufferedReader(superAdminFR);

        FileWriter superAdminFW=new FileWriter("SuperAdmin.txt");
        BufferedWriter superAdminBW=new BufferedWriter(superAdminFW);

        if ((superAdminBR.readLine()) == null)
        {
            //the file is empty



            //saveing username and password of the super admin to SuperAdmin.txt
            String forSave="1743434421 Sarina Hemmatpour rolijesoo 2221380 09140465914 " +
                    "blackpink.lisa.sh@gmail.com 1000 Isfahan";
            superAdminBW.write(forSave);
            superAdminBW.newLine();

            superAdminBW.close();
        }

        superAdminBW.close();

        //super admin file isnt empty so we creat a superAdmin
        //creat super admin

        String toRead=superAdminBR.readLine();
        String[] properties=toRead.split(" ");

        superAdmin = new Manager(properties[0] , properties[1] , properties[2] , properties[3]
                , properties[4] , properties[5] , properties[6] , properties[7]
                , properties[8] , true );
        ////////////////////////////////////////////////////////////////////////////////////////////

        //saving all the users in array lists

        //reading files:
        ////////////////////////////////////////
        //managers:
        FileReader managerFR=new FileReader("Managers.txt");
        BufferedReader managerBR=new BufferedReader(managerFR);

        String toRead1;
        while ((toRead1=managerBR.readLine()) != null)
        {
            String[] propeties=toRead1.split(" ");

            Manager tempManager=new Manager(propeties[0] , propeties[1] , propeties[2] , propeties[3] , propeties[4]
                    , propeties[5] , propeties[6] , propeties[7] , propeties[8] , false);

            managers.add(tempManager);
        }

        //employees:
        FileReader employeeFR=new FileReader("Employees.txt");
        BufferedReader employeeBR=new BufferedReader(employeeFR);

        String toRead2;
        while ((toRead2=employeeBR.readLine()) != null)
        {
            String[] propeties=toRead2.split(" ");

            Employee tempEmployee=new Employee(propeties[0] , propeties[1] , propeties[2] , propeties[3] , propeties[4]
                    , propeties[5] , propeties[6] , propeties[7] , propeties[8]);

            employees.add(tempEmployee);
        }

        //passangers
        FileReader passangerFR=new FileReader("Passangers.txt");
        BufferedReader passangerBR=new BufferedReader(passangerFR);

        String toRead3;
        while ((toRead3=passangerBR.readLine()) != null)
        {
            String[] propeties=toRead3.split(" ");

            Passanger tempPassanger=new Passanger(propeties[0] , propeties[1] , propeties[2] , propeties[3] , propeties[4]
                    , propeties[5] , propeties[6] , propeties[7] );

            passangers.add(tempPassanger);
        }
        ////////////////////////////////////////




        launch(args);
    }





    @Override
    public void start(Stage stage) throws Exception
    {

        //loading login stage
        FXMLLoader loginPageLoader=new FXMLLoader(Main.class.getResource("View/LoginPage.fxml"));

        try {
            loginPageLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Stage loginPageStage=new Stage();
        loginPageStage.setTitle("Login");
        loginPageStage.setScene(new Scene(loginPageLoader.getRoot()));
        loginPageStage.show();
    }
}
