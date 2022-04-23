package com.brightwaters.deception.model.h2;

public class SetupGameEvent {
    private String username;
    private String cardNumber;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    @Override
    public String toString() {
        return "SetupGameEvent [cardNumber=" + cardNumber + ", username=" + username + "]";
    }
    

    
}
