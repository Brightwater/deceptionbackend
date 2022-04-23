package com.brightwaters.deception.repository.postgres;

import com.brightwaters.deception.model.postgres.DeceptionUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<DeceptionUser, Long> {
    DeceptionUser findByUsername(String username);
}
