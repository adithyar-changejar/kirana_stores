package com.example.kiranastore.repository;

import com.example.kiranastore.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Account repository.
 */
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    /**
     * Find by user id optional.
     *
     * @param userId the user id
     * @return the optional
     */
    Optional<AccountEntity> findByUserId(String userId);
}
