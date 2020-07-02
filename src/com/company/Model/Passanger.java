package com.company.Model;

public class Passanger extends Person
{
    private String credit;

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
    }
}
