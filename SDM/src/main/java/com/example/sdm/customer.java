package com.example.sdm;

import java.util.Date;

public class customer {

    public String name;
    public String ticketType;
    public int age;
    public double amount;
    public Date date;

    public customer(String name, String ticketType, int age, double amount, Date date){
        this.name = name;
        this.ticketType = ticketType;
        this.age = age;
        this.amount = amount;
        this.date = date;
    }
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
