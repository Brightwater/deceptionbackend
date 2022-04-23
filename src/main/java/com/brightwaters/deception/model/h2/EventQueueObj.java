package com.brightwaters.deception.model.h2;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "event_queue")
public class EventQueueObj {
    @Id
    @GeneratedValue
    private Long eventId;
    private UUID gameId;
    @Lob
    private String jsonEvent;
    private String eventType;
    private Timestamp eventTs;
    private String player;
    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public UUID getGameId() {
        return gameId;
    }
    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
    public String getJsonEvent() {
        return jsonEvent;
    }
    public void setJsonEvent(String jsonEvent) {
        this.jsonEvent = jsonEvent;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public Timestamp getEventTs() {
        return eventTs;
    }
    public void setEventTs(Timestamp eventTs) {
        this.eventTs = eventTs;
    }
    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }
    @Override
    public String toString() {
        return "EventQueueObj [eventId=" + eventId + ", eventTs=" + eventTs + ", eventType=" + eventType + ", gameId="
                + gameId + ", jsonEvent=" + jsonEvent + ", player=" + player + "]";
    }
}
