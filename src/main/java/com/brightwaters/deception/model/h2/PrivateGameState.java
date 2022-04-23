package com.brightwaters.deception.model.h2;

import java.util.ArrayList;
import java.util.HashMap;

import com.brightwaters.deception.model.postgres.ClueCard;
import com.brightwaters.deception.model.postgres.HintCard;
import com.brightwaters.deception.model.postgres.Location;
import com.brightwaters.deception.model.postgres.WeaponCard;

public class PrivateGameState {
    private ArrayList<HintCard> hintCardsDeck;
    private ArrayList<ClueCard> clueCardsDeck;
    private ArrayList<WeaponCard> weaponCardsDeck;
    private ArrayList<Location> locationDeck;
    private ArrayList<String> rolesLeft;
    private String murdererPlayer;
    private String murderWeaponName;
    private String murderClueName;
    public ArrayList<HintCard> getHintCardsDeck() {
        return hintCardsDeck;
    }
    public void setHintCardsDeck(ArrayList<HintCard> hintCardsDeck) {
        this.hintCardsDeck = hintCardsDeck;
    }
    public ArrayList<ClueCard> getClueCardsDeck() {
        return clueCardsDeck;
    }
    public void setClueCardsDeck(ArrayList<ClueCard> clueCardsDeck) {
        this.clueCardsDeck = clueCardsDeck;
    }
    public ArrayList<WeaponCard> getWeaponCardsDeck() {
        return weaponCardsDeck;
    }
    public void setWeaponCardsDeck(ArrayList<WeaponCard> weaponCardsDeck) {
        this.weaponCardsDeck = weaponCardsDeck;
    }
    public ArrayList<Location> getLocationDeck() {
        return locationDeck;
    }
    public void setLocationDeck(ArrayList<Location> locationDeck) {
        this.locationDeck = locationDeck;
    }
    public ArrayList<String> getRolesLeft() {
        return rolesLeft;
    }
    public void setRolesLeft(ArrayList<String> rolesLeft) {
        this.rolesLeft = rolesLeft;
    }
    public String getMurdererPlayer() {
        return murdererPlayer;
    }
    public void setMurdererPlayer(String murdererPlayer) {
        this.murdererPlayer = murdererPlayer;
    }
    public String getMurderWeaponName() {
        return murderWeaponName;
    }
    public void setMurderWeaponName(String murderWeaponName) {
        this.murderWeaponName = murderWeaponName;
    }
    public String getMurderClueName() {
        return murderClueName;
    }
    public void setMurderClueName(String murderClueName) {
        this.murderClueName = murderClueName;
    }
   
    
    

    
}
