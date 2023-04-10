package com.example.sdm;

public class ticket {

    String ticketNumber;
    String ticketType;
    double ticketAmount;

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public double getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(double ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public String getTicket(){
        if(ticketNumber.charAt(0) >= '0' && ticketNumber.charAt(3) <= 'z'){
            return "Ticket has been credited successfully, please check the email for the confirmation";
        }else{
            return "Invalid ticket information has been provided";
        }
    }
}
