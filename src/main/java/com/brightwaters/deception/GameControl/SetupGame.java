package com.brightwaters.deception.GameControl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.brightwaters.deception.model.h2.GameState;
import com.brightwaters.deception.model.h2.GameStateObj;
import com.brightwaters.deception.model.h2.Player;
import com.brightwaters.deception.model.h2.PlayerVoted;
import com.brightwaters.deception.model.h2.PrivateGameState;
import com.brightwaters.deception.model.h2.PublicGameState;
import com.brightwaters.deception.model.postgres.ClueCard;
import com.brightwaters.deception.model.postgres.HintCard;
import com.brightwaters.deception.model.postgres.Location;
import com.brightwaters.deception.model.postgres.WeaponCard;
import com.brightwaters.deception.repository.h2.GameStateRepository;
import com.brightwaters.deception.repository.postgres.ClueCardRepository;
import com.brightwaters.deception.repository.postgres.HintCardRepository;
import com.brightwaters.deception.repository.postgres.LocationRepository;
import com.brightwaters.deception.repository.postgres.WeaponCardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupGame {
    @Autowired
    private ClueCardRepository clueRepos;
    
    @Autowired
    private HintCardRepository hintRepos;

    @Autowired
    private WeaponCardRepository weaponRepos;

    @Autowired
    private LocationRepository locationRepos;

    // location repos

    @Autowired
    private GameStateRepository gameRepos;

    public UUID setupNewGame(String startPlayerName, int cardCount) {
        System.out.println("card count " + cardCount);
        ArrayList<ClueCard> clueCards = (ArrayList<ClueCard>) clueRepos.findAllCards();
        ArrayList<HintCard> hintCards = (ArrayList<HintCard>) hintRepos.findAllCards();
        ArrayList<WeaponCard> weaponCards = (ArrayList<WeaponCard>) weaponRepos.findAllCards();
        ArrayList<Location> locationDeck = (ArrayList<Location>) locationRepos.findAllCards();

        for (ClueCard clueCard : clueCards) {
            clueCard.setSusVotes(new ArrayList<>());
        }
        for (WeaponCard weaponCard : weaponCards) {
            weaponCard.setSusVotes(new ArrayList<>());
        }

        // initialize game state
        GameState gameStateJson = new GameState();
        GameStateObj gameState = new GameStateObj();
        gameStateJson.setGameId(UUID.randomUUID());
        
        PrivateGameState privateGameState = new PrivateGameState();
        privateGameState.setHintCardsDeck(hintCards);
        privateGameState.setClueCardsDeck(clueCards);
        privateGameState.setWeaponCardsDeck(weaponCards);
        privateGameState.setLocationDeck(locationDeck);
        ArrayList<String> rolesLeft = new ArrayList<>();
        privateGameState.setRolesLeft(rolesLeft);
        gameState.setPrivateState(privateGameState);

        PublicGameState publicGameState = new PublicGameState();
        publicGameState.setGameId(gameStateJson.getGameId());
        publicGameState.setCardCount(cardCount);
        ArrayList<String> killMethods = new ArrayList<>();
        killMethods.add("Suffocation");
        killMethods.add("Severe Injury");
        killMethods.add("Loss of Blood");
        killMethods.add("Illness or Disease");
        killMethods.add("Poisoning");
        killMethods.add("Accident or Arson");
        publicGameState.setKillMethods(killMethods);
        ArrayList<String> locations = new ArrayList<>();
        publicGameState.setLocations(locations);
        ArrayList<HintCard> hintCardsInPlay = new ArrayList<>();
        publicGameState.setHintCardsInPlay(hintCardsInPlay);
        ArrayList<Player> players = new ArrayList<>();
        Player p = new Player();
        p.setUsername(startPlayerName);
        p.setPlayerNumber(1);
        publicGameState.setPlayerCount(1);
        p.setWeaponCards(new ArrayList<WeaponCard>());
        p.setClueCards(new ArrayList<ClueCard>());
        PlayerVoted pVoted= new PlayerVoted();
        pVoted.setVoted(false);
        p.setVoted(pVoted);
        players.add(p);
        
        publicGameState.setPlayers(players);
        gameState.setPublicState(publicGameState);
        
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
        
        return gameStateJson.getGameId();
    }
}
