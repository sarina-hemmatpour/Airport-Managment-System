package com.company.Model;

import java.util.ArrayList;

public class Flight
{
    private String id;
    private Airplane airplane;
    private Ticket ticket;
    private String origin;
    private String desination;
    private String date;
    private String takeOffTime;
    private String nmbrOFsoldTickets;
    private ArrayList<Passanger> passangers;
    private String flightTime;

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

    public String getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(String takeOffTime) {
        this.takeOffTime = takeOffTime;
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

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public Flight(String id, Airplane airplane, Ticket ticket, String origin, String desination, String date, String takeOffTime, String nmbrOFsoldTickets, ArrayList<Passanger> passangers, String flightTime) {
        this.id = id;
        this.airplane = airplane;
        this.ticket = ticket;
        this.origin = origin;
        this.desination = desination;
        this.date = date;
        this.takeOffTime = takeOffTime;
        this.nmbrOFsoldTickets = nmbrOFsoldTickets;
        this.passangers = passangers;
        this.flightTime = flightTime;
    }
}
