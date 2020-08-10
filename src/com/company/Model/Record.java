package com.company.Model;

import com.company.Controller.SARecordsPageController;
import com.company.Main;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record implements Serializable , Showable {

    private String text;
    private  String time;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Record() {
        this.text=new String();

        //set time : now
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();

        this.time = dtf.format(now);
    }

    public void passangerBuyTicket(Passanger passanger , Ticket ticket)
    {
        this.text="Passanger:"+passanger.getId()+" baught this ticket:"+ticket.getId();
        SARecordsPageController.refreshRecordsTV();
    }

    public void passangerCancelTicket(Passanger passanger , Ticket ticket)
    {
        this.text="Passanger:"+passanger.getId()+" canceled this ticket:"+ticket.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void passangerEditProfile(Passanger passanger)
    {
        this.text="Passanger:"+passanger.getId()+" edited their profile";
        SARecordsPageController.refreshRecordsTV();
    }
    public void passangerRaiseCredit(Passanger passanger)
    {
        this.text="Passanger:"+passanger.getId()+" raised their credit to "+passanger.getCredit();
        SARecordsPageController.refreshRecordsTV();
    }


    public void employeeEditProfile(Employee employee)
    {
        this.text="Employee:"+employee.getId()+" edited their profile";
        SARecordsPageController.refreshRecordsTV();
    }
    public void employeeRemovePassangerInFlight(Employee employee , Passanger passanger , Flight flight)
    {
        this.text="Employee:"+employee.getId()+" removed this passanger:"+passanger.getId() +" from this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void employeeAddPassangerInFlight(Employee employee , Passanger passanger , Flight flight)
    {
        this.text="Employee:"+employee.getId()+" added this passanger:"+passanger.getId() +" too this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void employeeRemoveFlight(Employee employee , Flight flight)
    {
        this.text="Employee:"+employee.getId()+" removed this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void employeeAddFlight(Employee employee , Flight flight)
    {
        this.text="Employee:"+employee.getId()+" added this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }

    public void managerEditProfile(Manager manager)
    {
        this.text="Manager:"+manager.getId()+" edited their profile";
        SARecordsPageController.refreshRecordsTV();
    }
    public void managerRemovePassangerInFlight(Manager manager , Passanger passanger , Flight flight)
    {
        this.text="Manager:"+manager.getId()+" removed this passanger:"+passanger.getId() +" from this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void managerAddPassangerInFlight(Manager manager , Passanger passanger , Flight flight)
    {
        this.text="Manager:"+manager.getId()+" added this passanger:"+passanger.getId() +" too this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void managerRemoveFlight(Manager manager , Flight flight)
    {
        this.text="Manager:"+manager.getId()+" removed this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void managerAddFlight(Manager manager , Flight flight)
    {
        this.text="Manager:"+manager.getId()+" added this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }

    public void managerRemovePassanger(Manager manager , Passanger passanger)
    {
        this.text="Manager:"+manager.getId()+" removed this passanger:"+passanger.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void managerAddPassanger(Manager manager , Passanger passanger)
    {
        this.text="Manager:"+manager.getId()+" added this passanger:"+passanger.getId();
        SARecordsPageController.refreshRecordsTV();
    }

    public void managerRemoveAirplane(Manager manager , Airplane airplane)
    {
        this.text="Manager:"+manager.getId()+" removed this airplane:"+airplane.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void managerAddAirplane(Manager manager , Airplane airplane)
    {
        this.text="Manager:"+manager.getId()+" added this airplane:"+airplane.getId();
        SARecordsPageController.refreshRecordsTV();
    }


    public void superAdminEditProfile()
    {
        this.text="superAdmin:"+Main.superAdmin.getId()+" edited their profile";
        SARecordsPageController.refreshRecordsTV();
    }
    public void superAdminRemovePassangerInFlight( Passanger passanger , Flight flight)
    {
        this.text="superAdmin:"+Main.superAdmin.getId()+" removed this passanger:"+passanger.getId() +" from this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void superAdminAddPassangerInFlight(Passanger passanger , Flight flight)
    {
        this.text="superAdmin:"+Main.superAdmin.getId()+" added this passanger:"+passanger.getId() +" too this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void superAdminRemoveFlight( Flight flight)
    {
        this.text="superAdmin:"+Main.superAdmin.getId()+" removed this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void superAdminAddFlight(Flight flight)
    {
        this.text="superAdmin:"+Main.superAdmin.getId()+" added this flight:"+flight.getId();
        SARecordsPageController.refreshRecordsTV();
    }

    public void superAdminRemovePassanger( Passanger passanger)
    {
        this.text="superAdmin:"+Main.superAdmin.getId()+" removed this passanger:"+passanger.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void superAdminAddPassanger( Passanger passanger)
    {
        this.text="superAdmin:"+Main.superAdmin.getId()+" added this passanger:"+passanger.getId();
        SARecordsPageController.refreshRecordsTV();
    }

    public void superAdminRemoveAirplane( Airplane airplane)
    {
        this.text="superAdmin:"+Main.superAdmin.getId()+" removed this airplane:"+airplane.getId();
        SARecordsPageController.refreshRecordsTV();
    }
    public void superAdminAddAirplane( Airplane airplane)
    {
        this.text="superAdmin:"+Main.superAdmin.getId()+" added this airplane:"+airplane.getId();
        SARecordsPageController.refreshRecordsTV();
    }

    @Override
    public String Show() {
        return time+"-"+text;
    }
}
