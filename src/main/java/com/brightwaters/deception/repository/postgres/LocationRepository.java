package com.brightwaters.deception.repository.postgres;

import java.io.Serial;
import java.util.List;

import com.brightwaters.deception.model.postgres.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Serial> {
    @Query(value = "SELECT * FROM location ORDER BY RANDOM ()", nativeQuery = true)
    List<Location> findAllCards();
}
