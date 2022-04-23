package com.brightwaters.deception.model.h2;

public class GameStateObj {
    private PublicGameState publicState;
    private PrivateGameState privateState;
    public PublicGameState getPublicState() {
        return publicState;
    }
    public void setPublicState(PublicGameState publicState) {
        this.publicState = publicState;
    }
    public PrivateGameState getPrivateState() {
        return privateState;
    }
    public void setPrivateState(PrivateGameState privateState) {
        this.privateState = privateState;
    }
    @Override
    public String toString() {
        return "GameStateObj [privateState=" + privateState + ", publicState=" + publicState + "]";
    }
}
