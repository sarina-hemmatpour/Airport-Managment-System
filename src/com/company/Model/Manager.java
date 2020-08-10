package com.company.Model;

import java.io.Serializable;


public class Manager extends Person implements Serializable
{
    private String salary;
    private String address;
    private boolean isSuperAdmin;

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Manager(String id, String name, String lastname, String username, String password, String phoneNumber, String emailAdress, String salary, String address , boolean isSuperAdmin) {
        super(id, name, lastname, username, password, phoneNumber, emailAdress);
        this.salary = salary;
        this.address = address;
        this.isSuperAdmin=isSuperAdmin;
    }

    @Override
    public String Show() {
        String string="";
        string+=getName()+"-"+getLastname()+"-"+getId()+"-"+getUsername()+"-"+getPassword()+"-"+getPhoneNumber()+"-"+
                getEmailAdress()+"-"+getAddress()+"-"+getSalary();

        return string;
    }

}
