package com.brightwaters.deception.GameControl;

import java.util.ArrayList;

import com.brightwaters.deception.model.h2.EventQueueObj;
import com.brightwaters.deception.model.h2.GameStateObj;
import com.brightwaters.deception.model.h2.Player;
import com.brightwaters.deception.model.h2.SelectCardEvent;
import com.brightwaters.deception.model.h2.SelectHintCardEvent;
import com.brightwaters.deception.model.postgres.ClueCard;
import com.brightwaters.deception.model.postgres.HintCard;
import com.brightwaters.deception.model.postgres.Location;
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
                // load locations
                for (int i = 0; i < 6; i++) {
                    Location location = state.getPrivateState().getLocationDeck().get(0);
                    state.getPrivateState().getLocationDeck().remove(location);
                    state.getPublicState().getLocations().add(location.getName());
                }

                // load hint cards
                for (int i = 0; i < 4; i++) {
                    HintCard hintCard = state.getPrivateState().getHintCardsDeck().get(0);
                    state.getPrivateState().getHintCardsDeck().remove(hintCard);
                    hintCard.setCurrentlySelectedOption(-1);
                    state.getPublicState().getHintCardsInPlay().add(hintCard);
                }

                state.getPublicState().setSelectedDeathMethod(-1);
                state.getPublicState().setSelectedLocation(-1);
                state.getPublicState().setState("Round1pre");
            }
            
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public GameStateObj round1PreSelectHint(EventQueueObj event, GameStateObj state) {
        try {
            if (state.getPublicState().getState().equals("Round1pre") 
            && event.getPlayer().equals(state.getPublicState().getForensicScientistPlayer())) {
                
                ObjectMapper mapper = new ObjectMapper();
                // since the gamestate is stored as json we need to convert it
                SelectHintCardEvent selectCard = new SelectHintCardEvent();
                
                
                try {
                    selectCard = mapper.readValue(event.getJsonEvent(), SelectHintCardEvent.class);
                    System.out.println(selectCard.getCardName());
                    System.out.println(selectCard.getCardType());
                    System.out.println(selectCard.getCurrentlySelectedOption());

                    // need to validate these options
                    if (selectCard.getCardType().equals("death")) {
                        state.getPublicState().setSelectedDeathMethod(selectCard.getCurrentlySelectedOption());
                        System.out.println("set death");
                    }
                    else if (selectCard.getCardType().equals("location")) {
                        state.getPublicState().setSelectedLocation(selectCard.getCurrentlySelectedOption());
                        System.out.println("set loc");
                    }
                    else {
                        for (HintCard card: state.getPublicState().getHintCardsInPlay()) {
                            if (card.getName().equals(selectCard.getCardName())) {
                                card.setCurrentlySelectedOption(selectCard.getCurrentlySelectedOption());
                                System.out.println("set hint");
                                break;
                            }
                        }
                    }
                    return state;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
            } else {
                return null;
            }
            
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public GameStateObj round1PreSubmitHint(EventQueueObj event, GameStateObj state) {
        try {
            if (state.getPublicState().getState().equals("Round1pre") 
            && event.getPlayer().equals(state.getPublicState().getForensicScientistPlayer())) {
                for (HintCard hintCard : state.getPublicState().getHintCardsInPlay()) {
                    if (hintCard.getCurrentlySelectedOption() == -1) {
                        return null;
                    }
                }
                if (state.getPublicState().getSelectedDeathMethod() == -1 || state.getPublicState().getSelectedLocation() == -1) {
                    return null;
                }

                state.getPublicState().setState("Round1");
                return state;
            } else {
                return null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
