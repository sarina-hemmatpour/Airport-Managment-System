package com.company.Model;

import java.util.ArrayList;

public class Airplane
{
    private String id;
    private String seats;
    private ArrayList<Flight> flights;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public Airplane(String id, String seats, ArrayList<Flight> flights) {
        this.id = id;
        this.seats = seats;
        this.flights = flights;
    }
}
