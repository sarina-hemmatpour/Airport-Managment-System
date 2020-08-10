package com.company.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable , Showable
{
    private String time;
    private String text;
    private String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Message(String text , String sender) {
        this.text = text;

        //set time : now
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();

        this.time = dtf.format(now);

        this.sender=sender;
    }

    @Override
    public String Show() {

        return sender+"-"+time+"-"+text;
    }
}
