package com.brightwaters.deception.repository.postgres;

import java.io.Serial;
import java.util.List;

import com.brightwaters.deception.model.postgres.HintCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HintCardRepository extends JpaRepository<HintCard, Serial> {
    HintCard findByName(String name);
    @Query(value = "SELECT * FROM hint_card ORDER BY RANDOM ()", nativeQuery = true)
    List<HintCard> findAllCards();
}
