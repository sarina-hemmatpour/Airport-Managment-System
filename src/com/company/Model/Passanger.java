package com.company.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Passanger extends Person implements Serializable
{
    private String credit;
    private ArrayList<Ticket> baughtTickets=new ArrayList<>();

    public ArrayList<Ticket> getBaughtTickets() {
        return baughtTickets;
    }

    public void setBaughtTickets(ArrayList<Ticket> baughtTickets) {
        this.baughtTickets = baughtTickets;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;

    }

    public Passanger(String id, String name, String lastname, String username, String password, String phoneNumber, String emailAdress, String credit)
    {
        super(id, name, lastname, username, password, phoneNumber, emailAdress);
        this.credit = credit;
//        this.baughtTickets=new ArrayList<>();
        System.out.println(this.baughtTickets.size());
    }

    @Override
    public String Show() {

        String string="";
        string+=getName()+"-"+getLastname()+"-"+getId()+"-"+getUsername()+"-"+getPassword()+"-"+getPhoneNumber()+"-"+
                getEmailAdress()+"-"+getCredit();

        return string;
    }
}
