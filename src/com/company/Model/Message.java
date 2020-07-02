package com.company.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message
{
    private String time;
    private String text;

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

    public Message(String text) {
        this.text = text;

        //set time : now
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();

        this.time = dtf.format(now);
    }
}
