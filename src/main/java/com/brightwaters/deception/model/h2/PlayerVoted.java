package com.brightwaters.deception.model.h2;


public class PlayerVoted {
    private Boolean voted;
    private String clue;
    private String weapon;
    public Boolean getVoted() {
        return voted;
    }
    public void setVoted(Boolean voted) {
        this.voted = voted;
    }
    public String getClue() {
        return clue;
    }
    public void setClue(String clue) {
        this.clue = clue;
    }
    public String getWeapon() {
        return weapon;
    }
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }
    @Override
    public String toString() {
        return "PlayerVoted [clue=" + clue + ", voted=" + voted + ", weapon=" + weapon + "]";
    }

    
}
