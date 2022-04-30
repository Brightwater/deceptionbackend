package com.brightwaters.deception.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;


import com.brightwaters.deception.GameControl.SetupGame;
import com.brightwaters.deception.model.h2.EventQueueObj;
import com.brightwaters.deception.model.h2.GameState;
import com.brightwaters.deception.model.h2.GameStateObj;
import com.brightwaters.deception.model.h2.Player;
import com.brightwaters.deception.model.h2.PublicGameState;
import com.brightwaters.deception.model.h2.RevealMurdererEvent;
import com.brightwaters.deception.model.h2.SelectCardEvent;
import com.brightwaters.deception.model.h2.SelectHintCardEvent;
import com.brightwaters.deception.model.h2.SetupGameEvent;
import com.brightwaters.deception.repository.h2.EventQueueRepository;
import com.brightwaters.deception.repository.h2.GameStateRepository;
import com.brightwaters.deception.svc.ExpiredGameCleaner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    @Autowired
    private GameStateRepository gameRepos;

    @Autowired
    SetupGame setup;

    @Autowired
    EventQueueRepository eventRepos;

    @Autowired
    ExpiredGameCleaner cleaner;

    // start the game event
    // takes in 1st username and the game card count
    @PostMapping(value = "/events/setupGame", consumes = "application/json", produces = "application/json")
    public PublicGameState testPGet(@RequestBody SetupGameEvent event) {
        System.out.println(new Timestamp(System.currentTimeMillis()) + " | Creating new game for player: " + event.getUsername() + " with card count " + event.getCardNumber());
        // start the game and get its id
        UUID gameId = setup.setupNewGame(event.getUsername(), Integer.parseInt(event.getCardNumber()));
        System.out.println(new Timestamp(System.currentTimeMillis()) + " | New game id: " + gameId);
        // retrieve the gamestate from memory 
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
        }

        // retrieve the public game state and return it to the player
        PublicGameState publicGameState = gameState.getPublicState();
        return publicGameState;
    }

    // get current gamestate
    // all players call this every 1s!
    @GetMapping(value="/gameState/{gameId}/{playerName}")
    public PublicGameState getCurrentGameState(@PathVariable("gameId") String s, @PathVariable("playerName") String playerName) {
        UUID gameId;
        try {
            gameId = UUID.fromString(s);
        } catch (Exception e) {
            return null;
        }
        
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
            return null;
        } 

        // retrieve the public game state and return it to the player
        PublicGameState publicGameState = gameState.getPublicState();
        // System.out.println(new Timestamp(System.currentTimeMillis()) + " | Sending gameId: " + gameId + " to player: " + playerName);
        return publicGameState;
    }

    // reveal the current players role
    @GetMapping(value="/revealRole/{gameId}/{playerName}")
    public String revealRole(@PathVariable("gameId") String s, @PathVariable("playerName") String playerName) {
        
        UUID gameId;
        try {
            gameId = UUID.fromString(s);
        } catch (Exception e) {
            return null;
        }
        
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
            return null;
        } 

        // don't tell them their role if its pregame
        if (!gameState.getPublicState().getState().equals("Pregame")
        || !gameState.getPublicState().getState().equals("")) {

            if (playerName.equals(gameState.getPublicState().getForensicScientistPlayer())) {
                return "Forensic Scientist";
            }
            if (playerName.equals(gameState.getPrivateState().getMurdererPlayer())) {
                return "Murderer";
            }
    
            for (Player p : gameState.getPublicState().getPlayers()) {
                if (p.getUsername().equals(playerName)) {
                    return "Investigator";
                }
            }
    
            return "Spectator";
        }

        return "";
    }

    // reveal the murderer and cards
    @GetMapping(value="/revealMurderer/{gameId}/{playerName}")
    public RevealMurdererEvent revealMurderer(@PathVariable("gameId") String s, @PathVariable("playerName") String playerName) {
        RevealMurdererEvent event = new RevealMurdererEvent();
        
        UUID gameId;
        try {
            gameId = UUID.fromString(s);
        } catch (Exception e) {
            return null;
        }
        
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
            return null;
        } 

     
        if (playerName.equals(gameState.getPublicState().getForensicScientistPlayer())) {
            event.setClueCard(gameState.getPrivateState().getMurderClueName());
            event.setWeaponCard(gameState.getPrivateState().getMurderWeaponName());
            event.setPlayerName(gameState.getPrivateState().getMurdererPlayer());
            return event;
        }
        

        return null;
    }

    // below are all single thread events

    // event for new player to join the game
    @GetMapping(value="/events/joinGame/{gameId}/{playerName}")
    public void joinGame(@PathVariable("gameId") String s, @PathVariable("playerName") String playerName) {
        EventQueueObj event = new EventQueueObj();
        event.setPlayer(playerName);
        event.setGameId(UUID.fromString(s));
        event.setEventTs(new Timestamp(System.currentTimeMillis()));
        event.setEventType("joinGame");
        eventRepos.save(event);
    }

    // event for the game to start
    @GetMapping(value="/events/startGame/{gameId}/{playerName}")
    public void startGame(@PathVariable("gameId") String s, @PathVariable("playerName") String playerName) {
        EventQueueObj event = new EventQueueObj();
        event.setPlayer(playerName);
        event.setGameId(UUID.fromString(s));
        event.setEventTs(new Timestamp(System.currentTimeMillis()));
        event.setEventType("startGame");
        eventRepos.save(event);
    }

    // event for a non forensic player to select their cards
    @GetMapping(value="/events/selectCards/{gameId}/{playerName}/{clueCard}/{weaponCard}")
    public void selectCards(@PathVariable("gameId") String s, @PathVariable("playerName") String playerName,
    @PathVariable("clueCard") String clueCard, @PathVariable("weaponCard") String weaponCard) {
        EventQueueObj event = new EventQueueObj();
        SelectCardEvent cardEvent = new SelectCardEvent();
        cardEvent.setClueCard(clueCard);
        cardEvent.setWeaponCard(weaponCard);

        event.setPlayer(playerName);
        event.setGameId(UUID.fromString(s));
        event.setEventTs(new Timestamp(System.currentTimeMillis()));
        event.setEventType("selectCards");

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(cardEvent);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        event.setJsonEvent(json);
        eventRepos.save(event);
    }

    // event for a forensic player to select a hint card
    @GetMapping(value="/events/selectHintCard/{gameId}/{playerName}/{cardType}/{cardName}/{selected}")
    public void selectHintCard(@PathVariable("gameId") String s, @PathVariable("playerName") String playerName,
    @PathVariable("cardType") String cardType, @PathVariable("cardName") String cardName,
    @PathVariable("selected") int selected) {

        EventQueueObj event = new EventQueueObj();
        SelectHintCardEvent cardEvent = new SelectHintCardEvent();
        cardEvent.setCardName(cardName.replace("%20", " "));
        cardEvent.setCardType(cardType);
        cardEvent.setCurrentlySelectedOption(selected);

        event.setPlayer(playerName);
        event.setGameId(UUID.fromString(s));
        event.setEventTs(new Timestamp(System.currentTimeMillis()));
        event.setEventType("selectCardForens");

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(cardEvent);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        event.setJsonEvent(json);
        eventRepos.save(event);
    }

    // event for a forensic player to submit their hint card selection
    @GetMapping(value="/events/submitHintCard/{gameId}/{playerName}")
    public void submitHintCards(@PathVariable("gameId") String s, @PathVariable("playerName") String playerName) {

        EventQueueObj event = new EventQueueObj();

        event.setPlayer(playerName);
        event.setGameId(UUID.fromString(s));
        event.setEventTs(new Timestamp(System.currentTimeMillis()));
        event.setEventType("submitCardsForens");

        eventRepos.save(event);
    }

   

    @PostMapping(value = "/events/string")
    public void testSGet(@RequestBody String s) {
        System.out.println(s);
        
    }

    @GetMapping(value = "/events/test/{s}")
    public void testEventAdd(@PathVariable("s") String s) {
        EventQueueObj event = new EventQueueObj();
        event.setJsonEvent(s);
        event.setGameId(UUID.randomUUID());
        event.setEventTs(new Timestamp(System.currentTimeMillis()));
        event.setEventType("TEST EVENT");
        eventRepos.save(event);
    }

    @GetMapping(value = "/events/getallevents")
    public void testEventRet() {
        ArrayList<EventQueueObj> eventQueue = (ArrayList<EventQueueObj>) eventRepos.retrieveEventQueue();
        for (EventQueueObj event : eventQueue) {
            System.out.println("EVENT " + event.getEventTs());
        }
    }

    @GetMapping(value = "/cleaner")
    public void testcleaner() {
        cleaner.cleanup();
    }

}
