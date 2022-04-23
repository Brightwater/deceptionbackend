package com.brightwaters.deception.repository.postgres;

import java.io.Serial;
import java.util.List;

import com.brightwaters.deception.model.postgres.ClueCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClueCardRepository extends JpaRepository<ClueCard, Serial>{
    ClueCard findByName(String name);

    @Query(value = "SELECT * FROM clue_card ORDER BY RANDOM ()", nativeQuery = true)
    List<ClueCard> findAllCards();
}
