package com.brightwaters.deception.repository.h2;
import java.util.UUID;

import com.brightwaters.deception.model.h2.GameState;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateRepository extends JpaRepository<GameState, UUID>  {
    GameState findByGameId(UUID id);
}
