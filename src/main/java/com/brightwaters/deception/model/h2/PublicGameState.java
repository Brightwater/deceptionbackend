package com.brightwaters.deception.model.h2;

import java.util.ArrayList;
import java.util.UUID;

import com.brightwaters.deception.model.postgres.HintCard;

public class PublicGameState {
    private UUID gameId;
    private String forensicScientistPlayer;
    private int cardCount;
    private int playerCount;
    private ArrayList<String> killMethods;
    private ArrayList<String> locations;
    private ArrayList<HintCard> hintCardsInPlay;
    private ArrayList<Player> players;
    private String state;
    private int selectedLocation;
    private int selectedDeathMethod;
    
    public UUID getGameId() {
        return gameId;
    }
    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
    public String getForensicScientistPlayer() {
        return forensicScientistPlayer;
    }
    public void setForensicScientistPlayer(String forensicScientistPlayer) {
        this.forensicScientistPlayer = forensicScientistPlayer;
    }
    public int getCardCount() {
        return cardCount;
    }
    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }
    public int getPlayerCount() {
        return playerCount;
    }
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    public ArrayList<String> getKillMethods() {
        return killMethods;
    }
    public void setKillMethods(ArrayList<String> killMethods) {
        this.killMethods = killMethods;
    }
    public ArrayList<String> getLocations() {
        return locations;
    }
    public void setLocations(ArrayList<String> locations) {
        this.locations = locations;
    }
    public ArrayList<HintCard> getHintCardsInPlay() {
        return hintCardsInPlay;
    }
    public void setHintCardsInPlay(ArrayList<HintCard> hintCardsInPlay) {
        this.hintCardsInPlay = hintCardsInPlay;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getSelectedLocation() {
        return selectedLocation;
    }
    public void setSelectedLocation(int selectedLocation) {
        this.selectedLocation = selectedLocation;
    }
    public int getSelectedDeathMethod() {
        return selectedDeathMethod;
    }
    public void setSelectedDeathMethod(int selectedDeathMethod) {
        this.selectedDeathMethod = selectedDeathMethod;
    }
    
    
}
