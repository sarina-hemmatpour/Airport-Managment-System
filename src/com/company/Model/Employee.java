package com.company.Model;

public class Employee extends Person
{
    private String salary;
    private String address;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Employee(String id, String name, String lastname, String username, String password, String phoneNumber, String emailAdress, String salary , String address) {
        super(id, name, lastname, username, password, phoneNumber, emailAdress);
        this.salary = salary;
        this.address=address;
    }
}
