package com.company;

import com.company.Controller.SuperAdminPageController;
import com.company.Controller.WriteReadFile;
import com.company.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sound.midi.Soundbank;;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Main extends Application {

    public static Manager superAdmin ;

    public static ArrayList<Manager> managers=new ArrayList<>();

    public static ArrayList<Employee> employees=new ArrayList<>();

    public static ArrayList<Passanger> passangers=new ArrayList<>();

    public static ArrayList<Airplane> airplanes=new ArrayList<>();

    public static ArrayList<Ticket> tickets=new ArrayList<>();

    public static ArrayList<Flight> flights=new ArrayList<>();

    public static ArrayList<Message> messages=new ArrayList<>();

    public static ArrayList<Record> records=new ArrayList<>();

    public static Stage loginPageStage;




    public static void main(String[] args) throws IOException , ClassNotFoundException {





        System.out.println(LocalDateTime.now());

        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();
        System.out.println(dtf.format(now));

        ////////////////////////////////////////////////////////////////////////////

//        //creating super admin
//        superAdmin=new Manager("1743434421", "Sarina" , "Hemmatpour" ,"rolijesoo" ,
//                "2221380" , "09140465914" , "blackpink.lisa.sh@gmail.com" , "8000" ,
//                "Isfahan" , true);

//        //save in file
        WriteReadFile<Manager> superAdminWriteReadFile=new WriteReadFile<>(superAdmin,"SuperAdmin.txt");
//        superAdminWriteReadFile.write();

        superAdmin=superAdminWriteReadFile.read();



        ////////////////////////////////////////////////////////////////////////////////////////////

        //saving all the users in array lists

        //reading files:
        ////////////////////////////////////////
        //managers:
//        FileReader managerFR=new FileReader("Managers.txt");
//        BufferedReader managerBR=new BufferedReader(managerFR);
//
//        String toRead1;
//        while ((toRead1=managerBR.readLine()) != null)
//        {
//            String[] propeties=toRead1.split(" ");
//
//            Manager tempManager=new Manager(propeties[0] , propeties[1] , propeties[2] , propeties[3] , propeties[4]
//                    , propeties[5] , propeties[6] , propeties[7] , propeties[8] , false);
//
//            managers.add(tempManager);
//        }
//        WriteReadFile<Manager> managerWriteReadFile=new WriteReadFile<>(managers , "Managers.txt");
//        managers=managerWriteReadFile.readList();

        WriteReadFile<Manager> managerWriteReadFile = new WriteReadFile<>(managers, "Managers.txt");
        managers = managerWriteReadFile.readList();




        //employees:
//        FileReader employeeFR=new FileReader("Employees.txt");
//        BufferedReader employeeBR=new BufferedReader(employeeFR);
//
//        String toRead2;
//        while ((toRead2=employeeBR.readLine()) != null)
//        {
//            String[] propeties=toRead2.split(" ");
//
//            Employee tempEmployee=new Employee(propeties[0] , propeties[1] , propeties[2] , propeties[3] , propeties[4]
//                    , propeties[5] , propeties[6] , propeties[7] , propeties[8]);
//
//            employees.add(tempEmployee);
//        }

        WriteReadFile<Employee> employeeWriteReadFile=new WriteReadFile<>(employees,"Employees.txt");
        employees=employeeWriteReadFile.readList();

        //passangers
//        FileReader passangerFR=new FileReader("Passanger.txt");
//        BufferedReader passangerBR=new BufferedReader(passangerFR);
//
//        String toRead3;
//        while ((toRead3=passangerBR.readLine()) != null)
//        {
//            String[] propeties=toRead3.split(" ");
//
//            Passanger tempPassanger=new Passanger(propeties[0] , propeties[1] , propeties[2] , propeties[3] , propeties[4]
//                    , propeties[5] , propeties[6] , propeties[7] );
//
//            passangers.add(tempPassanger);
//        }

        WriteReadFile<Passanger> passangerWriteReadFile=new WriteReadFile<>(passangers,"Passanger.txt");
        passangers=passangerWriteReadFile.readList();

        //airplanes

        WriteReadFile<Airplane> airplaneWriteReadFile =new WriteReadFile<>(airplanes , "airplanes.txt");
        airplanes=airplaneWriteReadFile.readList();


        //tickets
        WriteReadFile<Ticket> ticketWriteReadFile=new WriteReadFile<>(tickets , "Tickets.txt");
        tickets=ticketWriteReadFile.readList();

        //flights
        WriteReadFile<Flight> flightWriteReadFile=new WriteReadFile<>(flights,"flights.txt");
        flights=flightWriteReadFile.readList();

        //messages
//        BufferedReader messageBR=new BufferedReader(new FileReader("Messages.txt"));
//
//        String toRead4;
//
//        while ((toRead4=messageBR.readLine()) != null)
//        {
//            String[] propertiesm=toRead4.split(";");
//
//
//            Message newMessage=new Message(propertiesm[1]);
//            newMessage.setTime(propertiesm[0]);
//
//            messages.add(newMessage);
//        }
        WriteReadFile<Message> messageWriteReadFile=new WriteReadFile<>(messages , "Messages.txt");
        messages=messageWriteReadFile.readList();


        //records
        WriteReadFile<Record> recordWriteReadFile=new WriteReadFile<>(records , "Records.txt");
        records=recordWriteReadFile.readList();

        ////////////////////////////////////////


//        System.out.println(airplanes.get(0).getFlights().get(0).getPassangers().size());




        setFlightsStatus();
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
        loginPageStage=new Stage();
        loginPageStage.setTitle("Login");
        loginPageStage.setScene(new Scene(loginPageLoader.getRoot()));
        loginPageStage.show();
    }


    private static void setFlightsStatus()
    {
        for (int i=0 ; i<flights.size() ; i++)
        {
            DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now=LocalDateTime.now();
            //joda krdane time haye now
            String nowString=dtf.format(now);
            String[] date_clockNow=nowString.split(" ");

            String[] dateNow=date_clockNow[0].split("/");
            String[] clockNow=date_clockNow[1].split(":");
            //cast into integer
            int yearNow=Integer.parseInt(dateNow[0])%1000;
            int monthNow=Integer.parseInt(dateNow[1]);
            int dayNow=Integer.parseInt(dateNow[2]);

            int hourNow=Integer.parseInt(clockNow[0]);
            int miniuteNow=Integer.parseInt(clockNow[1]);

            //joda kardane flight time ha

            String[] date=flights.get(i).getDate().split("/");
            String[] clock=flights.get(i).getTakeOffTime().split(":");
            //cast into integer
            int year=Integer.parseInt(date[0]);
            int month=Integer.parseInt(date[1]);
            int day=Integer.parseInt(date[2]);

            int hour=Integer.parseInt(clock[0]);
            int miniute=Integer.parseInt(clock[1]);

            //check
            if (yearNow>year)
            {
                flights.get(i).setFlightStatus(Flight.Status.DONE);
            }else if (year==yearNow)
            {
                if (monthNow>month)
                {
                    flights.get(i).setFlightStatus(Flight.Status.DONE);
                }else if (monthNow==month)
                {
                    if (day<dayNow)
                    {
                        flights.get(i).setFlightStatus(Flight.Status.DONE);
                    }else if(hour<hourNow)
                    {
                        flights.get(i).setFlightStatus(Flight.Status.DONE);
                    }else if (hour==hourNow)
                    {
                        if (miniute<=miniuteNow)
                        {
                            flights.get(i).setFlightStatus(Flight.Status.DONE);
                        }
                    }
                }
            }
        }
        //save in file
        WriteReadFile<Flight> flightWriteReadFile=new WriteReadFile<>(Main.flights,"flights.txt");
        flightWriteReadFile.writeList();


        for (int i=0 ; i<airplanes.size() ; i++)
        {
            for (int j=0 ; j<airplanes.get(i).getFlights().size() ; j++)
            {
                DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now=LocalDateTime.now();
                //joda krdane time haye now
                String nowString=dtf.format(now);
                String[] date_clockNow=nowString.split(" ");

                String[] dateNow=date_clockNow[0].split("/");
                String[] clockNow=date_clockNow[1].split(":");
                //cast into integer
                int yearNow=Integer.parseInt(dateNow[0])%1000;
                int monthNow=Integer.parseInt(dateNow[1]);
                int dayNow=Integer.parseInt(dateNow[2]);

                int hourNow=Integer.parseInt(clockNow[0]);
                int miniuteNow=Integer.parseInt(clockNow[1]);

                //joda kardane flight time ha

                String[] date=airplanes.get(i).getFlights().get(j).getDate().split("/");
                String[] clock=airplanes.get(i).getFlights().get(j).getTakeOffTime().split(":");
                //cast into integer
                int year=Integer.parseInt(date[0]);
                int month=Integer.parseInt(date[1]);
                int day=Integer.parseInt(date[2]);

                int hour=Integer.parseInt(clock[0]);
                int miniute=Integer.parseInt(clock[1]);

                //check
                if (yearNow>year)
                {
                    airplanes.get(i).getFlights().get(j).setFlightStatus(Flight.Status.DONE);
                }else if (year==yearNow)
                {
                    if (monthNow>month)
                    {
                        airplanes.get(i).getFlights().get(j).setFlightStatus(Flight.Status.DONE);
                    }else if (monthNow==month)
                    {
                        if (day<dayNow)
                        {
                            airplanes.get(i).getFlights().get(j).setFlightStatus(Flight.Status.DONE);
                        }else if (day==dayNow)
                        {
                            if(hour<hourNow)
                            {
                                airplanes.get(i).getFlights().get(j).setFlightStatus(Flight.Status.DONE);
                            }else if (hour==hourNow)
                            {
                                if (miniute<=miniuteNow)
                                {
                                    airplanes.get(i).getFlights().get(j).setFlightStatus(Flight.Status.DONE);
                                }
                            }
                        }



                    }
                }
            }
        }
        //save in file
        WriteReadFile<Airplane> airplaneWriteReadFile=new WriteReadFile<>(Main.airplanes,"airplanes.txt");
        airplaneWriteReadFile.writeList();





    }
}
