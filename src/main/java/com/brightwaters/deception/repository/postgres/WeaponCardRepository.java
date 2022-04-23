package com.brightwaters.deception.repository.postgres;

import java.io.Serial;
import java.util.List;

import com.brightwaters.deception.model.postgres.WeaponCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeaponCardRepository extends JpaRepository<WeaponCard, Serial> {
    WeaponCard findByName(String name);
    @Query(value = "SELECT * FROM weapon_card ORDER BY RANDOM ()", nativeQuery = true)
    List<WeaponCard> findAllCards();
}
