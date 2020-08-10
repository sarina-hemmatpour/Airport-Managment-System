package com.company.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Flight implements Serializable ,Showable
{
    private String id;
    private Airplane airplane;
    private Ticket ticket;
    private String origin;
    private String desination;
    private String date;
    private String duration;
    private String nmbrOFsoldTickets;
    private ArrayList<Passanger> passangers;
    private String takeOffTime;
    private String flightPrice;
    private String flightFine;

    public String getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(String flightPrice) {
        this.flightPrice = flightPrice;
    }

    public String getFlightFine() {
        return flightFine;
    }

    public void setFlightFine(String flightFine) {
        this.flightFine = flightFine;
    }

    @Override
    public String Show() {

        String passangersid="";
        for (int i=0 ; i<passangers.size() ; i++)
        {
            passangersid+=passangers.get(i);
            if (i!=passangers.size()-1)
            {
                passangersid+="-";
            }
        }

        String string=id+"- airplane:"+airplane.getId()+"- ticket:"+ticket.getId()+
                "- origin:"+origin+"- destination:"+desination+"-"+date+"-"+takeOffTime+
                "- duration:"+duration+"- passangers:"+passangersid;

        return string;
    }

    public enum Status{ DONE, DOING , notDONE }
    private Status flightStatus;

    public Status getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(Status flightStatus) {
        this.flightStatus = flightStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDesination() {
        return desination;
    }

    public void setDesination(String desination) {
        this.desination = desination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration =duration;
    }

    public String getNmbrOFsoldTickets() {
        return nmbrOFsoldTickets;
    }

    public void setNmbrOFsoldTickets(String nmbrOFsoldTickets) {
        this.nmbrOFsoldTickets = nmbrOFsoldTickets;
    }

    public ArrayList<Passanger> getPassangers() {
        return passangers;
    }

    public void setPassangers(ArrayList<Passanger> passangers) {
        this.passangers = passangers;
    }

    public String getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(String takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public Flight(String id, Airplane airplane, Ticket ticket, String origin, String desination, String date, String duration, String takeOffTime) {
        //idkuyfyu*******************************
        this.id = id;

        this.airplane = airplane;
        this.ticket = ticket;
        this.origin = origin;
        this.desination = desination;
        this.date = date;
        this.duration = duration;
        this.nmbrOFsoldTickets = "0";
        this.takeOffTime = takeOffTime;
        this.flightStatus=Status.notDONE;

        this.flightPrice=this.ticket.getPrice();
        this.flightFine=this.ticket.getFine();

        this.passangers=new ArrayList<>();
    }
}
