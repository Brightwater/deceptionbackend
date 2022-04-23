package com.brightwaters.deception.GameControl;

import java.util.ArrayList;

import com.brightwaters.deception.model.h2.EventQueueObj;
import com.brightwaters.deception.model.h2.GameStateObj;
import com.brightwaters.deception.model.h2.Player;
import com.brightwaters.deception.model.h2.SelectCardEvent;
import com.brightwaters.deception.model.postgres.ClueCard;
import com.brightwaters.deception.model.postgres.WeaponCard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

@Component
public class Game {

    // accept the players card selection
    // for the murderer set the murder cards
    public GameStateObj selectCards(EventQueueObj event, GameStateObj state) {
        try {
            // NEED RESET VOTED

            ArrayList<Player> ps = state.getPublicState().getPlayers();
            Player player = null;
            for (Player p : ps) {
                System.out.println(p.getUsername() + " " + event.getPlayer());
                if (p.getUsername().equals(event.getPlayer())) {
                    player = p;
                    System.out.println("Found player");
                    break;
                }
            }

            // only submit cards in the pregame
            if (!state.getPublicState().getState().equals("Pregame") || 
                state.getPublicState().getForensicScientistPlayer().equals(event.getPlayer()) ||
                player.getVoted().getVoted()) {
                System.out.println("Null add cards");
                return null;
            }
            // increment cards submitted count
            
            System.out.println("Murderer " + state.getPrivateState().getMurdererPlayer());

            // for non murderer thats it
            if (!state.getPrivateState().getMurdererPlayer().equals(event.getPlayer())) {
                state.getPrivateState().setSubmittedCardsCount(state.getPrivateState().getSubmittedCardsCount() + 1);
                player.getVoted().setVoted(true);
                System.out.println("should be saving non murd");
                return state;
            }

            // for murderer need to set the game murder cards
            ObjectMapper mapper = new ObjectMapper();

            // since the gamestate is stored as json we need to convert it
            SelectCardEvent selectCard = new SelectCardEvent();
            try {
                selectCard = mapper.readValue(event.getJsonEvent(), SelectCardEvent.class);
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

            // all this to validate murderer cards...
            for (Player p : ps) {
                if (p.getUsername().equals(event.getPlayer())) {
                    ArrayList<ClueCard> clues = p.getClueCards();
                    ArrayList<WeaponCard> weapons = p.getWeaponCards();
                    Boolean clueValid = false;
                    Boolean weaponValid = false;
                    for (ClueCard clue : clues) {
                        if (clue.getName().equals(selectCard.getClueCard())) {
                            clueValid = true;
                            break;
                        }
                    }
                    for (WeaponCard weapon : weapons) {
                        if (weapon.getName().equals(selectCard.getWeaponCard())) {
                            weaponValid = true;
                            break;
                        }
                    }
                    if (!clueValid || !weaponValid) {
                        return null;
                    }
                }
            }

            // if got here the cards are valid so set them
            player.getVoted().setVoted(true);
            state.getPrivateState().setSubmittedCardsCount(state.getPrivateState().getSubmittedCardsCount() + 1);
            state.getPrivateState().setMurderClueName(selectCard.getClueCard());
            state.getPrivateState().setMurderClueName(selectCard.getWeaponCard());

            System.out.println(state.getPrivateState().getMurderClueName());

            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public GameStateObj checkAllCardsSubmitted(EventQueueObj event, GameStateObj state) {
        try {
            if (state.getPrivateState().getSubmittedCardsCount() == state.getPublicState().getPlayerCount() - 1) {
                System.out.println("All votes in");
                for (Player p : state.getPublicState().getPlayers()) {
                    p.getVoted().setVoted(false);
                }
                state.getPublicState().setState("Round1Pre");
            }
            
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }


}
