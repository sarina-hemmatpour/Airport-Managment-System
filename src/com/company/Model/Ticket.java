package com.company.Model;

import java.io.Serializable;

public class Ticket implements Serializable , Showable
{
    private String id;
    private String price;
    private String fine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }


    public Ticket(String id, String price, String fine) {
        this.id = id;
        this.price = price;
        this.fine = fine;
    }

    @Override
    public String Show() {
        return id+"- price:"+price+"-"+fine;
    }
}
