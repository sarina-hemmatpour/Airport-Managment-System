package com.company.Model;

public class Ticket
{
    private String id;
    private String price;
    private String cancelCost;

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

    public String getCancelCost() {
        return cancelCost;
    }

    public void setCancelCost(String cancelCost) {
        this.cancelCost = cancelCost;
    }


    public Ticket(String id, String price, String cancelCost) {
        this.id = id;
        this.price = price;
        this.cancelCost = cancelCost;
    }
}
