package com.brightwaters.deception.model.h2;

import java.util.ArrayList;

import com.brightwaters.deception.model.postgres.ClueCard;
import com.brightwaters.deception.model.postgres.WeaponCard;


public class Player {
    private String username;
    private int playerNumber;
    private ArrayList<WeaponCard> weaponCards;
    private ArrayList<ClueCard> clueCards;
    private PlayerVoted voted;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getPlayerNumber() {
        return playerNumber;
    }
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
    public ArrayList<WeaponCard> getWeaponCards() {
        return weaponCards;
    }
    public void setWeaponCards(ArrayList<WeaponCard> weaponCards) {
        this.weaponCards = weaponCards;
    }
    public ArrayList<ClueCard> getClueCards() {
        return clueCards;
    }
    public void setClueCards(ArrayList<ClueCard> clueCards) {
        this.clueCards = clueCards;
    }
    public PlayerVoted getVoted() {
        return voted;
    }
    public void setVoted(PlayerVoted voted) {
        this.voted = voted;
    }
    @Override
    public String toString() {
        return "Player [clueCards=" + clueCards + ", playerNumber=" + playerNumber + ", username=" + username
                + ", voted=" + voted + ", weaponCards=" + weaponCards + "]";
    }
    
    
}
