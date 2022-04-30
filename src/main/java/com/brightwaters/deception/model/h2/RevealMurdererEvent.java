package com.brightwaters.deception.model.h2;

public class RevealMurdererEvent {
    private String playerName;
    private String clueCard;
    private String weaponCard;
    
    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public String getClueCard() {
        return clueCard;
    }
    public void setClueCard(String clueCard) {
        this.clueCard = clueCard;
    }
    public String getWeaponCard() {
        return weaponCard;
    }
    public void setWeaponCard(String weaponCard) {
        this.weaponCard = weaponCard;
    }

    
}
