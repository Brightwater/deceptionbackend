package com.brightwaters.deception.GameControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.brightwaters.deception.model.h2.EventQueueObj;
import com.brightwaters.deception.model.h2.GameStateObj;
import com.brightwaters.deception.model.h2.Player;
import com.brightwaters.deception.model.h2.PlayerVoted;
import com.brightwaters.deception.model.h2.PublicGameState;
import com.brightwaters.deception.model.postgres.ClueCard;
import com.brightwaters.deception.model.postgres.WeaponCard;
import com.brightwaters.deception.repository.h2.GameStateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Lobby {

    public GameStateObj addPlayer(EventQueueObj event, GameStateObj state) {
        Player p = new Player();
        p.setUsername(event.getPlayer());
        p.setPlayerNumber(state.getPublicState().getPlayerCount() + 1);
        state.getPublicState().setPlayerCount(state.getPublicState().getPlayerCount() + 1);
        p.setWeaponCards(new ArrayList<WeaponCard>());
        p.setClueCards(new ArrayList<ClueCard>());
        PlayerVoted pVoted= new PlayerVoted();
        pVoted.setVoted(false);
        p.setVoted(pVoted);
        ArrayList<Player> players = state.getPublicState().getPlayers();
        for (Player player : players) {
            if (player.getUsername().equals(event.getPlayer())) {
                System.out.println(player.getUsername() + "   DFSADJAKSLDJASKLDJKLASDJ " + event.getPlayer());
                return null;
            }
        }
        players.add(p);
        state.getPublicState().setPlayers(players);

        return state;
    }

    // determine who the forens is
    // add the number of roles
    // shuffle the roles
    public GameStateObj startGame(EventQueueObj event, GameStateObj state) {
        // determine forens
        String firstPlayer = state.getPublicState().getPlayers().get(0).getUsername();
        if (state.getPublicState().getPlayerCount() >= 4 && state.getPublicState().getPlayerCount() < 12 
        && event.getPlayer().equals(firstPlayer)) {
            System.out.println("Correct conditions");
            Random random = new Random();
            int index = random.nextInt(state.getPublicState().getPlayerCount());
            Player forens = state.getPublicState().getPlayers().get(index);
            state.getPublicState().setForensicScientistPlayer(forens.getUsername());
            ArrayList<String> rolesLeft = state.getPrivateState().getRolesLeft();
            rolesLeft.add("Murderer");
            int count = state.getPublicState().getPlayerCount() - 2;
            for (int i=0; i < count; i++) {
                rolesLeft.add("Investigator");
            }

            Collections.shuffle(rolesLeft);
            state.getPrivateState().setRolesLeft(rolesLeft);
            state.getPublicState().setState("Pregame");
            ArrayList<Player> players = state.getPublicState().getPlayers();

            String forensName = forens.getUsername();
            // give the players cards
            for (Player player : players) {
                // don't give the forens cards
                if (player.getUsername().equals(forensName)) {
                    continue;
                }

                ArrayList<ClueCard> clueCards = new ArrayList<>();
                ArrayList<WeaponCard> weaponCards = new ArrayList<>();

                // give the players the stated card number (4-9)
                for (int i = 0; i < state.getPublicState().getCardCount(); i++) {
                    ClueCard clueCard = state.getPrivateState().getClueCardsDeck().get(0);
                    clueCards.add(clueCard);
                    state.getPrivateState().getClueCardsDeck().remove(clueCard);

                    WeaponCard weaponCard = state.getPrivateState().getWeaponCardsDeck().get(0);
                    weaponCards.add(weaponCard);
                    state.getPrivateState().getWeaponCardsDeck().remove(weaponCard);

                    player.setClueCards(clueCards);
                    player.setWeaponCards(weaponCards);
                }
            }

            state.getPublicState().setPlayers(players);

            return state;
        }
        
        System.out.println("incorrect conditions");
        return null;
    }
}
