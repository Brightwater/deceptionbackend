package com.brightwaters.deception.model.h2;

public class SelectHintCardEvent {
    String cardType;
    String cardName;
    int currentlySelectedOption;
    
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public int getCurrentlySelectedOption() {
        return currentlySelectedOption;
    }
    public void setCurrentlySelectedOption(int currentlySelectedOption) {
        this.currentlySelectedOption = currentlySelectedOption;
    }
    
    
}
