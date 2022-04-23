package com.brightwaters.deception.svc;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.brightwaters.deception.model.h2.GameState;
import com.brightwaters.deception.repository.h2.GameStateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExpiredGameCleaner {
    @Autowired
    GameStateRepository gameRepos;

    @Scheduled(cron = "0 0/10 * * * *")
    public void cleanup() {
        ArrayList<GameState> games = (ArrayList<GameState>) gameRepos.findAll();
        Long now = new Timestamp(System.currentTimeMillis()).toInstant().toEpochMilli();

        for (GameState state: games) {
            Long ts = state.getLastUpdatedTs().toInstant().toEpochMilli();
            Long difference = now - ts;
            if (difference >= 3600000L) {
                System.out.println(new Timestamp(System.currentTimeMillis()) + " | Game: " + state.getGameId() + " expired");
                gameRepos.delete(state);
            }
        }
    }
}
