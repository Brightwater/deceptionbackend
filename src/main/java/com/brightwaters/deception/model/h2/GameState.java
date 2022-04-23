package com.brightwaters.deception.model.h2;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@Entity
public class GameState {
    @Id
    private UUID gameId;
    @Lob
    private String jsonState;
    private Timestamp lastUpdatedTs;
    
    public UUID getGameId() {
        return gameId;
    }
    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
    public String getJsonState() {
        return jsonState;
    }
    public void setJsonState(String jsonState) {
        this.jsonState = jsonState;
    }
    public Timestamp getLastUpdatedTs() {
        return lastUpdatedTs;
    }
    public void setLastUpdatedTs(Timestamp lastUpdatedTs) {
        this.lastUpdatedTs = lastUpdatedTs;
    }

    

    
}
