package com.brightwaters.deception.SingleThreadQueueProcessor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.brightwaters.deception.GameControl.Game;
import com.brightwaters.deception.GameControl.Lobby;
import com.brightwaters.deception.model.h2.EventQueueObj;
import com.brightwaters.deception.model.h2.GameState;
import com.brightwaters.deception.model.h2.GameStateObj;
import com.brightwaters.deception.repository.h2.EventQueueRepository;
import com.brightwaters.deception.repository.h2.GameStateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Scope
public class QueueProcessor extends Thread {
    @Autowired
    EventQueueRepository eventRepos;

    @Autowired
    GameStateRepository gameRepos;

    @Autowired
    Lobby lobby;

    @Autowired
    Game game;

    
    // process ALL game events
    // in a single thread to make sure
    // they get processed sequentially
    @Override  
    public void run() {
        System.out.println("STARTING QUEUE PROCESSOR");
        while(true) {
            // run every .2s for low cpu usage
            try {
                Thread.sleep(200);
            } catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            try {
                // read all events from queue in the orer they occurred
            ArrayList<EventQueueObj> eventQueue = (ArrayList<EventQueueObj>) eventRepos.retrieveEventQueue();
            if (eventQueue.size() > 0) {
                for (EventQueueObj event : eventQueue) {
                    if (event.getEventType().equals("joinGame")) {
                        GameStateObj gameState = serializeGameState(event.getGameId());
                        gameState = lobby.addPlayer(event, gameState);
                        if (gameState == null) {
                            continue;
                        }
                        saveGameState(gameState);
                        System.out.println(event.getEventTs() + "| EVENT " + event.getEventType() + " for player " + event.getPlayer());
                    }
                    else if (event.getEventType().equals("startGame")) {
                        System.out.println("STARTING GAME");
                        GameStateObj gameState = serializeGameState(event.getGameId());
                        gameState = lobby.startGame(event, gameState);
                        if (gameState == null) {
                            continue;
                        }
                        saveGameState(gameState);
                        System.out.println(event.getEventTs() + "| EVENT " + event.getEventType() + " for player " + event.getPlayer());
                    }
                    else if (event.getEventType().equals("selectCards")) {
                        GameStateObj gameState = serializeGameState(event.getGameId());
                        gameState = game.selectCards(event, gameState);
                        if (gameState == null) {
                            continue;
                        }
                        gameState = game.checkAllCardsSubmitted(event, gameState);
                        saveGameState(gameState);
                        System.out.println(event.getEventTs() + "| EVENT " + event.getEventType() + " for player " + event.getPlayer());
                    }
                    else if (event.getEventType().equals("selectCardForens")) {
                        GameStateObj gameState = serializeGameState(event.getGameId());
                        gameState = game.round1PreSelectHint(event, gameState);
                        if (gameState == null) {
                            System.out.println("selectForens error");
                            continue;
                        }
                        saveGameState(gameState);
                        System.out.println(event.getEventTs() + "| EVENT " + event.getEventType() + " for player " + event.getPlayer());
                    }
                    else if (event.getEventType().equals("submitCardsForens")) {
                        GameStateObj gameState = serializeGameState(event.getGameId());
                        gameState = game.round1PreSubmitHint(event, gameState);
                        if (gameState == null) {
                            continue;
                        }
                        saveGameState(gameState);
                        System.out.println(event.getEventTs() + "| EVENT " + event.getEventType() + " for player " + event.getPlayer());
                    }
                    else if (event.getEventType().equals("endRound")) {
                        System.out.println("END ROUND");
                        GameStateObj gameState = serializeGameState(event.getGameId());
                        gameState = game.endRound(event, gameState);
                        if (gameState == null) {
                            continue;
                        }
                        saveGameState(gameState);
                        System.out.println(event.getEventTs() + "| EVENT " + event.getEventType() + " for player " + event.getPlayer());
                    }
                    else if (event.getEventType().equals("waitToAddCard")) {
                        GameStateObj gameState = serializeGameState(event.getGameId());
                        gameState = game.waitToAddCard(event, gameState);
                        if (gameState == null) {
                            continue;
                        }
                        saveGameState(gameState);
                        System.out.println(event.getEventTs() + "| EVENT " + event.getEventType() + " for player " + event.getPlayer());
                    }
                    else {
                        System.out.println("Unsupported event");
                        System.out.println(event.getEventTs() + "| EVENT " + event.getEventType());
                    }
                    
                }
                eventRepos.deleteAll(eventQueue);
            }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            
        }
    }

    // get the gamestate from memory and convert it to java object
    public GameStateObj serializeGameState(UUID gameId) {
        GameState gameStateJson = gameRepos.findByGameId(gameId);
        ObjectMapper mapper = new ObjectMapper();

        // since the gamestate is stored as json we need to convert it
        GameStateObj gameState = new GameStateObj();
        try {
            gameState = mapper.readValue(gameStateJson.getJsonState(), GameStateObj.class);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        return gameState;
    }

    // save the gamestate in memory
    public GameStateObj saveGameState(GameStateObj gameState) {
        GameState gameStateJson = new GameState();
        gameStateJson.setGameId(gameState.getPublicState().getGameId());

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(gameState);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        gameStateJson.setJsonState(json);
        gameStateJson.setLastUpdatedTs(new Timestamp(System.currentTimeMillis()));
        gameRepos.save(gameStateJson);

        return gameState;
    }
}
