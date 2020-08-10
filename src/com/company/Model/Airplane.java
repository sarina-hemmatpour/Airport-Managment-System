package com.company.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Airplane implements Serializable,Showable
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

    public Airplane(String id, String seats) {
        this.id = id;
        this.seats = seats;
        this.flights = new ArrayList<>();
    }

    @Override
    public String Show() {

        String flightsid="";
        for (int i=0 ; i<flights.size() ; i++)
        {
            flightsid+=flights.get(i);
            if (i!=flights.size()-1)
            {
                flightsid+="-";
            }
        }

        String string="";
        string+=id+"- seats:"+seats+"- flights:"+flightsid;

        return string;
    }
}
