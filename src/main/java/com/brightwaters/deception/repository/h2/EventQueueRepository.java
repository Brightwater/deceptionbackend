package com.brightwaters.deception.repository.h2;

import java.util.List;

import com.brightwaters.deception.model.h2.EventQueueObj;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventQueueRepository extends JpaRepository<EventQueueObj, Long>   {
    @Query(value = "SELECT * FROM event_queue ORDER BY eventTs asc", nativeQuery = true)
    List<EventQueueObj> retrieveEventQueue();
}
