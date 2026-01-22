package com.example.kiranastore.repository;

import com.example.kiranastore.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;   // ✅ THIS WAS MISSING
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AccountEntity> findByUserId(String userId);
}
